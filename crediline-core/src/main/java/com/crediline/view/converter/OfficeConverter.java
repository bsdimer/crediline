package com.crediline.view.converter;

import com.crediline.dao.OfficeDao;
import com.crediline.model.Office;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

/**
 * Created by dimer on 4/29/14.
 */
@Component("officeConverter")
@Scope("request")
public class OfficeConverter implements Converter {

    @Inject
    private OfficeDao officeService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        try {
            return getOfficeService().findOne(Long.parseLong(s));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o instanceof Office) {
            return ((Office) o).getId().toString();
        }
        return null;
    }

    public OfficeDao getOfficeService() {
        return officeService;
    }

    public void setOfficeService(OfficeDao officeService) {
        this.officeService = officeService;
    }
}