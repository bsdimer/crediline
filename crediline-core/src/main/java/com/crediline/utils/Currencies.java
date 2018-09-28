package com.crediline.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by dimer on 3/30/14.
 */
public class Currencies {

    public static Currency defaultCurrency = new Currency("BGL", "лв", "лева");

    public static final Map<Integer, Currency> currencies = new HashMap<Integer, Currency>() {{
        put(0, defaultCurrency);
        put(1, new Currency("USD", "$", "dollars"));
    }};

    public static Currency getCurrencty(Integer id) {
        if (currencies.containsKey(id)) {
            return currencies.get(id);
        } else {
            return defaultCurrency;
        }
    }

    public static Integer getCurrenctyId(Currency defaultCurrency) {
        Iterator iterator = currencies.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (defaultCurrency.equals(entry)) {
                return (Integer) entry.getKey();
            }
        }
        return 0;
    }
}
