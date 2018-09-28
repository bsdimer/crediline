package com.crediline.files.register.nomenclature;

public enum GracePeriod {
	// STODO add other activity when needed
	// get them from instructions_reg22_bg.pdf file from src/main/resources
	P_90("90", "No grace period");

    private String value;
    private String description;
    
    GracePeriod(String value, String description) {
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
