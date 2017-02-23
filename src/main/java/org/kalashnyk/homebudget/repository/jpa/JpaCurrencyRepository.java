package org.kalashnyk.homebudget.repository.jpa;

import org.kalashnyk.homebudget.model.Currency;
import org.kalashnyk.homebudget.repository.CurrencyRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Sergii on 01.02.2017.
 */
@Repository
@Transactional
public class JpaCurrencyRepository implements CurrencyRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Currency getCurrency(int id) {
        return em.createQuery("SELECT c FROM Currency c WHERE c.id=:id", Currency.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public Currency getCurrency(String name) {
        return em.createQuery("SELECT c FROM Currency c WHERE c.name=:stringId", Currency.class)
                .setParameter("stringId", name)
                .getSingleResult();
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return em.createQuery("SELECT c FROM Currency c", Currency.class)
                .getResultList();
    }
}
