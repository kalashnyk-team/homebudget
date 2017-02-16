package org.kalashnyk.homebudget.web;

import org.kalashnyk.homebudget.AuthorizedUser;
import org.kalashnyk.homebudget.model.Account;
import org.kalashnyk.homebudget.service.HomeBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * Created by Sergii on 16.02.2017.
 */
@RestController
@RequestMapping(value = "/rest/accounts")
public class RestAccountController {
    private HomeBudgetService budgetService;

    @Autowired
    public RestAccountController(HomeBudgetService budgetService) {
        this.budgetService = budgetService;
    }


    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> showAccountList(Model model) {
        return budgetService.getAllAccounts(AuthorizedUser.id());
    }
}
