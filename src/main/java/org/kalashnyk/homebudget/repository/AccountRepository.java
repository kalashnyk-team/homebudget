package org.kalashnyk.homebudget.repository;

import org.kalashnyk.homebudget.model.Account;

import java.util.List;

/**
 * Created by Sergii on 18.08.2016.
 */
public interface AccountRepository {

    Account findById(long id, long userId);

    Account save(Account account, long userId);

    boolean delete(long id, long userId);

    List<Account> getAll(long userId);
}