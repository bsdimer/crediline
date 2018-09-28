package com.crediline.mb;

import com.crediline.exceptions.ApplicationSpecifficExeption;
import com.crediline.model.Office;
import com.crediline.model.User;
import com.crediline.service.CommonValidator;
import com.crediline.service.OfficeService;
import com.crediline.service.UserService;
import com.crediline.utils.LocaleUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Component("userBean")
@Scope("prototype")
public class UserMB extends CommonManagedBean implements Serializable, ITabBean {
    private static final long serialVersionUID = 7217774566690677362L;

    @Inject
    private UserService userService;

    @Inject
    private OfficeService officeService;

    private User user;
    private Office selectedOffice;

    @PostConstruct
    public void init() {
        user = new User();
        if (getOffices() != null && getOffices().size() > 0) {
            selectedOffice = getOffices().get(0);
        }
    }


    @Override
    public Boolean getClosable() {
        return true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void save() throws ApplicationSpecifficExeption {
        try {
            if (CommonValidator.validateUser(user)) {
                user.setDisabled(false);
                userService.save(user);
                if (getTabViewMB() != null) {
                    getTabViewMB().closeByBean(this);
                }
            } else {
                showGrowl(FacesMessage.SEVERITY_ERROR, LocaleUtils.getLocaliziedMessage("error.input"), LocaleUtils.getLocaliziedMessage("error.input.detail"));
            }
        } catch (Exception e) {
            showGrowl(FacesMessage.SEVERITY_ERROR, LocaleUtils.getLocaliziedMessage("error.database"), e.getMessage());
        }
    }

    public List<Office> getOffices() {
        return officeService.findAll();
    }


    public Office getSelectedOffice() {
        return selectedOffice;
    }

    public void setSelectedOffice(Office selectedOffice) {
        this.selectedOffice = selectedOffice;
    }
}
