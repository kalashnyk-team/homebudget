package org.kalashnyk.homebudget.repository;

import org.kalashnyk.homebudget.to.NBUFXRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

/**
 * Created by Sergii on 03.03.2017.
 */
@Repository
public class NBUFXRateRepositoryImpl implements NBUFXRateRepository {
    private RestTemplate restTemplate;
    public DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd");
    private String urlPattern = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=%s&date=%s&json";

    @Autowired
    public NBUFXRateRepositoryImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public NBUFXRate get(Currency currency, LocalDate date) {
        final String uri = String.format(urlPattern, currency.getCurrencyCode(), fmt.format(date));
        NBUFXRate[] nbufxRate = restTemplate.getForObject(uri, NBUFXRate[].class);
        return nbufxRate[0];
    }
}