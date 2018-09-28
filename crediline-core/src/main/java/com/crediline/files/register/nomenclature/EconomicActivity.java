package com.crediline.files.register.nomenclature;

public enum EconomicActivity {
	//STODO add other activity when needed
	//get them from instructions_reg22_bg.pdf file from src/main/resources
	ACTIVITY_00("00", "Physical person");

    private String value;
    private String description;
    
    EconomicActivity(String value, String description) {
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
