package org.kalashnyk.homebudget.service;

import org.kalashnyk.homebudget.model.Account;
import org.kalashnyk.homebudget.model.Operation;
import org.kalashnyk.homebudget.model.OperationCategory;
import org.kalashnyk.homebudget.model.User;
import org.kalashnyk.homebudget.repository.AccountRepository;
import org.kalashnyk.homebudget.repository.OperationCategoryRepository;
import org.kalashnyk.homebudget.repository.OperationRepository;
import org.kalashnyk.homebudget.repository.UserRepository;
import org.kalashnyk.homebudget.util.exception.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Sergii on 27.09.2016.
 */
@Service
public class HomeBudgetServiceImpl implements HomeBudgetService {

    private UserRepository userRepository;
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    private OperationCategoryRepository operationCategoryRepository;

    @Autowired
    public HomeBudgetServiceImpl(UserRepository userRepository,
                                 AccountRepository accountRepository,
                                 OperationRepository operationRepository,
                                 OperationCategoryRepository operationCategoryRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.operationCategoryRepository = operationCategoryRepository;
    }

    @Override
    public User getUser(long userId) {
        return ExceptionUtil.checkNotFoundWithId(userRepository.findById(userId), userId);
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

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Account saveAccount(Account account, long userId) {
        return accountRepository.save(account, userId);
    }

    @Override
    public OperationCategory saveOperationCategory(OperationCategory operationCategory, long userId) {
        return operationCategoryRepository.save(operationCategory, userId);
    }

    @Override
    public Operation saveOperation(Operation operation, long userId, long debitAccountId, long creditAccountId) {
        return operationRepository.save(operation, userId, debitAccountId, creditAccountId);
    }

    @Override
    public void deleteUser(long id) {
        ExceptionUtil.checkNotFoundWithId(userRepository.delete(id), id);
    }

    @Override
    public void deleteAccount(long accountId, long userId) {

    }

    @Override
    public void deleteOperation(long operationId, long userId) {

    }

    @Override
    public void deleteOperationCategory(long operationCategoryId, long userId) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public List<Account> getAllAccounts(long userId) {
        return accountRepository.getAll(userId);
    }

    @Override
    public List<Operation> getAllOperations(long userId) {
        return null;
    }

    @Override
    public List<Operation> getAllOperationsForAccount(long userId, long accountId) {
        return null;
    }

    @Override
    public List<OperationCategory> getAllOperationCategories(long userId) {
        return null;
    }

    @Override
    public List<Operation> getOperationsBetween(long userId, LocalDateTime start, LocalDateTime end) {
        return null;
    }


}
