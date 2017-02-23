package org.kalashnyk.homebudget.service;

import org.kalashnyk.homebudget.model.*;
import org.kalashnyk.homebudget.repository.*;
import org.kalashnyk.homebudget.util.exception.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sergii on 27.09.2016.
 */
@Service
@Transactional
public class HomeBudgetServiceImpl implements HomeBudgetService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    private OperationCategoryRepository operationCategoryRepository;
    private CurrencyRepository currencyRepository;

    @Autowired
    public HomeBudgetServiceImpl(UserRepository userRepository,
                                 AccountRepository accountRepository,
                                 OperationRepository operationRepository,
                                 OperationCategoryRepository operationCategoryRepository,
                                 CurrencyRepository currencyRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.operationCategoryRepository = operationCategoryRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Currency getCurrency(int id) {
        return currencyRepository.getCurrency(id);
    }

    @Override
    public Currency getCurrency(String stringId) {
        return currencyRepository.getCurrency(stringId);
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return currencyRepository.getAllCurrencies();
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
                operationCategoryRepository.findById(operationCategoryId, userId),
                operationCategoryId);
    }

    /*@Override
    public User saveUser(User user) {
        User savedUser = userRepository.save(user);
        Account expenseAccount =
                Account.builder()
                        .name("EXPENSE_ACCOUNT")
                        .currency(savedUser.getBasicCurrency())
                        .owner(savedUser)
                        .amount(new BigDecimal("0.0"))
                        .type(Account.Type.EXPENSE)
                        .build();

        Account incomeAccount =
                Account.builder()
                        .name("INCOME_ACCOUNT")
                        .currency(savedUser.getBasicCurrency())
                        .owner(savedUser)
                        .amount(new BigDecimal("0.0"))
                        .type(Account.Type.INCOME)
                        .build();

        operationCategoryRepository.save(
                OperationCategory.builder()
                .name("IN_TRANSFER")
                .operationType(OperationCategory.OperationType.IN_TRANSFER)
                .owner(savedUser)
                .parent(null)
                .build(), savedUser.getId());

        operationCategoryRepository.save(
                OperationCategory.builder()
                        .name("OUT_TRANSFER")
                        .operationType(OperationCategory.OperationType.OUT_TRANSFER)
                        .owner(savedUser)
                        .parent(null)
                        .build(), savedUser.getId());

        accountRepository.save(expenseAccount, savedUser.getId());
        accountRepository.save(incomeAccount, savedUser.getId());

        return savedUser;
    }*/

    @Override
    public Account saveAccount(Account account, long userId) {
        return accountRepository.save(account, userId);
    }

    @Override
    public OperationCategory saveOperationCategory(OperationCategory operationCategory, long userId) {
        return operationCategoryRepository.save(operationCategory, userId);
    }

    @Override
    @Transactional
    public Operation saveOperation(Operation operation, long userId, long accountId) {
        Account account = accountRepository.findById(accountId, userId);
        BigDecimal diff;

        if (operation.isNew()) {
            diff = operation.getAmount();
        } else {
            diff = operationRepository.findById(operation.getId(), userId).getAmount().subtract(operation.getAmount());
        }

        switch (operation.getCategory().getOperationType()) {
            case EXPENSE:
                account.setAmount(account.getAmount().subtract(diff));
                break;
            case INCOME:
                account.setAmount(account.getAmount().add(diff));
                break;
        }

        accountRepository.save(account, userId);

        return operationRepository.save(operation, userId, accountId);
    }

    @Override
    public void deleteAccount(long accountId, long userId) {
        ExceptionUtil.checkNotFoundWithId(accountRepository.delete(accountId, userId), accountId);
    }

    @Override
    public void deleteOperation(long operationId, long userId) {
        ExceptionUtil.checkNotFoundWithId(operationRepository.delete(operationId, userId), operationId);
    }

    @Override
    public void deleteOperationCategory(long operationCategoryId, long userId) {
        ExceptionUtil.checkNotFoundWithId(operationCategoryRepository.delete(operationCategoryId, userId), operationCategoryId);
    }

    @Override
    public List<Account> getAllAccounts(long userId) {
        return accountRepository.getAll(userId);
    }

    @Override
    public List<Operation> getAllOperations(long userId) {
        return operationRepository.getAll(userId);
    }

    @Override
    public List<Operation> getAllOperationsForAccount(long userId, long accountId) {
        return operationRepository.getAllForAccount(userId, accountId);
    }

    @Override
    public List<OperationCategory> getAllOperationCategories(long userId) {
        return operationCategoryRepository.getAll(userId).stream()
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
}