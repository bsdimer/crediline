package com.crediline.files.register.nomenclature;

public enum BorrowerType {
    TYPE_1("1", "Local physical person"), 
    TYPE_2("2", "Juridical or not juridical person"), 
    TYPE_3("3", "Foreign person"),
    TYPE_4("4", "Persons engaged in profession or craft activity");

    private String value;
    private String description;
    
    BorrowerType(String value, String description) {
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
