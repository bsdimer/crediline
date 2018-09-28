package com.crediline.files.register.nomenclature;


public enum RecordType {
    TYPE_1("1", "Data for new borrower"), 
    TYPE_2("2", "Borrower data change"), 
    TYPE_3("3", "Data for allowing credit"),
    TYPE_4("4", "Credit data change"), 
    TYPE_5("5", "Current credit state"),
    TYPE_6("6", "Close credit"),
    TYPE_7("7", "Data corectoins for old report periods"),
    TYPE_8("8", "Readjustements of data for old report periods"),
    TYPE_9("9", "Control record"),
    TYPE_D("D", "Delete record from temporary table");

    private String value;
    private String description;
    
    RecordType(String value, String description) {
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