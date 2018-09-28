package com.crediline.files.register.nomenclature;

public enum OverdueDebtPeriod {
	PERIOD_70("70", "No overdue"),
	PERIOD_71("71", "Up to 30 days"),
	PERIOD_72("72", "Between 31 and 90 days"),
	PERIOD_73("73", "Between 91 and 180 days"),
	PERIOD_74("74", "Over 180");

    private String value;
    private String description;
    
    OverdueDebtPeriod(String value, String description) {
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
