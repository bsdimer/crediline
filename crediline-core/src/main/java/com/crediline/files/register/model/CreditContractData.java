package com.crediline.files.register.model;

import com.crediline.utils.PrintUtils;

import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

//данни за кредитните договори;
public class CreditContractData implements IRegisterData {
	//Полетата са еквивалентни на полетата в документ instructions_reg22_bg.pdf
	
	//Дата на отчета
	private String CRED_DATE = new String();
	//Код на деклариращата отчетна единица
	private String CRED_BAE = new String();
	//Тип на записа – номенклатура “Тип на записа” (т. IV Номенклатури)
	private String CRED_REC = new String();
	//Уникален код за идентификация на кредита в отчетната единица, представяща отчета
	private String CRED_NUM = new String();
	//Идентификационен код на кредитополучателя
	private String CRED_BORR = new String();
	//Тип на кредита – номенклатура “Тип на кредита” (т. IV Номенклатури)
	private String CRED_SPEC = new String();
	//Предназначение (цел) на кредита – номенклатура “Предназначение (цел) на кредита” (т. IV Номенклатури)
	private String CRED_PURP = new String();
	//Валута на кредита по SWIFT стандарта – номенклатура “Вид валута” (т. IV Номенклатури)
	private String CRED_CURR = new String();
	//Разрешена сума на кредита в оригинална валута
	private String CRED_ORG = new String();
	//Разрешена сума на кредита в лева
	private String CRED_BGL = new String();
	//Дата на разрешаване на кредита
	private String CRED_DAT1 = new String();
	//Дата на издължаване на кредита
	private String CRED_DAT2 = new String();
	//Общ размер на обезпечението
	private String CRED_COLR = new String();
	//Код на приемливо обезпечение и гаранция – по номенклатура “Вид обезпечение”обезпечение”, 
	//кодове 301-305, 307-309, 318-319, 327-336 (т. IV Номенклатури)
	private String CRED_COLT1 = new String();
	//Размер на приемливо обезпечение и гаранция
	private String CRED_COLR1 = new String();
	//Код на обезпечение с ипотека – по номенклатура “Вид обезпечение”, кодове 306 и 326 (т. IV Номенклатури)
	private String CRED_COLT2 = new String();
	//Размер на обезпечение с ипотека
	private String CRED_COLR2 = new String();
	//Код на друго обезпечение – по номенклатура “Вид обезпечение”, кодове 310-317, 320-321 (т. IV Номенклатури)
	private String CRED_COLT3 = new String();
	//Размер на друго обезпечение
	private String CRED_COLR3 = new String();
	//Вид на договорения лихвен процент – код по номенклатура “Вид на договорения лихвен процент” (т. IV Номенклатури)
	private String CRED_INTR_TYPE = new String();
	//База за определяне на лихвения процент при променлив лихвен процент – код по номенклатура 
	//“База за определяне на лихвения процент” (т. IV Номенклатури) Попълва се при променлив лихвен 
	//процент – кодове 11, 12, 15, 16 в поле Вид на договорения лихвен процент.
	private String CRED_INTR_BASE = new String();
	//Начален лихвен процент при отпускане на кредита Полето е задължително за 
	//новоразрешените кредити и се подава във формат nnn.nn.
	private String CRED_INTR = new String();
	//Гратисен период на кредита - код по номенклатура “Гратисен период ” (т. IV Номенклатури) 
	//Попълва се при подаване на информация за нов кредитен договор.
	private String CRED_GRACE_PER = new String();
	//Източник на финансиране – код по номенклатура “Източник на финансиране” (т. IV Номенклатури)
	private String CRED_FIN = new String();
	//Схема на издължаване на кредита – код по номенклатура “Схема на издължаване на кредита” (т. IV Номенклатури)
	private String CRED_SH_PAY = new String();
	//Преоформяне на кредитния договор – код по номенклатура “Преоформяне на кредитния договор” 
	//(т. IV Номенклатури) Попълва се при преоформяне на кредитния договор.
	private String CRED_PREDOG = new String();
	//Причина за преоформяне на кредитния договор – код по номенклатура 
	//“Причина за преоформяне на кредитния договор” (т. IV Номенклатури) 
	//Полето се попълва когато поле Преоформяне на кредитния договор е попълнено – съдържа конкретна стойност.
	private String CRED_PREDOG_REASON = new String();
	//Номер на неактивен/активен кредит от С на ЦКР, който се преоформя. 
	//Полето е задължително при регистриране на нов кредит, с който се преоформя 
	//друг стар кредит от същата отчетна единица. Полето трябва да съдържа 
	//номер на кредит от ИС на ЦКР, регистриран и поддържан от отчетната единица.
	private String CRED_PREDOG_OLDNUM = new String();
	//Прехвърляне на кредита на трето лице. Стойност “ДА”
	private String CRED_TYPE112 = new String();
	//ЕИК/БУЛСТАТ или друг идентификатор на третото лице, на което отчетната единица прехвърля 
	//кредита. Полето задължително се попълва когато поле CRED_SPEC има стойност “112” и 
	//поле CRED_TYPE112 имa стойност “ДА”. Проверява се въведения ЕИК или БУЛСТАТ за валидност.
	private String CRED_TYPE112_BULSTAT = new String();
	//Рефинансиране (прехвърляне на кредита на друг кредитор) Стойност “ДА”.
	private String CRED_REFINANS = new String();
	//Идентификационен код на отчетната единица на кредита, който се рефинансира – на стария кредитор. 
	//Полето се попълва при регистриране на нов кредит, който рефинансира стар кредит с прехвърляне 
	//на друг кредитор. Полето трябва да съдържа идентификационен код на отчетната единица на стария кредит.
	private String CRED_REFINANS_BAE = new String();
	//Номер на неактивен/активен кредит от ИС на ЦКР, който се рефинансира (стар номер на кредита в ЦКР). 
	//Полето се попълва при регистриране на нов кредит, който рефинансира стар кредит с прехвърляне на 
	//друг кредитор. Полето трябва да съдържа номер на кредит от ИС на ЦКР, регистриран и поддържан от 
	//отчетната единица, чийто идентификационен код е указан в поле CRED_REFINANS_BAE.
	private String CRED_REFINANS_OLDNUM = new String();
	
