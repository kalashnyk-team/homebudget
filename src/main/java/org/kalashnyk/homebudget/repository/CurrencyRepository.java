package org.kalashnyk.homebudget.repository;

import org.kalashnyk.homebudget.model.Currency;

import java.util.List;

/**
 * Created by Sergii on 01.02.2017.
 */
public interface CurrencyRepository {
    Currency getCurrency(int id);
    Currency getCurrency(String name);
    List<Currency> getAllCurrencies();
}
