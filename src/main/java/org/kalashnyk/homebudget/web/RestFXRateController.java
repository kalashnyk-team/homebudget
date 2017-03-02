package org.kalashnyk.homebudget.web;

import org.kalashnyk.homebudget.AuthorizedUser;
import org.kalashnyk.homebudget.model.FXRate;
import org.kalashnyk.homebudget.model.Operation;
import org.kalashnyk.homebudget.repository.FXRateRepository;
import org.kalashnyk.homebudget.service.HomeBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

/**
 * Created by Sergii on 16.02.2017.
 */
@RestController
@RequestMapping(value = "/rest/rates")
public class RestFXRateController {

private HomeBudgetService budgetService;
    private FXRateRepository fxRateRepository;

    @Autowired
    public RestFXRateController(HomeBudgetService budgetService, FXRateRepository fxRateRepository) {
        this.budgetService = budgetService;
        this.fxRateRepository = fxRateRepository;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public FXRate getRate(@RequestParam String baseCurrency,
                                @RequestParam String variableCurrency,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {


        return fxRateRepository.get(Currency.getInstance(baseCurrency), Currency.getInstance(variableCurrency), date);
    }

    @RequestMapping(value = "/operations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Operation> getALLOperationsForAccount(@RequestParam Long accountId) {

        return budgetService.getAllOperationsForAccount(AuthorizedUser.id(),accountId);
    }
}
