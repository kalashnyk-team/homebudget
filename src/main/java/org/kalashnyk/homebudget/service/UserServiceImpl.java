package org.kalashnyk.homebudget.service;

import org.kalashnyk.homebudget.AuthorizedUser;
import org.kalashnyk.homebudget.model.Account;
import org.kalashnyk.homebudget.model.OperationCategory;
import org.kalashnyk.homebudget.model.User;
import org.kalashnyk.homebudget.repository.AccountRepository;
import org.kalashnyk.homebudget.repository.OperationCategoryRepository;
import org.kalashnyk.homebudget.repository.UserRepository;
import org.kalashnyk.homebudget.util.UserUtil;
import org.kalashnyk.homebudget.util.exception.ExceptionUtil;
import org.kalashnyk.homebudget.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Created by Sergii on 15.02.2017.
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(UserUtil.prepareToSave(user));
    }

    @Override
    public void delete(long id) {
        ExceptionUtil.checkNotFoundWithId(userRepository.delete(id), id);
    }

    @Override
    public User get(long id) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(userRepository.get(id), id);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        Objects.requireNonNull(email, "Email must not be empty");
        return ExceptionUtil.checkNotFound(userRepository.getByEmail(email), "email=" + email);
    }

    @Cacheable("users")
    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    @Override
    public void update(User user) {
        userRepository.save(UserUtil.prepareToSave(user));
    }

    @Override
    public void enable(long id, boolean enable) {
        User user = get(id);
        user.setEnabled(enable);
        userRepository.save(user);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = userRepository.getByEmail(email.toLowerCase());
        if (u == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(u);
    }
}