	public String getCRED_DATE() {
		return CRED_DATE;
	}

	public void setCRED_DATE(String CRED_DATE) {
		this.CRED_DATE = CRED_DATE;
	}
	
	public void setCRED_DATE(LocalDateTime CRED_DATE) {
		this.CRED_DATE = DATE_FORMAT.format(CRED_DATE.toDate());
	}

	public String getCRED_BAE() {
		return CRED_BAE;
	}

	public void setCRED_BAE(String CRED_BAE) {
		this.CRED_BAE = CRED_BAE;
	}

	public String getCRED_REC() {
		return CRED_REC;
	}

	public void setCRED_REC(String CRED_REC) {
		this.CRED_REC = CRED_REC;
	}

	public String getCRED_NUM() {
		return CRED_NUM;
	}

	public void setCRED_NUM(String CRED_NUM) {
		this.CRED_NUM = CRED_NUM;
	}

	public String getCRED_BORR() {
		return CRED_BORR;
	}

	public void setCRED_BORR(String CRED_BORR) {
		this.CRED_BORR = CRED_BORR;
	}

	public String getCRED_SPEC() {
		return CRED_SPEC;
	}

	public void setCRED_SPEC(String CRED_SPEC) {
		this.CRED_SPEC = CRED_SPEC;
	}

	public String getCRED_PURP() {
		return CRED_PURP;
	}

	public void setCRED_PURP(String CRED_PURP) {
		this.CRED_PURP = CRED_PURP;
	}

	public String getCRED_CURR() {
		return CRED_CURR;
	}

	public void setCRED_CURR(String CRED_CURR) {
		this.CRED_CURR = CRED_CURR;
	}

	public String getCRED_ORG() {
		return CRED_ORG;
	}

	public void setCRED_ORG(String CRED_ORG) {
		this.CRED_ORG = CRED_ORG;
	}

	public void setCRED_ORG(BigDecimal CRED_ORG) {
		this.CRED_ORG = PrintUtils.print(CRED_ORG, Locale.ENGLISH);
	}
			
