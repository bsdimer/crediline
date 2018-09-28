package com.crediline.files.register.model;

import java.util.Date;

import org.joda.time.LocalDateTime;

//данни за клиенти - кредитополучатели;
public class BorrowerData implements IRegisterData {
	//Полетата са еквивалентни на полетата в документ instructions_reg22_bg.pdf
	
	//Дата на отчета
	private String BORR_DATE = new String();
	//Код на деклариращата отчетна единица
	private String BORR_BAE = new String();
	//Тип на записа - номенклатура “Тип на записа” (т. IV Номенклатури)
	private String BORR_REC = new String();
	//Тип на кредитополучателя - номенклатура “Тип на кредитополучателя” (т. IV Номенклатури)
	private String BORR_TYPE = new String();
	//Идентификационен код на кредитополучателя
	private String BORR_ID = new String();
	//Име на кредитополучателя
	private String BORR_NAME = new String();
	//Адрес на кредитополучателя
	private String BORR_ADR = new String();
	//Допълнителни данни(за самоличност или търговска регистрация)
	private String BORR_INF = new String();
	//Статут на кредитополучателя -номенклатура “Юридически статут на кредитополучателя” (т. IV Номенклатури)
	private String BORR_STAT = new String();
	//Сектор в икономиката - номенклатура “Институционален сектор” (т. IV Номенклатури)
	private String BORR_SECT = new String();
	//Отрасъл - номенклатура “Икономически дейности” (т. IV Номенклатури)
	private String BORR_BRAN = new String();
	
	public String getBORR_DATE() {
		return BORR_DATE;
	}

	public void setBORR_DATE(String BORR_DATE) {
		this.BORR_DATE = BORR_DATE;
	}
	
	public void setBORR_DATE(LocalDateTime BORR_DATE) {
		this.BORR_DATE = DATE_FORMAT.format(BORR_DATE.toDate());
	}

	public String getBORR_BAE() {
		return BORR_BAE;
	}

	public void setBORR_BAE(String BORR_BAE) {
		this.BORR_BAE = BORR_BAE;
	}

	public String getBORR_REC() {
		return BORR_REC;
	}

	public void setBORR_REC(String BORR_REC) {
		this.BORR_REC = BORR_REC;
	}

	public String getBORR_TYPE() {
		return BORR_TYPE;
	}

	public void setBORR_TYPE(String BORR_TYPE) {
		this.BORR_TYPE = BORR_TYPE;
	}

	public String getBORR_ID() {
		return BORR_ID;
	}

	public void setBORR_ID(String BORR_ID) {
		this.BORR_ID = BORR_ID;
	}

	public String getBORR_NAME() {
		return BORR_NAME;
	}

	public void setBORR_NAME(String BORR_NAME) {
		this.BORR_NAME = BORR_NAME;
	}

	public String getBORR_ADR() {
		return BORR_ADR;
	}

	public void setBORR_ADR(String BORR_ADR) {
		this.BORR_ADR = BORR_ADR;
	}

	public String getBORR_INF() {
		return BORR_INF;
	}

	public void setBORR_INF(String BORR_INF) {
		this.BORR_INF = BORR_INF;
	}

	public String getBORR_STAT() {
		return BORR_STAT;
	}

	public void setBORR_STAT(String BORR_STAT) {
		this.BORR_STAT = BORR_STAT;
	}

	public String getBORR_SECT() {
		return BORR_SECT;
	}

	public void setBORR_SECT(String BORR_SECT) {
		this.BORR_SECT = BORR_SECT;
	}

	public String getBORR_BRAN() {
		return BORR_BRAN;
	}

	public void setBORR_BRAN(String BORR_BRAN) {
		this.BORR_BRAN = BORR_BRAN;
	}

	@Override
	public String getData() {
		String format = String.format("%-8s %-8s %-2s %-1s %-13s %-60s %-60s %-60s %-5s %-4s %-5s\r\n",
				BORR_DATE, BORR_BAE, BORR_REC, BORR_TYPE, BORR_ID, 
				BORR_NAME, BORR_ADR, BORR_INF, 
				BORR_STAT, BORR_SECT, BORR_BRAN);
		
		return format;
	}

	public static String getFilename() {
		return "BORR_F44_" + FILENAME_DATE_FORMAT.format(new Date()) + "00.txt";
	}
}
