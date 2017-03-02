package org.kalashnyk.homebudget.service;

import org.kalashnyk.homebudget.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sergii on 02.09.2016.
 */
public interface HomeBudgetService {
    Account getAccount(long accountId, long userId);

    Operation getOperation(long operationId, long userId);

    OperationCategory getOperationCategory(long operationCategoryId, long userId);

    Account saveAccount(Account account, long userId);

    OperationCategory saveOperationCategory(OperationCategory operationCategory, long userId);

    Operation saveOperation(Operation operation,
                            long userId,
                            long accountId);

    void saveTransfer(Operation outTransfer, Operation inTransfer, long userId, long fromAccountId, long toAccountId);

    void deleteAccount(long accountId, long userId);

    void deleteOperation(long operationId, long userId);

    void deleteOperationCategory(long operationCategoryId, long userId);

    List<Account> getAllAccounts(long userId);

    List<Operation> getAllOperations(long userId);

    List<Operation> getAllOperationsForAccount(long userId, long accountId);

    List<OperationCategory> getAllOperationCategories(long userId);

    List<Operation> getOperationsBetween(long userId, LocalDateTime start, LocalDateTime end);

    Map<Account.Type, Set<Account>> getAccountsGroupByType(long userId);

    Map<LocalDate, Set<Operation>> getOperationsForAccountGroupByDate(long userId, long accountId);

    OperationCategory getServiceCategory(String serviceCategory);
}
