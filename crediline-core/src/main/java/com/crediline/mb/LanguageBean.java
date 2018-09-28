package com.crediline.mb;

import org.springframework.stereotype.Component;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@Component("language")
@SessionScoped
public class LanguageBean implements Serializable {
    private static final long serialVersionUID = 2936571853322538301L;

    private String localeCode;

    private static Map<String, Object> countries;

    static {
        countries = new LinkedHashMap<String, Object>();
        countries.put("English", Locale.ENGLISH); //label, value
        countries.put("Chinese", Locale.SIMPLIFIED_CHINESE);
    }

    public Map<String, Object> getCountriesInMap() {
        return countries;
    }


    public String getLocaleCode() {
        return localeCode;
    }


    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    //value change event listener
    public void countryLocaleCodeChanged(ValueChangeEvent e) {

        String newLocaleValue = e.getNewValue().toString();

        //loop country map to compare the locale code
        for (Map.Entry<String, Object> entry : countries.entrySet()) {

            if (entry.getValue().toString().equals(newLocaleValue)) {

                FacesContext.getCurrentInstance()
                        .getViewRoot().setLocale((Locale) entry.getValue());

            }
        }
    }

}