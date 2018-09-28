package com.crediline.utils;

import com.crediline.model.User;
import com.crediline.security.CustomUserDetails;
import com.crediline.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

@Component
public final class SessionUtils {

    @Inject
    private static UserService userService;

    public static final String getSessionId() {
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        String sessionId = session.getId();

        return sessionId;
    }

    public static synchronized User getCurrentUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getDomainUser();
    }
}
