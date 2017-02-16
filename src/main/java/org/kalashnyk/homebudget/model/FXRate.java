package org.kalashnyk.homebudget.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kalashnyk.homebudget.model.id.FXRateId;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Sergii on 01.02.2017.
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
        @ManyToOne
        @JoinColumn(name = "base_currency_id")
        private Currency baseCurrency;

        @Id
        @ManyToOne
        @JoinColumn(name = "variable_currency_id")
        private Currency variableCurrency;

        @Column(name = "rate")
        private BigDecimal rate;

        @Id
        @Column(name = "date")
        private LocalDate date;
}
