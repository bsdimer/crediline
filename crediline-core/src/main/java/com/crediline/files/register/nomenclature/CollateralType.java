package com.crediline.files.register.nomenclature;

public enum CollateralType {
	// STODO add other activity when needed
	// get them from instructions_reg22_bg.pdf file from src/main/resources
	TYPE_330("330", "Guarantee from another person under Article 143 of Decree N8 of the BNB"),
	TYPE_317("317" , "Pledge of personal collateral- underwriting, assuming debt");
	
    private String value;
    private String description;
    
    CollateralType(String value, String description) {
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
