package com.crediline.files.register.model;

import com.crediline.utils.PrintUtils;

import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

//състояние на кредитите към края на отчетен период;
public class CreditStateData implements IRegisterData {
	//Дата на отчета
	private String CUCR_DATE = new String();
	//Код на деклариращата отчетна единица
	private String CUCR_BAE = new String();
	//Тип на записа – код номенклатура “Тип на записа” (т. IV Номенклатури)
	private String CUCR_REC = new String();
	//Уникален код за идентификация на кредита в отчетната единица
	private String CUCR_CRED = new String();
	//Идентификационен код на кредитополучателя
	private String CUCR_BORR = new String();
	//Категория на кредита – код по номенклатура “Категория на кредита” (т. IV Номенклатури)
	private String CUCR_CATG = new String();
	//Усвоен размер на кредита
	private String CUCR_SUMA = new String();
	//Остатък по редовна главница
	private String CUCR_PRINC = new String();
	//Остатък по просрочена, съдебна или присъдена главница
	private String CUCR_PRINC_OVER = new String();
	//Просрочени неиздължени лихви
	private String CUCR_OVER_INTER = new String();
	//Съдебни вземания, лихви и разноски
	private String CUCR_JUD_DUES = new String();
	//Други вземания
	private String CUCR_COND_BAL = new String();
	//Балансова стойност на кредита преди обезценка
	private String CUCR_TOT_BALANS = new String();
	//Балансова стойност на кредита след обезценка
	private String CUCR_DEVAL = new String();
	//Необслужвани вземания – задбалансово
	private String CUCR_OFFBAL_DUES = new String();
	//Условни ангажименти – задбалансово
	private String CUCR_COND_OFF = new String();
	//Неусвоен размер на кредита – задбалансово
	private String CUCR_OVER_OFF = new String();
	//Обща задбалансова експозиция
	private String CUCR_TOT_OFFBAL = new String();
	//Причина за обезценката код по номенклатура “Причина за обезценката” (т. IV Номенклатури)
	private String CUCR_DEVAL_REASON = new String();
	//Период на просрочие на дълга – номенклатура “Период на просрочие на дълга” (т. IV Номенклатури)
	private String CUCR_EXP_NOM = new String();
	//Лихвен процент на текуща база към отчетна дата
	private String CUCR_INTR = new String();
	//Размер на специфични провизии за кредитен риск по Наредба № 9 на БНБ
	private String CUCR_PROV_RISK = new String();
	//Размер на провизии за индивидуално оценявани финансови активи по МСС
	private String CUCR_PROV_MSS = new String();
	//Общ размер на обезпечението
	private String CUCR_COLR = new String();
	//Код на приемливо обезпечение и гаранция – по номенклатура “Вид обезпечение”, 
	//кодове 301-305, 307-309, 318-319, 327-336 (т. IV Номенклатури)
	private String CUCR_COLT1 = new String();
	//Размер на приемливо обезпечение и гаранция
	private String CUCR_COLR1 = new String();
	//Код на обезпечение с ипотека – по номенклатура “Вид обезпечение”, кодове 306 и 326 (т. IV Номенклатури)
	private String CUCR_COLT2 = new String();
	//Размер на обезпечение с ипотека
	private String CUCR_COLR2 = new String();
	//Код на друго обезпечение – по номенклатура “Вид обезпечение”, кодове 310-317, 320-321 (т. IV Номенклатури)
	private String CUCR_COLT3 = new String();
	//Размер на друго обезпечение
	private String CUCR_COLR3 = new String();
	
	public String getCUCR_DATE() {
		return CUCR_DATE;
	}

	public void setCUCR_DATE(String CUCR_DATE) {
		this.CUCR_DATE = CUCR_DATE;
	}

	public void setCUCR_DATE(LocalDateTime CUCR_DATE) {
		this.CUCR_DATE = DATE_FORMAT.format(CUCR_DATE.toDate());
	}
	
	public String getCUCR_BAE() {
		return CUCR_BAE;
	}

	public void setCUCR_BAE(String CUCR_BAE) {
		this.CUCR_BAE = CUCR_BAE;
	}

	public String getCUCR_REC() {
		return CUCR_REC;
	}

	public void setCUCR_REC(String CUCR_REC) {
		this.CUCR_REC = CUCR_REC;
	}

	public String getCUCR_CRED() {
		return CUCR_CRED;
	}

	public void setCUCR_CRED(String CUCR_CRED) {
		this.CUCR_CRED = CUCR_CRED;
	}

	public String getCUCR_BORR() {
		return CUCR_BORR;
	}

	public void setCUCR_BORR(String CUCR_BORR) {
		this.CUCR_BORR = CUCR_BORR;
	}

