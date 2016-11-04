package org.kalashnyk.homebudget;

/**
 * Created by Sergii on 27.09.2016.
 */
public class AuthorizedUser {
    public static long id = 1;

    public static long id() {
        return id;
    }

    public static void setId(long id) {
        AuthorizedUser.id = id;
    }
}
