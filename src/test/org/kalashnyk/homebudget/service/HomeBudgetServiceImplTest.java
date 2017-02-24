package org.kalashnyk.homebudget.service;

import org.junit.Test;

import org.kalashnyk.homebudget.repository.AccountRepository;
import org.kalashnyk.homebudget.repository.CurrencyRepository;
import org.kalashnyk.homebudget.repository.OperationCategoryRepository;
import org.kalashnyk.homebudget.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.Assert.*;

/**
 * Created by Sergii on 22.02.2017.
 */

@Service
public class HomeBudgetServiceImplTest {

    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    private OperationCategoryRepository categoryRepository;
    private CurrencyRepository currencyRepository;

    @Autowired
    public HomeBudgetServiceImplTest(AccountRepository accountRepository,
                                 OperationRepository operationRepository,
                                 OperationCategoryRepository categoryRepository,
                                 CurrencyRepository currencyRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.categoryRepository = categoryRepository;
        this.currencyRepository = currencyRepository;
    }

    @Test
    public void getOperationsForAccountGroupByDateTest() throws Exception {

    }

}