package org.kalashnyk.homebudget.repository.jpa;

import org.kalashnyk.homebudget.model.Account;
import org.kalashnyk.homebudget.model.Operation;
import org.kalashnyk.homebudget.model.OperationCategory.*;
import org.kalashnyk.homebudget.repository.OperationRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Sergii on 26.08.2016.
 */
@Repository
public class JpaOperationRepositoryImpl implements OperationRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Operation findById(long id, long userId) {
        List<Operation> operations = em.createQuery("SELECT o FROM Operation o WHERE o.id=:id AND (o.debitAccount.owner.id=:userId OR o.creditAccount.owner.id=:userId)", Operation.class)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getResultList();
        return DataAccessUtils.singleResult(operations);
    }

    @Override
    public Operation save(Operation operation, long userId, long debitAccountId, long creditAccountId) {
        if (!operation.isNew() && findById(operation.getId(), userId) == null) {
            return null;
        }

        if (operation.getCategory().getOperationType() == OperationType.EXPENSE) {
            operation.setCreditAccount(em.getReference(Account.class, creditAccountId));
        } else if (operation.getCategory().getOperationType() == OperationType.INCOME) {
            operation.setDebitAccount(em.getReference(Account.class, debitAccountId));
        } else if (operation.getCategory().getOperationType() == OperationType.TRANSFER) {
            operation.setCreditAccount(em.getReference(Account.class, creditAccountId));
            operation.setDebitAccount(em.getReference(Account.class, debitAccountId));
        }

        if (operation.isNew()) {
            em.persist(operation);
            return operation;
        } else {
            return em.merge(operation);
        }
    }

    @Override
    public boolean delete(long id, long userId) {
        return em.createQuery("DELETE FROM Operation o WHERE o.id=:id AND (o.debitAccount.owner.id=:userId OR o.creditAccount.owner.id=:userId)")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public List<Operation> getAll(long userId) {
        return em.createQuery("SELECT o FROM Operation o WHERE o.debitAccount.owner.id=:userId OR o.creditAccount.owner.id=:userId", Operation.class)
                .setParameter("userId", userId)
                .getResultList();
    }


    @Override
    public List<Operation> getAllForAccount(long userId, long accountId) {
        return em.createQuery(
                "SELECT o FROM Operation o " +
                        "WHERE (o.debitAccount.owner.id=:userId OR o.creditAccount.owner.id=:userId) " +
                        "AND (o.debitAccount.id=:accountId OR o.creditAccount.id=:accountId)", Operation.class)
                .setParameter("userId", userId)
                .setParameter("accountId", accountId)
                .getResultList();
    }

    @Override
    public List<Operation> getBetween(long userId, LocalDateTime start, LocalDateTime end) {
        return em.createQuery(
                "SELECT o FROM Operation o " +
                        "WHERE (o.debitAccount.owner.id=:userId OR o.creditAccount.owner.id=:userId)" +
                        "AND o.date BETWEEN :start AND :end", Operation.class)
                .setParameter("userId", userId)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }
}