	public String getCUCR_CATG() {
		return CUCR_CATG;
	}

	public void setCUCR_CATG(String CUCR_CATG) {
		this.CUCR_CATG = CUCR_CATG;
	}

	public String getCUCR_SUMA() {
		return CUCR_SUMA;
	}

	public void setCUCR_SUMA(String CUCR_SUMA) {
		this.CUCR_SUMA = CUCR_SUMA;
	}
	
	public void setCUCR_SUMA(BigDecimal CUCR_SUMA) {
		this.CUCR_SUMA = PrintUtils.print(CUCR_SUMA, Locale.ENGLISH);
	}

	public String getCUCR_PRINC() {
		return CUCR_PRINC;
	}

	public void setCUCR_PRINC(String CUCR_PRINC) {
		this.CUCR_PRINC = CUCR_PRINC;
	}
	
	public void setCUCR_PRINC(BigDecimal CUCR_PRINC) {
		this.CUCR_PRINC = PrintUtils.print(CUCR_PRINC, Locale.ENGLISH);
	}

	public String getCUCR_PRINC_OVER() {
		return CUCR_PRINC_OVER;
	}

	public void setCUCR_PRINC_OVER(String CUCR_PRINC_OVER) {
		this.CUCR_PRINC_OVER = CUCR_PRINC_OVER;
	}

	public String getCUCR_OVER_INTER() {
		return CUCR_OVER_INTER;
	}

	public void setCUCR_OVER_INTER(String CUCR_OVER_INTER) {
		this.CUCR_OVER_INTER = CUCR_OVER_INTER;
	}
	
	public void setCUCR_OVER_INTER(BigDecimal CUCR_OVER_INTER) {
		if(CUCR_OVER_INTER != null) {
			this.CUCR_OVER_INTER = PrintUtils.print(CUCR_OVER_INTER, Locale.ENGLISH);
		}
	}

	public String getCUCR_JUD_DUES() {
		return CUCR_JUD_DUES;
	}

	public void setCUCR_JUD_DUES(String CUCR_JUD_DUES) {
		this.CUCR_JUD_DUES = CUCR_JUD_DUES;
	}

	public String getCUCR_COND_BAL() {
		return CUCR_COND_BAL;
	}

	public void setCUCR_COND_BAL(String CUCR_COND_BAL) {
		this.CUCR_COND_BAL = CUCR_COND_BAL;
	}

	public String getCUCR_TOT_BALANS() {
		return CUCR_TOT_BALANS;
	}

	public void setCUCR_TOT_BALANS(String CUCR_TOT_BALANS) {
		this.CUCR_TOT_BALANS = CUCR_TOT_BALANS;
	}
	
	public void setCUCR_TOT_BALANS(BigDecimal CUCR_TOT_BALANS) {
		this.CUCR_TOT_BALANS = PrintUtils.print(CUCR_TOT_BALANS, Locale.ENGLISH);
	}

	public String getCUCR_DEVAL() {
		return CUCR_DEVAL;
	}

	public void setCUCR_DEVAL(String CUCR_DEVAL) {
		this.CUCR_DEVAL = CUCR_DEVAL;
	}
	
	public void setCUCR_DEVAL(BigDecimal CUCR_DEVAL) {
		this.CUCR_DEVAL = PrintUtils.print(CUCR_DEVAL, Locale.ENGLISH);
	}

	public String getCUCR_OFFBAL_DUES() {
		return CUCR_OFFBAL_DUES;
	}

	public void setCUCR_OFFBAL_DUES(String CUCR_OFFBAL_DUES) {
		this.CUCR_OFFBAL_DUES = CUCR_OFFBAL_DUES;
	}

	public String getCUCR_COND_OFF() {
		return CUCR_COND_OFF;
	}

	public void setCUCR_COND_OFF(String CUCR_COND_OFF) {
		this.CUCR_COND_OFF = CUCR_COND_OFF;
	}

	public String getCUCR_OVER_OFF() {
		return CUCR_OVER_OFF;
	}

	public void setCUCR_OVER_OFF(String CUCR_OVER_OFF) {
		this.CUCR_OVER_OFF = CUCR_OVER_OFF;
	}

	public String getCUCR_TOT_OFFBAL() {
		return CUCR_TOT_OFFBAL;
	}

	public void setCUCR_TOT_OFFBAL(String CUCR_TOT_OFFBAL) {
		this.CUCR_TOT_OFFBAL = CUCR_TOT_OFFBAL;
	}

	public String getCUCR_DEVAL_REASON() {
		return CUCR_DEVAL_REASON;
	}

	public void setCUCR_DEVAL_REASON(String CUCR_DEVAL_REASON) {
		this.CUCR_DEVAL_REASON = CUCR_DEVAL_REASON;
	}

