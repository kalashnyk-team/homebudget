package org.kalashnyk.homebudget.repository.jpa;

import org.kalashnyk.homebudget.model.FXRate;
import org.kalashnyk.homebudget.repository.FXRateRepository;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

/**
 * Created by Sergii on 27.02.2017.
 */
@Repository
public class JpaFXRateRepository implements FXRateRepository {
    @PersistenceContext
    private EntityManager em;


    @Override
    public void save(FXRate rate) {
        em.persist(rate);
    }

    @Override
    public FXRate get(Currency base, Currency variable, LocalDate date) {
        List<FXRate> rates = em.createQuery("SELECT rate FROM FXRate rate " +
                "WHERE rate.baseCurrency=:base " +
                "AND rate.variableCurrency=:variable " +
                "AND rate.date<=:date " +
                "ORDER BY rate.date DESC", FXRate.class)
                .setParameter("base", base)
                .setParameter("variable", variable)
                .setParameter("date", date)
                .setMaxResults(1)
                .getResultList();

        return DataAccessUtils.singleResult(rates);
    }
}
