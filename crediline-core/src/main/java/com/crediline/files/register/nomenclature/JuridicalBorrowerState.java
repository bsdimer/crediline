package com.crediline.files.register.nomenclature;

public enum JuridicalBorrowerState {
	// STODO add other activity when needed
	// get them from instructions_reg22_bg.pdf file from src/main/resources
	STATE_1998("1998", "Local physical person");

	private String value;
	private String description;

	JuridicalBorrowerState(String value, String description) {
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
