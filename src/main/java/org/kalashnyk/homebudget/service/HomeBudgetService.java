package org.kalashnyk.homebudget.service;

import org.kalashnyk.homebudget.model.Account;
import org.kalashnyk.homebudget.model.Operation;
import org.kalashnyk.homebudget.model.OperationCategory;
import org.kalashnyk.homebudget.model.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Sergii on 02.09.2016.
 */
public interface HomeBudgetService {
    User getUser(long userId);

    Account getAccount(long accountId, long userId);

    Operation getOperation(long operationId, long userId);

    OperationCategory getOperationCategory(long operationCategoryId, long userId);

    User saveUser(User user);

    Account saveAccount(Account account, long userId);

    OperationCategory saveOperationCategory(OperationCategory operationCategory, long userId);

    Operation saveOperation(Operation operation,
                       long userId,
                       long debitAccountId,
                       long creditAccountId);

    void deleteUser(long id);

    void deleteAccount(long accountId, long userId);

    void deleteOperation(long operationId, long userId);

    void deleteOperationCategory(long operationCategoryId, long userId);

    List<User> getAllUsers();

    List<Account> getAllAccounts(long userId);

    List<Operation> getAllOperations(long userId);

    List<Operation> getAllOperationsForAccount(long userId, long accountId);

    List<OperationCategory> getAllOperationCategories(long userId);

    List<Operation> getOperationsBetween(long userId, LocalDateTime start, LocalDateTime end);
}
