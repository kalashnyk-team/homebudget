package org.kalashnyk.homebudget.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kalashnyk.homebudget.model.id.FXRateId;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

/**
 * Created by Sergii on 27.02.2017.
 */
@Entity
@Table(name = "fx_rates")
@IdClass(FXRateId.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FXRate {
    @Id
    @Column(name = "base_currency_code")
    private Currency baseCurrency;

    @Id
    @Column(name = "variable_currency_code")
    private Currency variableCurrency;

    @Column(name = "rate")
    private BigDecimal rate;

    @Id
    @Column(name = "date")
    private LocalDate date;
}
