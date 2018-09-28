package com.crediline.mb;

import com.crediline.exceptions.ApplicationSpecifficExeption;
import com.crediline.model.*;
import com.crediline.service.OfficeService;
import com.crediline.service.RegionService;
import com.crediline.utils.LocaleUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import java.io.Serializable;

@Component("officeBean")
@Scope("prototype")
public class OfficeMB extends CommonManagedBean implements Serializable, ITabBean {
    private static final long serialVersionUID = 2433453976797128627L;

    @Inject
    private RegionService regionService;

    @Inject
    private OfficeService officeService;

    private Office office;
    private Address address;
    private Phone phone;
    private Region region;
    private Person manager;

    @PostConstruct
    public void init() {
        address = new Address();
        phone = new Phone();
        office = new Office();
        office.setAddress(address);
        region = regionService.findOne(1L);
    }


    @Override
    public Boolean getClosable() {
        return true;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
        if (this.office.getAddress() == null) {
            office.setAddress(new Address());
        }
    }

    public void addPhone2Office() {
        office.getPhones().add(phone);
        flushPhone();
    }

    public void removePhone(ActionEvent event) {
        Phone selectedPhone = (Phone) event.getComponent().getAttributes().get("parameter");
        try {
            if (office.getPhones().contains(selectedPhone)) {
                office.getPhones().remove(selectedPhone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void flushPhone() {
        phone = new Phone();
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public void save() throws ApplicationSpecifficExeption {
        try {
            officeService.save(office);
        } catch (Exception e) {
            showGrowl(FacesMessage.SEVERITY_ERROR, LocaleUtils.getLocaliziedMessage("error.database"), e.getMessage());
        }
    }

    public void saveAndClose() throws ApplicationSpecifficExeption {
        try {
            officeService.save(office);
            if (getTabViewMB() != null) {
                getTabViewMB().closeByBean(this);
            }

        } catch (Exception e) {
            showGrowl(FacesMessage.SEVERITY_ERROR, LocaleUtils.getLocaliziedMessage("error.database"), e.getMessage());
        }
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Person getManager() {
        return manager;
    }

    public void setManager(Person manager) {
        this.manager = manager;
    }
}
