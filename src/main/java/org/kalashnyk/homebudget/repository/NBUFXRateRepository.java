package org.kalashnyk.homebudget.repository;

import org.kalashnyk.homebudget.to.NBUFXRate;

import java.time.LocalDate;
import java.util.Currency;

/**
 * Created by Sergii on 03.03.2017.
 */
public interface NBUFXRateRepository {
    NBUFXRate get(Currency currency, LocalDate date);
}
