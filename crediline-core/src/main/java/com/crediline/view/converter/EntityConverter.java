package com.crediline.view.converter;

import com.crediline.dao.CredilineRepository;
import com.crediline.model.Identifiable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Created by peter on 7/26/14.
 */
public abstract class EntityConverter<T extends Identifiable> implements Converter {


    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        try {
            if (s.length() > 0) {
                Long id = Long.parseLong(s);
                return getRepository().findOne(id);
            }
        } catch (Exception e) {
            System.out.print("Error in convertion");
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        try {
            if (o instanceof Identifiable) {
                return ((Identifiable) o).getId().toString();
            }
        } catch (Exception e) {
            System.out.print("Error in convertion");
        }
        return null;
    }

    protected abstract CredilineRepository<T> getRepository();
}
