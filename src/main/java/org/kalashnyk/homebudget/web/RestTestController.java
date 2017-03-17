package org.kalashnyk.homebudget.web;

import org.kalashnyk.homebudget.AuthorizedUser;
import org.kalashnyk.homebudget.model.OperationCategory;
import org.kalashnyk.homebudget.service.HomeBudgetService;
import org.kalashnyk.homebudget.service.ReportingService;
import org.kalashnyk.homebudget.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
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

    @RequestMapping(value = "/expenses", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List expensesByCategories(@RequestParam String groupBy,
                                     @RequestParam(required = false) Long parentId,
                                     @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                     @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        if (start == null)
            start = LocalDate.from(YearMonth.now().atDay(1));

        if (end == null)
            end = LocalDate.now();

        switch (groupBy) {
            case "category": {
                if (parentId != null) {
                    return reportingService.getOperationsByCategories(start, end, budgetService.getOperationCategory(parentId, AuthorizedUser.id()));
                } else {
                    return reportingService.getExpensesByRootCaregories(AuthorizedUser.id(), start, end);
                }
            }
            case "month": {
                return reportingService.getExpensesByMonthes(AuthorizedUser.id(), start, end);
            }
            default:
                return null;
        }
    }
}
