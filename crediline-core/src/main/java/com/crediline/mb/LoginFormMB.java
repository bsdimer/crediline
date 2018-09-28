package com.crediline.mb;

import com.crediline.model.Office;
import com.crediline.service.OfficeService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component("loginformMB")
@Scope("prototype")
public class LoginFormMB extends CommonManagedBean implements Serializable, ITabBean {
    private static final long serialVersionUID = 2116049527658932332L;

    @Inject
    private OfficeService officeService;
    private String theme;

    @PostConstruct
    public void init() {

    }

    @Override
    public Boolean getClosable() {
        return true;
    }

    public List<String> getAllOffices() {
        List<Office> offices = officeService.findAll();
        List<String> officeNames = new ArrayList<>();
        for (Office office : offices) {
            officeNames.add(office.getName());
        }
        return officeNames;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }
}
