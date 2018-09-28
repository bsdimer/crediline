package com.crediline.view.converter;

import com.crediline.model.Person;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by dimer on 4/6/14.
 */
@FacesConverter(value = "person2EgnFirstConverter")
public class Person2EGNFirstConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o instanceof Person) {
            return ((Person) o).getEgn();
        }
        return null;
    }
}
