package com.crediline.files.register.nomenclature;

public enum InterestType {
	// STODO add other activity when needed
	// get them from instructions_reg22_bg.pdf file from src/main/resources
	TYPE_10("10", "Fixed rate");

    private String value;
    private String description;
    
    InterestType(String value, String description) {
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
