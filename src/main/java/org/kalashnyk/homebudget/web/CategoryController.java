package org.kalashnyk.homebudget.web;

import org.kalashnyk.homebudget.AuthorizedUser;
import org.kalashnyk.homebudget.model.OperationCategory;
import org.kalashnyk.homebudget.service.HomeBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Sergii on 02.02.2017.
 */
@Controller
@RequestMapping(value = "/categories")
public class CategoryController {

    private HomeBudgetService budgetService;

    @Autowired
    public CategoryController(HomeBudgetService budgetService) {
        this.budgetService = budgetService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public String getCategories(Model model) {
        model.addAttribute("categories", budgetService.getAllOperationCategories(AuthorizedUser.id()));
        return "categories";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String saveCategoty(Model model) {
        model.addAttribute("category", new OperationCategory());
        model.addAttribute("categories", budgetService.getAllOperationCategories(AuthorizedUser.id()));
        return "category";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String saveCategory(@RequestParam String type,
                               @RequestParam(required = false) Long parentId,
                               @RequestParam String name) {
        OperationCategory parent = parentId == null ? null : budgetService.getOperationCategory(parentId, AuthorizedUser.id());
        OperationCategory.OperationType operationType = null;
        int level = 1;

        if (parent != null) {
            operationType = parent.getOperationType();
            level = parent.getLevel() + 1;
        } else {
            switch (type) {
                case "income":
                    operationType = OperationCategory.OperationType.INCOME;
                    break;
                case "expense":
                    operationType = OperationCategory.OperationType.EXPENSE;
                    break;
            }
        }

        OperationCategory category = OperationCategory.builder()
                .name(name)
                .level(level)
                .parent(parent)
                .operationType(operationType)
                .build();

        budgetService.saveOperationCategory(category, AuthorizedUser.id());

        return "redirect:/categories";
    }
}
