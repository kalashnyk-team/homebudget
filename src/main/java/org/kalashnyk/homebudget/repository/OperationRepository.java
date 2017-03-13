package org.kalashnyk.homebudget.repository;

import org.kalashnyk.homebudget.model.Operation;
import org.kalashnyk.homebudget.model.OperationCategory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Sergii on 18.08.2016.
 */
public interface OperationRepository {

    Operation findById(long id, long userId);

    Operation save(Operation operation, long userId, long accountId);

    boolean delete(long id, long userId);

    List<Operation> getAll(long userId);

    List<Operation> getAllForAccount(long userId, long accountId);

    List<Operation> getBetween(long userId, LocalDateTime start, LocalDateTime end);

    List<Operation> getAllOperationAfter(long accountId, Operation before);

    Operation getLastOperationBefore(long accountId, Operation after);

    Operation getLastOperationForAccount(long accountId);

    List<Operation> getOperationsForCategory(OperationCategory category, LocalDate start, LocalDate end);
}
