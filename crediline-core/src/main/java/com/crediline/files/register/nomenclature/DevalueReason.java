package com.crediline.files.register.nomenclature;

public enum DevalueReason {
	REASON_801("801", "Delay"),
	REASON_802("802", "Deterioration in the financial condition"),
	REASON_803("803", "Delay and deterioration of financial position"),
	REASON_804("804", "Other");

    private String value;
    private String description;
    
    DevalueReason(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
    
    public String getDescription() {
        return description;
    }
}
