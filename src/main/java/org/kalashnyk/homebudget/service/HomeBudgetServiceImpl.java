package org.kalashnyk.homebudget.service;

import org.kalashnyk.homebudget.model.*;
import org.kalashnyk.homebudget.repository.*;
import org.kalashnyk.homebudget.util.exception.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Sergii on 27.09.2016.
 */
@Service
@Transactional
public class HomeBudgetServiceImpl implements HomeBudgetService {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    private OperationCategoryRepository categoryRepository;
    private FXRateRepository fxRateRepository;
    private NBUFXRateRepository nbufxRateRepository;


    @Autowired
    public HomeBudgetServiceImpl(AccountRepository accountRepository,
                                 OperationRepository operationRepository,
                                 OperationCategoryRepository categoryRepository,
                                 FXRateRepository fxRateRepository,
                                 NBUFXRateRepository nbufxRateRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.categoryRepository = categoryRepository;
        this.fxRateRepository = fxRateRepository;
        this.nbufxRateRepository = nbufxRateRepository;
    }

    @Override
    public Account getAccount(long accountId, long userId) {
        return ExceptionUtil.checkNotFoundWithId(accountRepository.findById(accountId, userId), accountId);
    }

    @Override
    public Operation getOperation(long operationId, long userId) {
        return ExceptionUtil.checkNotFoundWithId(operationRepository.findById(operationId, userId), operationId);
    }

    @Override
    public OperationCategory getOperationCategory(long operationCategoryId, long userId) {
        return ExceptionUtil.checkNotFoundWithId(
                categoryRepository.findById(operationCategoryId, userId),
                operationCategoryId);
    }

    @Override
    public Account saveAccount(Account account, long userId) {
        Account savedAccount = accountRepository.save(account, userId);


        operationRepository.save(Operation.builder()
                .account(savedAccount)
                .amount(savedAccount.getAmount())
                .date(LocalDate.now().atStartOfDay())
                .category(categoryRepository.getServiceCategory(OperationCategory.OPENING))
                .remainOnAccount(savedAccount.getAmount())
                .build(), userId, savedAccount.getId());

        return savedAccount;
    }

    @Override
    public OperationCategory saveOperationCategory(OperationCategory operationCategory, long userId) {
        return categoryRepository.save(operationCategory, userId);
    }

    @Override
    @Transactional
    public Operation saveOperation(Operation operation, long userId, long accountId) {
        calculateAmountBaseCurrencyAmount(operation);
        operationRepository.save(operation, userId, accountId);
        correctAllOperationsAfterThis(getLastOperationBeforeThis(operation), userId, accountId);
        correctRemainOnAccountAfterOperation(userId, accountId);

        return operation;
    }

    @Override
    @Transactional
    public void saveTransfer(Operation outTransfer, Operation inTransfer, long userId, long fromAccountId, long toAccountId) {
        operationRepository.save(outTransfer, userId, fromAccountId);
        operationRepository.save(inTransfer, userId, toAccountId);

        outTransfer.setCorrespondingOperation(inTransfer);
        inTransfer.setCorrespondingOperation(outTransfer);

        operationRepository.save(outTransfer, userId, fromAccountId);
        operationRepository.save(inTransfer, userId, toAccountId);


        correctAllOperationsAfterThis(getLastOperationBeforeThis(inTransfer), userId, toAccountId);
        correctAllOperationsAfterThis(getLastOperationBeforeThis(outTransfer), userId, fromAccountId);

        correctRemainOnAccountAfterOperation(userId, fromAccountId);
        correctRemainOnAccountAfterOperation(userId, toAccountId);
    }

    @Override
    public void deleteAccount(long accountId, long userId) {
        ExceptionUtil.checkNotFoundWithId(accountRepository.delete(accountId, userId), accountId);
    }

    @Override
    @Transactional
    public void deleteOperation(long operationId, long userId) {
        Operation toDelete = operationRepository.findById(operationId, userId);
        Operation before = getLastOperationBeforeThis(toDelete);

        long accountId = toDelete.getAccount().getId();

        if (toDelete.getCorrespondingOperation() != null) {
            long correspondingOperationId = toDelete.getCorrespondingOperation().getId();
            toDelete.getCorrespondingOperation().setCorrespondingOperation(null);
            toDelete.setCorrespondingOperation(null);
            deleteOperation(correspondingOperationId, userId);

        }

        ExceptionUtil.checkNotFoundWithId(operationRepository.delete(operationId, userId), operationId);
        correctAllOperationsAfterThis(before, userId, accountId);
        correctRemainOnAccountAfterOperation(userId, accountId);
    }

    @Override
    public void deleteOperationCategory(long operationCategoryId, long userId) {
        ExceptionUtil.checkNotFoundWithId(categoryRepository.delete(operationCategoryId, userId), operationCategoryId);
    }

    @Override
    public List<Account> getAllAccounts(long userId) {
        List<Account> sortedAccounts = accountRepository.getAll(userId);
        sortedAccounts.sort(null);
        return sortedAccounts;
    }

    @Override
    public List<Operation> getAllOperations(long userId) {
        return operationRepository.getAll(userId);
    }

    @Override
    @Transactional
    public List<Operation> getAllOperationsForAccount(long userId, long accountId) {
        return operationRepository.getAllForAccount(userId, accountId);
    }

