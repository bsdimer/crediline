package com.crediline.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by dimer on 8/9/14.
 */
public class CustomUserDetails extends User {

    private com.crediline.model.User domainUser;

    public CustomUserDetails(com.crediline.model.User domainUser, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.domainUser = domainUser;
    }

    public CustomUserDetails(com.crediline.model.User domainUser, String username, String password, boolean enabled, boolean accountNonExpired,
                             boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.domainUser = domainUser;
    }

    public com.crediline.model.User getDomainUser() {
        return domainUser;
    }

    public void setDomainUser(com.crediline.model.User domainUser) {
        this.domainUser = domainUser;
    }
}
