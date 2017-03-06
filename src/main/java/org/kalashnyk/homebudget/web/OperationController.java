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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    public String getTransferForm(Model model) {
        long userId = AuthorizedUser.id();
        model.addAttribute("accounts", budgetService.getAllAccounts(userId));
        return "transfer";
    }

    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    public String createTransfer(@RequestParam Long fromAccountId,
                                 @RequestParam Long toAccountId,
                                 @RequestParam BigDecimal amount,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                 @RequestParam(required = false) String comment) {
        long userId = AuthorizedUser.id();
        Account fromAccount = budgetService.getAccount(fromAccountId, userId);
        Account toAccount = budgetService.getAccount(toAccountId, userId);

        Operation inTransfer = Operation.builder()
                .account(toAccount)
                .category(budgetService.getServiceCategory(OperationCategory.IN_TRANSFER))
                .amount(amount)
                .date(date.atStartOfDay())
                .remainOnAccount(BigDecimal.ZERO)
                .comment(comment)
                .build();

        Operation outTransfer = Operation.builder()
                .account(fromAccount)
                .category(budgetService.getServiceCategory(OperationCategory.OUT_TRANSFER))
                .amount(amount)
                .date(date.atStartOfDay())
                .remainOnAccount(BigDecimal.ZERO)
                .comment(comment)
                .build();

        budgetService.saveTransfer(outTransfer, inTransfer, userId, fromAccountId, toAccountId);

        return "redirect:/accounts";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getOperationList(@RequestParam(required = false) Long accountId,
                                   Model model) {
        if (accountId != null) {
            model.addAttribute("account", budgetService.getAccount(accountId, AuthorizedUser.id()));
            model.addAttribute("groupedOperations", budgetService.getOperationsForAccountGroupByDate(AuthorizedUser.id(), accountId));
        }
        return "operations";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createOperation(@RequestParam Long accId,
                                  @RequestParam Long categoryId,
                                  @RequestParam BigDecimal amount,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                  @RequestParam(required = false) String comment) {
        long userId = AuthorizedUser.id();
        Account account = budgetService.getAccount(accId, userId);
        OperationCategory category = budgetService.getOperationCategory(categoryId, userId);

        Operation operation = Operation.builder()
                .account(account)
                .category(category)
                .amount(amount)
                .date(date.atStartOfDay())
                .remainOnAccount(BigDecimal.ZERO)
                .amountInBaseCurrency(amount.setScale(2, RoundingMode.HALF_UP))
                .comment(comment)
                .build();

        budgetService.saveOperation(operation, userId, accId);

        return "redirect:/accounts";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String deleteOperation(@PathVariable(value = "id") Long id, Model model) {
        Account account = budgetService.getOperation(id, AuthorizedUser.id()).getAccount();
        budgetService.deleteOperation(id, AuthorizedUser.id());
        return "redirect:/operations?accountId=" + account.getId();
    }
}