package org.kalashnyk.homebudget.model.id;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Currency;

/**
 * Created by Sergii on 27.02.2017.
 */
@Getter
@Setter
public class FXRateId implements Serializable {
    private Currency baseCurrency;
    private Currency variableCurrency;
    private LocalDate date;
}
