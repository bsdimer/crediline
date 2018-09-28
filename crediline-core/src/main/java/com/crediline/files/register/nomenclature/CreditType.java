package com.crediline.files.register.nomenclature;

public enum CreditType {
	// STODO add other activity when needed
	// get them from instructions_reg22_bg.pdf file from src/main/resources
	TYPE_101("101", "Loan of any type");

    private String value;
    private String description;
    
    CreditType(String value, String description) {
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
