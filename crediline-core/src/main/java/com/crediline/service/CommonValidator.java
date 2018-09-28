package com.crediline.service;

import com.crediline.model.*;

/**
 * Created by dimer on 2/20/14.
 */
// TODO: Replace with bean validation
public class CommonValidator {

    public synchronized static Boolean validateUser(User user) {
        return true;
    }

    public synchronized static Boolean validatePerson(Person person) {
        if (person.getEgn() == null) {
            return false;
        }

        if (person.getEgn().length() != 10) {
            return false;
        }

        if (person.getLkNo() == null) {
            return false;
        }

        return true;
    }

    public synchronized static Boolean validateAddress(Address address) {
        return true;
    }

    public synchronized static Boolean validatePhone(Phone phone) {
        return true;
    }

    public static boolean validateDocument(Document entity) {
        return true;
    }

    public static boolean validateDocumentTemplate(DocumentTemplate entity) {
        return true;
    }

    public static boolean validateDocumentParameter(DocumentParameter entity) {
        return true;
    }

    public static boolean validateOffice(Office entity) {
        return true;
    }

    public static boolean validateRegion(Region region) {
        return true;
    }


}
