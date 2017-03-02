package org.kalashnyk.homebudget.repository;

import org.kalashnyk.homebudget.model.User;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by user on 17.08.2016.
 */
public interface UserRepository {

    boolean delete(long id);

    List<User> getAll();

    User get(long id) throws DataAccessException;

    User save(User user) throws DataAccessException;

    User getByEmail(String email);
}
