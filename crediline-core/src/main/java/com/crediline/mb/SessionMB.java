package com.crediline.mb;

import com.crediline.model.Office;
import com.crediline.model.Role;
import com.crediline.model.User;
import com.crediline.service.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;

@Component("sessionMB")
@Scope("session")
public class SessionMB implements Serializable, AuditorAware {
    private static final long serialVersionUID = 2116049527658932332L;

    @Inject
    private UserService userService;

    public String getCurrentRole() {
        User currentUser = getCurrentUser();
        if (currentUser != null) {
            return currentUser.getRole().toString();
        }
        return Role.ROLE_UNKNOWN.toString();
    }

    @Override
    public Object getCurrentAuditor() {
        return null;
    }

    public Office getCurrentOffice() {
        return getCurrentUser().getOffice();
    }

    public User getCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(currentUsername);
    }

    /*public String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }*/

    public void logout() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try {
            externalContext.redirect("j_spring_security_logout");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
