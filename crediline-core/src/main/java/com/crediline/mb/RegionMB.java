package com.crediline.mb;

import com.crediline.exceptions.ApplicationSpecifficExeption;
import com.crediline.model.Region;
import com.crediline.service.CommonValidator;
import com.crediline.service.RegionService;
import com.crediline.utils.LocaleUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import java.io.Serializable;

@Component("regionBean")
@Scope("prototype")
public class RegionMB extends CommonManagedBean implements Serializable, ITabBean {
    private static final long serialVersionUID = 5435657665397135728L;

    @Inject
    private RegionService regionService;

    private Region region;

    @PostConstruct
    public void init() {
        region = new Region();
    }


    @Override
    public Boolean getClosable() {
        return true;
    }


    public void save() throws ApplicationSpecifficExeption {
        try {
            if (CommonValidator.validateRegion(region)) {
                regionService.save(region);
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
}
