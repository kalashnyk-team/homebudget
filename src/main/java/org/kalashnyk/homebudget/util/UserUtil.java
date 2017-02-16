package org.kalashnyk.homebudget.util;

import org.kalashnyk.homebudget.model.User;

/**
 * Created by Sergii on 15.02.2017.
 */
public class UserUtil {

    public static User prepareToSave(User user) {
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
