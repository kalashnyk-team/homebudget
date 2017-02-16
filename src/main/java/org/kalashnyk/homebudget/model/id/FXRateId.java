package org.kalashnyk.homebudget.model.id;

import lombok.Getter;
import lombok.Setter;
import org.kalashnyk.homebudget.model.Currency;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Sergii on 01.02.2017.
 */
@Getter
@Setter
public class FXRateId implements Serializable {
    private Currency baseCurrency;
    private Currency variableCurrency;
    private LocalDate date;
}
