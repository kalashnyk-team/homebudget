package org.kalashnyk.homebudget.repository.jpa;

import org.kalashnyk.homebudget.model.Account;
import org.kalashnyk.homebudget.model.Operation;
import org.kalashnyk.homebudget.model.OperationCategory;
import org.kalashnyk.homebudget.repository.OperationRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by Sergii on 26.08.2016.
 */
@Repository
@Transactional
public class JpaOperationRepository implements OperationRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Operation findById(long id, long userId) {
        List<Operation> operations = em.createQuery("SELECT o FROM Operation o WHERE o.id=:id AND o.account.owner.id=:userId", Operation.class)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getResultList();
        return DataAccessUtils.singleResult(operations);
    }

    @Override
    public Operation save(Operation operation, long userId, long accountId) {
        if (!operation.isNew() && findById(operation.getId(), userId) == null) {
            return null;
        }
        operation.setAccount(em.getReference(Account.class, accountId));

        if (operation.isNew()) {
            em.persist(operation);
            return operation;
        } else {
            return em.merge(operation);
        }
    }

    @Override
    @Transactional
    public boolean delete(long id, long userId) {
        return em.createNativeQuery("DELETE FROM operations o " +
                "WHERE o.id=:id " +
                "AND o.acc_id IN (SELECT a.id FROM accounts a WHERE a.user_id=:userId)")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
        /*return em.createQuery("DELETE FROM Operation o WHERE o.id=:id AND o.account.owner.id=:userId")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;*/
    }

    @Override
    public List<Operation> getAll(long userId) {
        return em.createQuery("SELECT o FROM Operation o WHERE o.account.owner.id=:userId", Operation.class)
                .setParameter("userId", userId)
                .getResultList();
    }


    @Override
    public List<Operation> getAllForAccount(long userId, long accountId) {
        return em.createQuery("SELECT o FROM Operation o LEFT JOIN FETCH o.account " +
                "WHERE o.account.owner.id=:userId AND o.account.id=:accountId ORDER BY o.date, o.id", Operation.class)
                .setParameter("userId", userId)
                .setParameter("accountId", accountId)
                .getResultList();
    }

    @Override
    public List<Operation> getBetween(long userId, LocalDateTime start, LocalDateTime end) {
        return em.createQuery(
                "SELECT o FROM Operation o " +
                        "WHERE o.account.owner.id=:userId " +
                        "AND o.date BETWEEN :start AND :end", Operation.class)
                .setParameter("userId", userId)
                .setParameter("start", start)
                .setParameter("end", end)
                .getResultList();
    }

    @Override
    public List<Operation> getAllOperationAfter(long accountId, Operation before) {
        return em.createNativeQuery(
                "SELECT * FROM " +
                        "(SELECT * FROM operations o WHERE o.acc_id=:accountId AND o.date>:beforeDate " +
                        "UNION " +
                        "SELECT * FROM operations o WHERE o.acc_id=:accountId AND o.date=:beforeDate AND o.id>:id) u " +
                        "ORDER BY u.date, u.id", Operation.class)
                .setParameter("accountId", accountId)
                .setParameter("beforeDate", before.getDate())
                .setParameter("id", before.isNew() ? Long.MAX_VALUE : before.getId())
                .getResultList();
    }

    @Override
    public Operation getLastOperationBefore(long accountId, Operation after) {
        List<Operation> operations = em.createNativeQuery("SELECT * FROM operations o " +
                "WHERE o.acc_id=:accountId " +
                "AND o.date<=:afterDate " +
                "AND o.id<:id " +
                "ORDER BY date DESC, id DESC LIMIT 1", Operation.class)
                .setParameter("accountId", accountId)
                .setParameter("afterDate", after.getDate())
                .setParameter("id", after.getId())
                .getResultList();
        return DataAccessUtils.singleResult(operations);
    }

    @Override
    public Operation getLastOperationForAccount(long accountId) {
        List<Operation> operations = em.createQuery("SELECT o FROM Operation o " +
                "WHERE o.account.id=:accountId " +
                "ORDER BY o.date DESC, o.id DESC", Operation.class)
                .setParameter("accountId", accountId)
                .setMaxResults(1)
                .getResultList();
        return DataAccessUtils.singleResult(operations);
    }

    @Override
    public List<Operation> getOperationsForCategory(OperationCategory category, LocalDate start, LocalDate end) {
        return em.createQuery("SELECT o FROM  Operation o WHERE o.category=:category AND o.date BETWEEN :start AND :end", Operation.class)
                .setParameter("category", category)
                .setParameter("start", start.atStartOfDay())
                .setParameter("end", end.atTime(LocalTime.MAX))
                .getResultList();
    }

    @Override
    public List<Operation> getExpenses(long userId, LocalDate start, LocalDate end) {
        return em.createQuery("SELECT o FROM Operation o " +
                "WHERE o.category.operationType=:operationType " +
                "AND o.account.owner.id=:userId " +
                "AND o.date BETWEEN :start AND :end", Operation.class)
                .setParameter("operationType", OperationCategory.OperationType.EXPENSE)
                .setParameter("userId", userId)
                .setParameter("start", start.atStartOfDay())
                .setParameter("end", end.atTime(LocalTime.MAX))
                .getResultList();

    }
}