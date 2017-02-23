package org.kalashnyk.homebudget.service;

import org.kalashnyk.homebudget.model.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Sergii on 02.09.2016.
 */
public interface HomeBudgetService {
    Currency getCurrency(int id);

    Currency getCurrency(String stringId);

    List<Currency> getAllCurrencies();

    Account getAccount(long accountId, long userId);

    Operation getOperation(long operationId, long userId);

    OperationCategory getOperationCategory(long operationCategoryId, long userId);

    Account saveAccount(Account account, long userId);

    OperationCategory saveOperationCategory(OperationCategory operationCategory, long userId);

    Operation saveOperation(Operation operation,
                            long userId,
                            long accountId);

    void deleteAccount(long accountId, long userId);

    void deleteOperation(long operationId, long userId);

    void deleteOperationCategory(long operationCategoryId, long userId);

    List<Account> getAllAccounts(long userId);

    List<Operation> getAllOperations(long userId);

    List<Operation> getAllOperationsForAccount(long userId, long accountId);

    List<OperationCategory> getAllOperationCategories(long userId);

    List<Operation> getOperationsBetween(long userId, LocalDateTime start, LocalDateTime end);
}
