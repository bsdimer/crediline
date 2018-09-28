package com.crediline.utils;

import com.crediline.model.*;
import org.springframework.stereotype.Component;

/**
 * Created by dimer on 2/3/14.
 */

@Component("data")
public class Data {


    private final static String[] countries;
    private final static String[] langs;

    static {
        countries = new String[3];
        countries[0] = "Bulgaria";
        langs = new String[2];
        langs[0] = "bg";
        langs[1] = "en";
    }

    public FlowType[] getFlowTypes() {
        return FlowType.values();
    }

    public MaritalStatus[] getStatuses() {
        return MaritalStatus.values();
    }

    public Education[] getEducations() {
        return Education.values();
    }

    public PhoneType[] getPhoneTypes() {
        return PhoneType.values();
    }

    public AddressType[] getAddressTypes() {
        return AddressType.values();
    }

    public Role[] getUserRoles() {
        return Role.values();
    }

    public CreditState[] getCreditStates() {
        return CreditState.values();
    }

    public InsuranceStatus[] getInsuranceStates() {
        return InsuranceStatus.values();
    }

    public PaymentMethod[] getPaymentMethods() {
        return PaymentMethod.values();
    }

    public TransactionReason[] getTransactonReaons() {
        return TransactionReason.values();
    }

    public DocumentTemplatePurpose[] getDocumentTemplatePurposes() {
        return DocumentTemplatePurpose.values();
    }

    public String[] getCountries() {
        return countries;
    }

    public String[] getLangs() {
        return langs;
    }

}
