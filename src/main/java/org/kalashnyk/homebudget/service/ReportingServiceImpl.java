package org.kalashnyk.homebudget.service;

import org.kalashnyk.homebudget.model.Operation;
import org.kalashnyk.homebudget.model.OperationCategory;
import org.kalashnyk.homebudget.repository.OperationCategoryRepository;
import org.kalashnyk.homebudget.repository.OperationRepository;
import org.kalashnyk.homebudget.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

/**
 * Created by Sergii on 10.03.2017.
 */
@Service
public class ReportingServiceImpl implements ReportingService {
    private OperationCategoryRepository categoryRepository;
    private OperationRepository operationRepository;

    @Autowired
    public ReportingServiceImpl(OperationCategoryRepository categoryRepository, OperationRepository operationRepository) {
        this.categoryRepository = categoryRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public List<Pair<OperationCategory, BigDecimal>> getExpensesByRootCaregories(long userId, LocalDate start, LocalDate end) {
        List<OperationCategory> rootExpenseCategories = categoryRepository.getAllRootExpenseCategory(userId);
        return getOperationAmountByCategories(start, end, rootExpenseCategories);
    }

    @Override
    public List<Pair<OperationCategory, BigDecimal>> getIncomesByRootCaregories(long userId, LocalDate start, LocalDate end) {
        List<OperationCategory> rootIncomeCategories = categoryRepository.getAllRootIncomeCategory(userId);
        return getOperationAmountByCategories(start, end, rootIncomeCategories);
    }

    @Override
    public List<Pair<OperationCategory, BigDecimal>> getOperationsByCategories(LocalDate start, LocalDate end, OperationCategory parent) {
        List<Pair<OperationCategory, BigDecimal>> list = getOperationAmountByCategories(start, end, parent.getSubCategories());

        BigDecimal amount = getOperationsAmountForCategory(start, end, parent);
        if (amount.compareTo(BigDecimal.ZERO) != 0) {
            list.add(new Pair<>(parent, amount));
        }

        return list;
    }

    @Override
    public List<Pair<YearMonth, BigDecimal>> getExpensesByMonthes(long userId, LocalDate start, LocalDate end) {
        List<Pair<YearMonth, BigDecimal>> expensesByMonthes = new ArrayList<>();
        List<Operation> operations = operationRepository.getExpenses(userId, start, end);
        Map<YearMonth, BigDecimal> map = new TreeMap<>();
        for (Operation o : operations) {
            YearMonth yearMonth = YearMonth.from(o.getDate());
            map.put(yearMonth, map.getOrDefault(yearMonth, BigDecimal.ZERO).add(o.getAmountInBaseCurrency()));
        }
        map.forEach((yearMonth, value) -> expensesByMonthes.add(new Pair<>(yearMonth, value)));
        return expensesByMonthes;
    }

    private List<OperationCategory> parentWithChildren(OperationCategory parent, List<OperationCategory> list) {
        list.add(parent);
        List<OperationCategory> subCategories = parent.getSubCategories();
        if (!subCategories.isEmpty()) {
            for (OperationCategory category : subCategories) {
                parentWithChildren(category, list);
            }
        }
        return list;
    }

    private BigDecimal getOperationsAmountForCategory(LocalDate start, LocalDate end, OperationCategory category) {
        BigDecimal amount = BigDecimal.ZERO;
        List<Operation> allOperationsForCategory = new ArrayList<>();
        for (OperationCategory c : parentWithChildren(category, new ArrayList<>())) {
            allOperationsForCategory.addAll(operationRepository.getOperationsForCategory(c, start, end));
        }

        for (Operation o : allOperationsForCategory) {
            amount = amount.add(o.getAmountInBaseCurrency());
        }

        return amount;
    }

    private List<Pair<OperationCategory, BigDecimal>> getOperationAmountByCategories(LocalDate start, LocalDate end, List<OperationCategory> categories) {
        List<Pair<OperationCategory, BigDecimal>> list = new ArrayList<>();
        for (OperationCategory c : categories) {
            BigDecimal amount = getOperationsAmountForCategory(start, end, c);
            if (!amount.equals(BigDecimal.ZERO)) {
                list.add(new Pair<>(c, amount));
            }
        }
        list.sort(null);
        return list;
    }
}
