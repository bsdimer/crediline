package com.crediline.mb;

import com.crediline.exceptions.ApplicationSpecifficExeption;
import com.crediline.model.Street;
import com.crediline.service.StreetService;
import com.crediline.utils.LocaleUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * Created by dimer on 5/16/14.
 */
@Component("streetBean")
@Scope("prototype")
public class StreetMB extends CommonManagedBean implements Serializable, ITabBean {
    @Inject
    private StreetService streetService;

    private Street street;
    private String name;

    @PostConstruct
    public void init() {
        street = new Street();
    }


    public void save() throws ApplicationSpecifficExeption {
        try {
            streetService.save(street);
        } catch (Exception e) {
            showGrowl(FacesMessage.SEVERITY_ERROR, LocaleUtils.getLocaliziedMessage("error.database"), e.getMessage());
        }
    }

    public void saveAndClose() throws ApplicationSpecifficExeption {
        try {
            streetService.save(street);
            if (getTabViewMB() != null) {
                getTabViewMB().closeByBean(this);
            }
        } catch (Exception e) {
            showGrowl(FacesMessage.SEVERITY_ERROR, LocaleUtils.getLocaliziedMessage("error.database"), e.getMessage());
        }
    }


    @Override
    public Boolean getClosable() {
        return true;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }
}
