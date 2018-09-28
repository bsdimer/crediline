package com.crediline.dataimport;

import com.crediline.dataimport.impl.CityImporter;
import com.crediline.dataimport.impl.CreditImporter;
import com.crediline.dataimport.impl.PersonImporter;

/**
 * Created by dimer on 4/29/14.
 */
public class ImporterFactory {

    public static synchronized DataImporter getImporter(ImporterType type) {

        if (type.equals(ImporterType.CITIES)) {
            return new CityImporter();
        }

        if (type.equals(ImporterType.PERSONS)) {
            return new PersonImporter();
        }

        if (type.equals(ImporterType.CREDITS)) {
            return new CreditImporter();
        }

        return null;
    }
}
