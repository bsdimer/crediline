package com.crediline.files.register.nomenclature;

public enum CreditCategory {
	CATEGORY_401("401", "Normal"),
	CATEGORY_402("402", "Watched"),
	CATEGORY_403("403", "Unattended"),
	CATEGORY_404("404", "Wasted");

    private String value;
    private String description;
    
    CreditCategory(String value, String description) {
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
