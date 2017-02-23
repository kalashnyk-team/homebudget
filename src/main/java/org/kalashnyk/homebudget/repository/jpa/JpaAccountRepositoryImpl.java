package org.kalashnyk.homebudget.repository.jpa;

import org.kalashnyk.homebudget.model.Account;
import org.kalashnyk.homebudget.model.User;
import org.kalashnyk.homebudget.repository.AccountRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Sergii on 25.08.2016.
 */
@Repository
@Transactional
public class JpaAccountRepositoryImpl implements AccountRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Account findById(long id, long userId) {
        List<Account> accounts = em.createQuery("SELECT a FROM Account a WHERE a.id=:id AND a.owner.id=:userId", Account.class)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getResultList();
        return DataAccessUtils.singleResult(accounts);
    }

    @Override
    public Account save(Account account, long userId) {
        if (!account.isNew() && findById(account.getId(), userId) == null) {
            return null;
        }
        account.setOwner(em.getReference(User.class, userId));
        if (account.isNew()) {
            em.persist(account);
            return account;
        } else {
            return em.merge(account);
        }
    }

    @Override
    public boolean delete(long id, long userId) {
        return em.createQuery("DELETE FROM Account a WHERE a.id=:id AND a.owner.id=:userId")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public List<Account> getAll(long userId) {
        return em.createQuery("SELECT a FROM Account a WHERE a.owner.id=:userId AND a.type IN :types", Account.class)
                .setParameter("userId", userId)
                .setParameter("types", Account.types())
                .getResultList();
    }
}