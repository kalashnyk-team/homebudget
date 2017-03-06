package org.kalashnyk.homebudget.util;

import org.kalashnyk.homebudget.model.FXRate;
import org.kalashnyk.homebudget.to.NBUFXRate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

/**
 * Created by Sergii on 03.03.2017.
 */
public class FXRateUtil {
    static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static FXRate fromNBUFXRate(NBUFXRate nbufxRate) {
        return FXRate.builder()
                .baseCurrency(Currency.getInstance(nbufxRate.getCurrencyCode()))
                .variableCurrency(Currency.getInstance("UAH"))
                .rate(nbufxRate.getRate())
                .date(LocalDate.parse(nbufxRate.getDate(), fmt))
                .build();
        /*return FXRate.builder()
                .baseCurrency(Currency.getInstance(nbufxRate.getCc()))
                .variableCurrency(Currency.getInstance("UAH"))
                .rate(nbufxRate.getRate())
                .date(LocalDate.parse(nbufxRate.getExchangedate(), fmt))
                .build();*/
    }
}
