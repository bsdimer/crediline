package com.crediline.files.register.nomenclature;

public enum RepaymentScheme {
	// STODO add other activity when needed
	// get them from instructions_reg22_bg.pdf file from src/main/resources
	SCHEME_701("701", "Annuities");

    private String value;
    private String description;
    
    RepaymentScheme(String value, String description) {
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
