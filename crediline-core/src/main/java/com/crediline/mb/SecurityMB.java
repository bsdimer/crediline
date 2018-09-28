package com.crediline.mb;

import com.crediline.model.User;
import com.crediline.service.UserService;
import com.crediline.utils.LocaleUtils;
import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;

@Component("securityBean")
@Scope("prototype")
public class SecurityMB  implements Serializable {
    private static final long serialVersionUID = 5896341106784119931L;

    @Inject
    private UserService userService;

    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    // TODO: Transactional?
    public void changePassword() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(currentUsername);
        if (user.getPassword().equals(currentPassword)) {
            user.setPassword(newPassword);
            RequestContext.getCurrentInstance().execute("logout();");
        } else {
            FacesContext.getCurrentInstance().addMessage("changePassowrdMessages", new FacesMessage(FacesMessage.SEVERITY_ERROR, LocaleUtils.getLocaliziedMessage("change.password.current.error"), ""));
        }
    }
}
