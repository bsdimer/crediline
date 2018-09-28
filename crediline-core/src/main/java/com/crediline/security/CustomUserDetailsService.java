package com.crediline.security;

import com.crediline.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class CustomUserDetailsService implements UserDetailsService {

    public static Logger logger = Logger.getLogger(CustomUserDetailsService.class.getName());

    @Autowired
    private UserDao userService;

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        com.crediline.model.User domainUser = userService.findByUsername(login);

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new CustomUserDetails(domainUser,
                domainUser.getUsername(),
                domainUser.getPassword(),
                domainUser.getDisabled().equals(false),
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                getAuthorities(domainUser.getRole().toString())
        );

    }

    public Collection<? extends GrantedAuthority> getAuthorities(String role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }

    public List<String> getRoles(final String role) {
        return new ArrayList<String>() {
            {
                add(role);
            }
        };
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}