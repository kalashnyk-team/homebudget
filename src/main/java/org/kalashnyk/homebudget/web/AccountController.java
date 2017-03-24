package org.kalashnyk.homebudget.web;


import org.kalashnyk.homebudget.AuthorizedUser;
import org.kalashnyk.homebudget.model.Account;
import org.kalashnyk.homebudget.model.User;
import org.kalashnyk.homebudget.service.HomeBudgetService;
import org.kalashnyk.homebudget.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;

/**
 * Created by Sergii on 25.01.2017.
 */
@Controller
@RequestMapping(value = {"/accounts"})
public class AccountController {

    private HomeBudgetService budgetService;
    private UserService userService;

    @Autowired
    public AccountController(HomeBudgetService budgetService, UserService userService) {
        this.budgetService = budgetService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showAccountList(Model model) {
        model.addAttribute("currencies", Arrays.asList(Currency.getInstance("UAH"), Currency.getInstance("USD"), Currency.getInstance("EUR")));
        model.addAttribute("accountTypes", Account.types());
        model.addAttribute("groupedAccounts", budgetService.getAccountsGroupByType(AuthorizedUser.id()));
        return "accounts";
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public String saveAccount(@PathVariable Long accountId, Model model) {
        Account account = budgetService.getAccount(accountId, AuthorizedUser.id());
        model.addAttribute("account", account);
        model.addAttribute("currencies", Arrays.asList(Currency.getInstance("UAH"), Currency.getInstance("USD"), Currency.getInstance("EUR")));
        model.addAttribute("accountTypes", Account.Type.values());
        return "account";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String saveAccount(@RequestParam(required = false) Long id,
                              @RequestParam String name,
                              @RequestParam String currencyCode,
                              @RequestParam BigDecimal amount,
                              @RequestParam String type) {
        Currency currency = Currency.getInstance(currencyCode);
        User owner = userService.get(AuthorizedUser.id());

        Account.Type accType = null;
        for (Account.Type t : Account.Type.values()) {
            if (t.name().equals(type))
                accType = t;
        }

        Account account = Account.builder()
                .id(id)
                .name(name)
                .currency(currency)
                .amount(amount)
                .owner(owner)
                .type(accType)
                .build();

        budgetService.saveAccount(account, AuthorizedUser.id());
        return "redirect:/accounts";
    }
}