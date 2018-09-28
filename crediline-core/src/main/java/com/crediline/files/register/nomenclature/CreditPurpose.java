package com.crediline.files.register.nomenclature;

public enum CreditPurpose {
	// STODO add other activity when needed
	// get them from instructions_reg22_bg.pdf file from src/main/resources
	TYPE_208("208", "Consumer loans for physical persons");

    private String value;
    private String description;
    
    CreditPurpose(String value, String description) {
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
