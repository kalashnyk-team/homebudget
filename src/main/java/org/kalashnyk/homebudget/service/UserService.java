package org.kalashnyk.homebudget.service;

import org.kalashnyk.homebudget.model.User;
import org.kalashnyk.homebudget.util.exception.NotFoundException;

import java.util.List;

/**
 * Created by Sergii on 15.02.2017.
 */
public interface UserService {

    User save(User user);

    void delete(long id) throws NotFoundException;

    User get(long id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    List<User> getAll();

    void update(User user);

    void enable(long id, boolean enable);
}
