package org.kalashnyk.homebudget.service;

import org.kalashnyk.homebudget.model.OperationCategory;
import org.kalashnyk.homebudget.util.Pair;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergii on 10.03.2017.
 */
public interface ReportingService {

    List<Pair<OperationCategory, BigDecimal>> getExpensesByRootCaregories(long userId, LocalDate start, LocalDate end);

    List<Pair<OperationCategory, BigDecimal>> getIncomesByRootCaregories(long userId, LocalDate start, LocalDate end);

    List<Pair<OperationCategory, BigDecimal>> getOperationsByCategories(LocalDate start, LocalDate end, OperationCategory parent);
}