	public String getCRED_BGL() {
		return CRED_BGL;
	}

	public void setCRED_BGL(String CRED_BGL) {
		this.CRED_BGL = CRED_BGL;
	}
	
	public void setCRED_BGL(BigDecimal CRED_BGL) {
		this.CRED_BGL = PrintUtils.print(CRED_BGL, Locale.ENGLISH);
	}

	public String getCRED_DAT1() {
		return CRED_DAT1;
	}
	
	public void setCRED_DAT1(String CRED_DAT1) {
		this.CRED_DAT1 = CRED_DAT1;
	}
	
	public void setCRED_DAT1(LocalDateTime CRED_DATE) {
		this.CRED_DAT1 = DATE_FORMAT.format(CRED_DATE.toDate());
	}

	public String getCRED_DAT2() {
		return CRED_DAT2;
	}

	public void setCRED_DAT2(String CRED_DAT2) {
		this.CRED_DAT2 = CRED_DAT2;
	}
	
	public void setCRED_DAT2(LocalDateTime CRED_DATE) {
		this.CRED_DAT2 = DATE_FORMAT.format(CRED_DATE.toDate());
	}

	public String getCRED_COLR() {
		return CRED_COLR;
	}

	public void setCRED_COLR(String CRED_COLR) {
		this.CRED_COLR = CRED_COLR;
	}
	
	public void setCRED_COLR(BigDecimal CRED_COLR) {
		this.CRED_COLR = PrintUtils.print(CRED_COLR, Locale.ENGLISH);
	}

	public String getCRED_COLT1() {
		return CRED_COLT1;
	}

	public void setCRED_COLT1(String CRED_COLT1) {
		this.CRED_COLT1 = CRED_COLT1;
	}

	public String getCRED_COLR1() {
		return CRED_COLR1;
	}

	public void setCRED_COLR1(String CRED_COLR1) {
		this.CRED_COLR1 = CRED_COLR1;
	}
	
	public void setCRED_COLR1(BigDecimal CRED_COLR1) {
		this.CRED_COLR1 = PrintUtils.print(CRED_COLR1, Locale.ENGLISH);
	}

	public String getCRED_COLT2() {
		return CRED_COLT2;
	}

	public void setCRED_COLT2(String CRED_COLT2) {
		this.CRED_COLT2 = CRED_COLT2;
	}

	public String getCRED_COLR2() {
		return CRED_COLR2;
	}

	public void setCRED_COLR2(String CRED_COLR2) {
		this.CRED_COLR2 = CRED_COLR2;
	}

	public String getCRED_COLT3() {
		return CRED_COLT3;
	}

	public void setCRED_COLT3(String CRED_COLT3) {
		this.CRED_COLT3 = CRED_COLT3;
	}

	public String getCRED_COLR3() {
		return CRED_COLR3;
	}

	public void setCRED_COLR3(String CRED_COLR3) {
		this.CRED_COLR3 = CRED_COLR3;
	}
	
	public void setCRED_COLR3(BigDecimal CRED_COLR3) {
		this.CRED_COLR3 = PrintUtils.print(CRED_COLR3, Locale.ENGLISH);
	}

	public String getCRED_INTR_TYPE() {
		return CRED_INTR_TYPE;
	}

	public void setCRED_INTR_TYPE(String CRED_INTR_TYPE) {
		this.CRED_INTR_TYPE = CRED_INTR_TYPE;
	}

	public String getCRED_INTR_BASE() {
		return CRED_INTR_BASE;
	}

	public void setCRED_INTR_BASE(String CRED_INTR_BASE) {
		this.CRED_INTR_BASE = CRED_INTR_BASE;
	}

	public String getCRED_INTR() {
		return CRED_INTR;
	}

	public void setCRED_INTR(String CRED_INTR) {
		this.CRED_INTR = CRED_INTR;
	}
	
	public void setCRED_INTR(BigDecimal CRED_INTR) {
		this.CRED_INTR = PrintUtils.print(CRED_INTR, Locale.ENGLISH);
	}

	public String getCRED_GRACE_PER() {
		return CRED_GRACE_PER;
	}

