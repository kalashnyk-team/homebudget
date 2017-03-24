package org.kalashnyk.homebudget.web;


import org.kalashnyk.homebudget.model.FXRate;
import org.kalashnyk.homebudget.service.HomeBudgetService;
import org.kalashnyk.homebudget.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Currency;
import java.util.TimeZone;

/**
 * Created by Sergii on 16.02.2017.
 */
@RestController
@RequestMapping(value = "/rest/rates")
public class RestFXRateController {

    private HomeBudgetService budgetService;

    @Autowired
    public RestFXRateController(HomeBudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public FXRate getRate(@RequestParam String baseCurrency,
                          @RequestParam String variableCurrency,
                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        if (date == null || date.compareTo(LocalDate.now(ZoneId.of(TimeZone.getTimeZone("Europe/Kiev").getID()))) > 0)
            date = LocalDate.now(ZoneId.of(TimeZone.getTimeZone("Europe/Kiev").getID()));


        return budgetService.getNBUFXRate(
                Currency.getInstance(baseCurrency.toUpperCase()),
                Currency.getInstance(variableCurrency.toUpperCase()),
                date);
    }


}