    @Override
    public List<OperationCategory> getAllOperationCategories(long userId) {
        return categoryRepository.getAll(userId).stream()
                .filter(category -> category.getOperationType() != OperationCategory.OperationType.IN_TRANSFER)
                .filter(category -> category.getOperationType() != OperationCategory.OperationType.OUT_TRANSFER)
                .sorted((o1, o2) -> {
                    if (o1.getOperationType() != o2.getOperationType()) {
                        return o1.getOperationType() == OperationCategory.OperationType.INCOME ? -1 : 1;
                    } else {
                        return o1.toString().compareTo(o2.toString());
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Operation> getOperationsBetween(long userId, LocalDateTime start, LocalDateTime end) {
        return operationRepository.getBetween(userId, start, end);
    }

    @Override
    public Map<Account.Type, Set<Account>> getAccountsGroupByType(long userId) {
        Map<Account.Type, Set<Account>> groupedAccounts = new TreeMap<>();
        List<Account> accounts = accountRepository.getAll(userId);

        for (Account a : accounts) {
            Account.Type type = a.getType();
            if (groupedAccounts.containsKey(type)) {
                groupedAccounts.get(type).add(a);
            } else {
                groupedAccounts.put(type, new TreeSet<>(Arrays.asList(a)));
            }
        }

        return groupedAccounts;
    }

    @Override
    public Map<LocalDate, Set<Operation>> getOperationsForAccountGroupByDate(long userId, long accountId, LocalDate start, LocalDate end) {
        Map<LocalDate, Set<Operation>> grouppedOperations = new TreeMap<>();
        List<Operation> operations = operationRepository.getAllForAccountBetween(userId, accountId, start, end);

        System.out.println(Arrays.toString(operations.toArray()));

        for (Operation o : operations) {
            LocalDate date = o.getDate().toLocalDate();
            System.out.println(o);
            if (grouppedOperations.containsKey(date)) {
                grouppedOperations.get(date).add(o);
            } else {
                grouppedOperations.put(date, new TreeSet<>(Arrays.asList(o)));
            }
        }
        return grouppedOperations;
    }

    @Override
    public OperationCategory getServiceCategory(String serviceCategory) {
        return categoryRepository.getServiceCategory(serviceCategory);
    }

    private void correctAllOperationsAfterThis(Operation before, long userId, long accountId) {
        Operation previous = before;
        if (before == null) {
            previous = Operation.builder()
                    .remainOnAccount(new BigDecimal("0.0"))
                    .build();
        }
        for (Operation o : getAllOperationToCorrect(before, accountId, userId)) {
            if (o.isExpense()) {
                o.setRemainOnAccount(previous.getRemainOnAccount().subtract(o.getAmount()));
            } else {
                o.setRemainOnAccount(previous.getRemainOnAccount().add(o.getAmount()));
            }

            operationRepository.save(o, userId, accountId);
            previous = o;
        }
    }

    private void correctRemainOnAccountAfterOperation(long userId, long accountId) {
        Account account = accountRepository.findById(accountId, userId);
        Operation last = operationRepository.getLastOperationForAccount(accountId);
        if (last != null) {
            account.setAmount(last.getRemainOnAccount());
        } else {
            account.setAmount(BigDecimal.ZERO);
        }
        accountRepository.save(account, userId);
    }

    private Operation getLastOperationBeforeThis(Operation operation) {
        return operationRepository.getLastOperationBefore(operation.getAccount().getId(), operation);
    }

    private List<Operation> getAllOperationToCorrect(Operation before, long accountId, long userId) {
        if (before == null)
            return operationRepository.getAllForAccount(userId, accountId);

        return operationRepository.getAllOperationAfter(accountId, before);
    }

    private void calculateAmountBaseCurrencyAmount(Operation operation) {
        BigDecimal rate = getNBUFXRate(operation.baseCurrency(), operation.getCurrency(), operation.getDate().toLocalDate()).getRate();
        operation.setAmountInBaseCurrency(operation.getAmount().setScale(2).divide(rate, RoundingMode.HALF_UP));
    }

    @Override
    public FXRate getNBUFXRate(Currency base, Currency variable, LocalDate date) {
        FXRate rate = FXRate.builder()
                .baseCurrency(base)
                .variableCurrency(variable)
                .date(date)
                .build();

        if (base == variable) {
            rate.setRate(BigDecimal.ONE);
        } else if (variable == Currency.getInstance("UAH")) {
            rate.setRate(nbufxRateRepository.get(base, date).getRate());
        } else if (base == Currency.getInstance("UAH")) {
            rate.setRate(BigDecimal.ONE.setScale(10).divide(nbufxRateRepository.get(variable, date).getRate(), RoundingMode.HALF_UP));

        } else {
            BigDecimal baseToUAHRate = nbufxRateRepository.get(base, date).getRate().setScale(10, RoundingMode.HALF_UP);
            BigDecimal variableToUAHRate = nbufxRateRepository.get(variable, date).getRate().setScale(10, RoundingMode.HALF_UP);
            rate.setRate(baseToUAHRate.divide(variableToUAHRate, RoundingMode.HALF_UP));
        }

        return rate;
    }
}