	public void setCRED_GRACE_PER(String CRED_GRACE_PER) {
		this.CRED_GRACE_PER = CRED_GRACE_PER;
	}

	public String getCRED_FIN() {
		return CRED_FIN;
	}

	public void setCRED_FIN(String CRED_FIN) {
		this.CRED_FIN = CRED_FIN;
	}

	public String getCRED_SH_PAY() {
		return CRED_SH_PAY;
	}

	public void setCRED_SH_PAY(String CRED_SH_PAY) {
		this.CRED_SH_PAY = CRED_SH_PAY;
	}

	public String getCRED_PREDOG() {
		return CRED_PREDOG;
	}

	public void setCRED_PREDOG(String CRED_PREDOG) {
		this.CRED_PREDOG = CRED_PREDOG;
	}

	public String getCRED_PREDOG_REASON() {
		return CRED_PREDOG_REASON;
	}

	public void setCRED_PREDOG_REASON(String CRED_PREDOG_REASON) {
		this.CRED_PREDOG_REASON = CRED_PREDOG_REASON;
	}

	public String getCRED_PREDOG_OLDNUM() {
		return CRED_PREDOG_OLDNUM;
	}

	public void setCRED_PREDOG_OLDNUM(String CRED_PREDOG_OLDNUM) {
		this.CRED_PREDOG_OLDNUM = CRED_PREDOG_OLDNUM;
	}

	public String getCRED_TYPE112() {
		return CRED_TYPE112;
	}

	public void setCRED_TYPE112(String CRED_TYPE112) {
		this.CRED_TYPE112 = CRED_TYPE112;
	}

	public String getCRED_TYPE112_BULSTAT() {
		return CRED_TYPE112_BULSTAT;
	}

	public void setCRED_TYPE112_BULSTAT(String CRED_TYPE112_BULSTAT) {
		this.CRED_TYPE112_BULSTAT = CRED_TYPE112_BULSTAT;
	}

	public String getCRED_REFINANS() {
		return CRED_REFINANS;
	}

	public void setCRED_REFINANS(String CRED_REFINANS) {
		this.CRED_REFINANS = CRED_REFINANS;
	}

	public String getCRED_REFINANS_BAE() {
		return CRED_REFINANS_BAE;
	}

	public void setCRED_REFINANS_BAE(String CRED_REFINANS_BAE) {
		this.CRED_REFINANS_BAE = CRED_REFINANS_BAE;
	}

	public String getCRED_REFINANS_OLDNUM() {
		return CRED_REFINANS_OLDNUM;
	}

	public void setCRED_REFINANS_OLDNUM(String CRED_REFINANS_OLDNUM) {
		this.CRED_REFINANS_OLDNUM = CRED_REFINANS_OLDNUM;
	}

	@Override
	public String getData() {
		String format = String.format("%-8s %-8s %-2s %-20s %-13s %-3s %-3s %-3s %-10s %-10s "
                					+ "%-8s %-8s %-10s %-3s %-10s %-3s %-10s %-3s %-10s %-2s "
                					+ "%-2s %-6s %-2s %-5s %-3s %-2s %-2s %-20s %-2s %-13s %-2s %-8s %-20s\r\n",
                CRED_DATE, CRED_BAE, CRED_REC, CRED_NUM, CRED_BORR, CRED_SPEC, CRED_PURP,
                CRED_CURR, CRED_ORG, CRED_BGL, CRED_DAT1, CRED_DAT2,
                CRED_COLR, CRED_COLT1, CRED_COLR1, CRED_COLT2, CRED_COLR2,
                CRED_COLT3, CRED_COLR3, CRED_INTR_TYPE, CRED_INTR_BASE, CRED_INTR,
                CRED_GRACE_PER, CRED_FIN, CRED_SH_PAY, CRED_PREDOG, CRED_PREDOG_REASON, CRED_PREDOG_OLDNUM,
                CRED_TYPE112, CRED_TYPE112_BULSTAT, CRED_REFINANS, CRED_REFINANS_BAE, CRED_REFINANS_OLDNUM);
		
		return format;
	}

	public static String getFilename() {
		return "CRED_F44_" + FILENAME_DATE_FORMAT.format(new Date()) + "00.txt";
	}
}
