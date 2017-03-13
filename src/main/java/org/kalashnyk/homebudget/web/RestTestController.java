package org.kalashnyk.homebudget.web;

import org.kalashnyk.homebudget.AuthorizedUser;
import org.kalashnyk.homebudget.service.HomeBudgetService;
import org.kalashnyk.homebudget.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;
import java.util.List;


/**
 * Created by Sergii on 10.03.2017.
 */
@RestController
@RequestMapping(value = "/rest")
public class RestTestController {

    private HomeBudgetService budgetService;
    private ReportingService reportingService;

    @Autowired
    public RestTestController(HomeBudgetService budgetService, ReportingService reportingService) {
        this.budgetService = budgetService;
        this.reportingService = reportingService;
    }

    @RequestMapping(value = "/expenses/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public List expensesByCategories(@RequestParam(required = false) Long parentId) {
        LocalDate today = LocalDate.now();
        if (parentId != null) {
            return reportingService.getOperationsByCategories(LocalDate.of(today.getYear(), today.getMonth(), 1), today, budgetService.getOperationCategory(parentId, AuthorizedUser.id()));
        } else {
            return reportingService.getExpensesByRootCaregories(AuthorizedUser.id(), LocalDate.of(today.getYear(), today.getMonth(), 1), today);
        }
    }
}
