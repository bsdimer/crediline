package com.crediline.files.register.nomenclature;

public enum InstitutionalSector {
	// STODO add other activity when needed
	// get them from instructions_reg22_bg.pdf file from src/main/resources
	SECTOR_3312("3312", "Households - Population");

	private String value;
	private String description;

	InstitutionalSector(String value, String description) {
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
