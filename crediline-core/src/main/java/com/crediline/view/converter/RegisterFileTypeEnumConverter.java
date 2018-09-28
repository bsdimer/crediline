package com.crediline.view.converter;

import com.crediline.mb.CreditRegisterReportMB.RegisterFileType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@Component("registerFileTypeConverter")
@Scope("request")
public class RegisterFileTypeEnumConverter implements Converter {

    private RegisterFileType[] types = RegisterFileType.values();

    @Override
    public String getAsString(FacesContext context, UIComponent component,
                              Object value) {
        if (value instanceof RegisterFileType) {
            return value.toString();
        }
        return null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
                              String value) {

        return findObject(value);
    }

    private RegisterFileType findObject(String value) {
        for (RegisterFileType type : types) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