	public String getCUCR_EXP_NOM() {
		return CUCR_EXP_NOM;
	}

	public void setCUCR_EXP_NOM(String CUCR_EXP_NOM) {
		this.CUCR_EXP_NOM = CUCR_EXP_NOM;
	}

	public String getCUCR_INTR() {
		return CUCR_INTR;
	}

	public void setCUCR_INTR(String CUCR_INTR) {
		this.CUCR_INTR = CUCR_INTR;
	}
	
	public void setCUCR_INTR(BigDecimal CUCR_INTR) {
		this.CUCR_INTR = PrintUtils.print(CUCR_INTR, Locale.ENGLISH);
	}

	public String getCUCR_PROV_RISK() {
		return CUCR_PROV_RISK;
	}

	public void setCUCR_PROV_RISK(String CUCR_PROV_RISK) {
		this.CUCR_PROV_RISK = CUCR_PROV_RISK;
	}

	public String getCUCR_PROV_MSS() {
		return CUCR_PROV_MSS;
	}

	public void setCUCR_PROV_MSS(String CUCR_PROV_MSS) {
		this.CUCR_PROV_MSS = CUCR_PROV_MSS;
	}

	public String getCUCR_COLR() {
		return CUCR_COLR;
	}

	public void setCUCR_COLR(String CUCR_COLR) {
		this.CUCR_COLR = CUCR_COLR;
	}
	
	public void setCUCR_COLR(BigDecimal CUCR_COLR) {
		this.CUCR_COLR = PrintUtils.print(CUCR_COLR, Locale.ENGLISH);
	}

	public String getCUCR_COLT1() {
		return CUCR_COLT1;
	}

	public void setCUCR_COLT1(String CUCR_COLT1) {
		this.CUCR_COLT1 = CUCR_COLT1;
	}

	public String getCUCR_COLR1() {
		return CUCR_COLR1;
	}

	public void setCUCR_COLR1(String CUCR_COLR1) {
		this.CUCR_COLR1 = CUCR_COLR1;
	}
	
	public void setCUCR_COLR1(BigDecimal CUCR_COLR1) {
		this.CUCR_COLR1 = PrintUtils.print(CUCR_COLR1, Locale.ENGLISH);
	}

	public String getCUCR_COLT2() {
		return CUCR_COLT2;
	}

	public void setCUCR_COLT2(String CUCR_COLT2) {
		this.CUCR_COLT2 = CUCR_COLT2;
	}

	public String getCUCR_COLR2() {
		return CUCR_COLR2;
	}

	public void setCUCR_COLR2(String CUCR_COLR2) {
		this.CUCR_COLR2 = CUCR_COLR2;
	}

	public String getCUCR_COLT3() {
		return CUCR_COLT3;
	}

	public void setCUCR_COLT3(String CUCR_COLT3) {
		this.CUCR_COLT3 = CUCR_COLT3;
	}

	public String getCUCR_COLR3() {
		return CUCR_COLR3;
	}

	public void setCUCR_COLR3(String CUCR_COLR3) {
		this.CUCR_COLR3 = CUCR_COLR3;
	}
	
	public void setCUCR_COLR3(BigDecimal CUCR_COLR3) {
		this.CUCR_COLR3 = PrintUtils.print(CUCR_COLR3, Locale.ENGLISH);
	}

	@Override
	public String getData() {
		String format = String.format("%-8s %-8s %-2s %-20s %-13s %-3s %-10s %-10s %-10s %-10s "
				                    + "%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-3s %-3s "
				                    + "%-6s %-10s %-10s %-10s %-3s %-10s %-3s %-10s %-3s %-10s\r\n",
				                    CUCR_DATE, CUCR_BAE, CUCR_REC, CUCR_CRED, CUCR_BORR, CUCR_CATG, CUCR_SUMA,
				                    CUCR_PRINC, CUCR_PRINC_OVER, CUCR_OVER_INTER, CUCR_JUD_DUES, CUCR_COND_BAL,
				                    CUCR_TOT_BALANS, CUCR_DEVAL, CUCR_OFFBAL_DUES, CUCR_COND_OFF, CUCR_OVER_OFF,
				                    CUCR_TOT_OFFBAL, CUCR_DEVAL_REASON, CUCR_EXP_NOM, CUCR_INTR, CUCR_PROV_RISK,
				                    CUCR_PROV_MSS, CUCR_COLR, CUCR_COLT1, CUCR_COLR1, CUCR_COLT2, CUCR_COLR2,
				                    CUCR_COLT3, CUCR_COLR3);
		
		return format;
	}

	public static String getFilename() {
		return "CUCR_F44_" + FILENAME_DATE_FORMAT.format(new Date()) + "00.txt";
	}
}
