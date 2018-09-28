package com.crediline.utils;

import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by dimer on 3/23/14.
 */
@Component
public class LocaleUtils {

    private static Map<String, Locale> locales = new HashMap<String, Locale>();
    public static String defaultLocale = "bg_BG";

    // ToDo:
    public static Locale getCurrentLocale() {
        if (locales.get(defaultLocale) == null) {
            locales.put(defaultLocale, new Locale("bg", "BG"));
        }

        return locales.get(defaultLocale);
    }

    // ToDo: must generate locale for other languages not included in the standard Locales
    public static Locale getLocale(String localeString) {
        return getCurrentLocale();
    }

    public static ResourceBundle getMessageBean() {
        ResourceBundle bundle = null;
        if (bundle == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            bundle = context.getApplication().getResourceBundle(context, "msg");
        }
        return bundle;
    }

    public static String getLocaliziedMessage(String key) {
        String message = getMessageBean().getString(key);
        if (message != null && message.length() > 0) {
            return message;
        }
        return key;
    }


}
