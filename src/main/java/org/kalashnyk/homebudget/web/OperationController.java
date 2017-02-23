package org.kalashnyk.homebudget.web;

import org.kalashnyk.homebudget.AuthorizedUser;
import org.kalashnyk.homebudget.model.Account;
import org.kalashnyk.homebudget.model.Operation;
import org.kalashnyk.homebudget.model.OperationCategory;
import org.kalashnyk.homebudget.service.HomeBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Sergii on 08.02.2017.
 */
@Controller
@RequestMapping(value = "/operations")
public class OperationController {
    private HomeBudgetService budgetService;

    @Autowired
    public OperationController(HomeBudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String getOperationForm(Model model) {
        long userId = AuthorizedUser.id();
        model.addAttribute("accounts", budgetService.getAllAccounts(userId));
        model.addAttribute("categories", budgetService.getAllOperationCategories(userId));
        return "operation";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createOperation(@RequestParam Long accId,
                                  @RequestParam Long categoryId,
                                  @RequestParam BigDecimal amount,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                                  Model model) {
        long userId = AuthorizedUser.id();
        Account account = budgetService.getAccount(accId, userId);
        OperationCategory category = budgetService.getOperationCategory(categoryId, userId);

        Operation operation = Operation.builder()
                .account(account)
                .category(category)
                .amount(amount)
                .date(date)
                .build();

        budgetService.saveOperation(operation, userId, accId);

        return "redirect:/accounts";
    }
}
