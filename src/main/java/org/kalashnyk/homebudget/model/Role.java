package org.kalashnyk.homebudget.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Sergii on 17.08.2016.
 */
public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
