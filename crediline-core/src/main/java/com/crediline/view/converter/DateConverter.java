package com.crediline.view.converter;

import org.joda.time.LocalDateTime;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by dimer on 4/6/14.
 */
@FacesConverter(value = "dateConverter")
public class DateConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(s);
            LocalDateTime dateTime = LocalDateTime.fromDateFields(date);
            return dateTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o instanceof LocalDateTime) {
            StringBuilder sb = new StringBuilder();
            sb.append(((LocalDateTime) o).getDayOfMonth()).append("/");
            sb.append(((LocalDateTime) o).getMonthOfYear()).append("/");
            sb.append(((LocalDateTime) o).getYear());
            return sb.toString();
        }
        return null;
    }
}
