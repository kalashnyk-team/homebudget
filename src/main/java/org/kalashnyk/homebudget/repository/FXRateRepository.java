package org.kalashnyk.homebudget.repository;

import org.kalashnyk.homebudget.model.FXRate;

import java.time.LocalDate;
import java.util.Currency;

/**
 * Created by Sergii on 27.02.2017.
 */
public interface FXRateRepository {
    void save(FXRate rate);
    FXRate get(Currency base, Currency variable, LocalDate date);
}
