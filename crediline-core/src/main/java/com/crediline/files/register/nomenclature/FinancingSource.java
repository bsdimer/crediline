package com.crediline.files.register.nomenclature;

public enum FinancingSource {
	// STODO add other activity when needed
	// get them from instructions_reg22_bg.pdf file from src/main/resources
	SOURCE_90900("90900", "Other resources");

    private String value;
    private String description;
    
    FinancingSource(String value, String description) {
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
