package org.kalashnyk.homebudget.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Currency;

/**
 * Created by Sergii on 04.03.2017.
 */
@RestController
@RequestMapping(value = "/currencies")
public class RestCurrencyController {

    @RequestMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Currency getCurrency(@PathVariable(value = "code") String currencyCode) {
        return Currency.getInstance(currencyCode);
    }
}
