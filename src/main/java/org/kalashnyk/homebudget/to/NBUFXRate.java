package org.kalashnyk.homebudget.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Sergii on 03.03.2017.
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NBUFXRate {
    @JsonProperty(value = "r030") private int currencyDigitCode;
    @JsonProperty(value = "txt") private String name;
    private BigDecimal rate;
    @JsonProperty(value = "cc") private String currencyCode;
    @JsonProperty(value = "exchangedate") private String date;

    @Builder
    public NBUFXRate(int currencyDigitCode, String name, BigDecimal rate, String currencyCode, String date) {
        this.currencyDigitCode = currencyDigitCode;
        this.name = name;
        this.rate = rate;
        this.currencyCode = currencyCode;
        this.date = date;
    }
}
