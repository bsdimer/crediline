package com.crediline.documents;

import com.crediline.mb.ITemplatable;
import com.crediline.model.Credit;
import com.crediline.model.Document;
import com.crediline.model.DocumentTemplatePurpose;
import com.crediline.model.Payment;
import com.crediline.utils.PrintFlag;
import com.crediline.utils.PrintUtils;
import com.crediline.utils.SessionUtils;
import com.crediline.utils.SpellerBG;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.util.StringUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by dimer on 8/25/14.
 */
public class CreditDocumentHelper implements ITemplatable {


    private Credit credit;

    public static DocumentFactory documentFactory;

    public CreditDocumentHelper(Credit credit) {
        this.credit = credit;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    @Override
    public Map<String, Object> getTemplateItems() throws IllegalArgumentException {
        Map<String, Object> items = new HashMap<String, Object>();

        DateTimeFormatter dtFormater = DateTimeFormat.forPattern("dd/MM/yyyy").withLocale(Locale.US);

        items.put("credit", credit);
        items.put("today", LocalDateTime.now());
        items.put("StringUtils", StringUtils.class);
        items.put("PrintUtils", PrintUtils.class);
        items.put("SpellerBG", SpellerBG.class);
        items.put("mathTool", new MathTool());
        items.put("currentOffice", SessionUtils.getCurrentUser().getOffice());

        items.put("monthInterest", credit.getMonthInterest());
        items.put("spelledMonthInterest", SpellerBG.spellPercent(credit.getMonthInterest()));
        items.put("dayInterest", credit.getDayInterest());
        items.put("spelledDayInterest", SpellerBG.spellPercent(credit.getDayInterest()));
        items.put("yearInterest", credit.getYearInterest());
        items.put("spelledYearInterest", SpellerBG.spellPercent(credit.getYearInterest()));


        items.put("gpr", credit.getGpr().setScale(2, RoundingMode.HALF_UP));
        items.put("spelledGpr", SpellerBG.spellPercent(credit.getGpr().setScale(2, RoundingMode.HALF_UP)));

        if (credit.getPerson() != null) {
            items.put("id", credit.getId() != null ? String.valueOf(credit.getId()) : ".....");
            items.put("name", credit.getPerson().getName().length() > 0 ? credit.getPerson().getName() : ".............");
            items.put("midname", credit.getPerson().getMidname().length() > 0 ? credit.getPerson().getMidname() : "............");
            items.put("surname", credit.getPerson().getSurname().length() > 0 ? credit.getPerson().getSurname() : "............");
            items.put("fullName", credit.getPerson().getFullName().length() > 0 ? credit.getPerson().getFullName() : ".............");
            items.put("egn", credit.getPerson().getEgn().length() > 0 ? credit.getPerson().getEgn() : "...........");
            items.put("lkno", credit.getPerson().getLkNo().length() > 0 ? credit.getPerson().getLkNo() : "...........");
            items.put("lkissuedate", credit.getPerson().getLkIssueDate() != null ? dtFormater.print(credit.getPerson().getLkIssueDate()) : ".........");
            items.put("lkissueplace", credit.getPerson().getLkIssueLocation() != null ? credit.getPerson().getLkIssueLocation() : "............");
            items.put("person", credit.getPerson());
            items.put("credit", credit);
            if (credit.getPerson().getAddresses().size() > 0) {
                items.put("lkaddress", PrintUtils.print(credit.getPerson().getAddresses().get(0), PrintFlag.PRINT_PRETY));
                if (credit.getPerson().getAddresses().size() > 1) {
                    items.put("currentAddress", PrintUtils.print(credit.getPerson().getAddresses().get(1), PrintFlag.PRINT_PRETY));
                } else {
                    items.put("currentAddress", PrintUtils.print(credit.getPerson().getAddresses().get(0), PrintFlag.PRINT_PRETY));
                }
            } else {
                items.put("lkaddress", ".......................................................");
                items.put("currentAddress", ".......................................................");
            }
            items.put("workplace", credit.getPerson().getWorkplace() != null ? credit.getPerson().getWorkplace() : "................");
            if (credit.getPerson().getPhones().size() > 0) {
                items.put("phone", credit.getPerson().getPhones().get(0).getNumber());
            } else {
                items.put("phone", "...............");
            }
        } else {
            throw new NullPointerException("No person associated");
        }


        items.put("sum", credit.getBasis() != null ? credit.getBasis().toString() : ".......");
        items.put("period", credit.getPeriod() != null ? credit.getPeriod().toString() : ".....");

        BigDecimal interest = credit.getInterest().multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);

        items.put("interest", interest.toString());
        items.put("interestWords", SpellerBG.spellPercent(interest.floatValue()));
        items.put("dayInterest", interest.divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP).toString());
        items.put("dayInterestWords", SpellerBG.spellPercent(interest.divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP).floatValue()));
        items.put("yearInterest", interest.multiply(BigDecimal.valueOf(12)).toString());
        items.put("yearInterestWords", SpellerBG.spellPercent(interest.multiply(BigDecimal.valueOf(12)).floatValue()));
        items.put("sumWords", SpellerBG.speelCurrency(credit.getBasis().floatValue()));
        items.put("basis", credit.getBasis());
        items.put("originalBasis", credit.getOriginalBasis());
        items.put("basisWords", SpellerBG.speelCurrency(credit.getBasis().floatValue()));
        items.put("originalBasisWords", SpellerBG.speelCurrency(credit.getOriginalBasis().floatValue()));
        items.put("currentDate", dtFormater.print(credit.getCreationDate()));
        items.put("fullSum", SpellerBG.speelCurrency(credit.getFullSum().floatValue()));
        items.put("periodWords", SpellerBG.spellInteger(credit.getPeriod()));
        items.put("periodSumWords", SpellerBG.spellInteger(credit.getPeriod()));
        items.put("payments", credit.getPayments() != null ? credit.getPayments() : new ArrayList<Payment>());
        items.put("taxManagement", credit.getApplicationFee());
        if (credit.getGpr().floatValue() > 0) {
            items.put("gpr", credit.getGpr().multiply(BigDecimal.valueOf(100d)).setScale(2, RoundingMode.HALF_UP));
            items.put("gprWords", SpellerBG.spellPercent(credit.getGpr().multiply(BigDecimal.valueOf(100d)).setScale(2, RoundingMode.HALF_UP).floatValue()));
        }
        items.put("insurance", credit.getInsurance().getSum().setScale(2, RoundingMode.HALF_UP));
        items.put("insuredAmount", credit.getInsurance().getSum());


        if (credit.getCreatedIn() != null) {
            items.put("currentCity", credit.getCreatedIn().getName());
            items.put("region", credit.getCreatedIn().getRegion().getName());
            items.put("mopc", credit.getCreatedIn().getMopc());
            items.put("moap", credit.getCreatedIn().getMoap());
            items.put("mobank", credit.getCreatedIn().getMobank());
            items.put("mocity", credit.getCreatedIn().getMocity());
            items.put("motitular", credit.getCreatedIn().getMotitular());
            items.put("mooblast", credit.getCreatedIn().getMooblast());
            items.put("mophone", credit.getCreatedIn().getMophone());
            items.put("moregion", credit.getCreatedIn().getMoregion());
            if (credit.getCreatedIn().getRegion() != null && credit.getCreatedIn().getRegion().getManager() != null) {
                items.put("moOfficeManagerName", credit.getCreatedIn().getRegion().getManager().getFullName());
            } else {
                items.put("moOfficeManagerName", "...........................");
            }
            items.put("moOfficeManagerNameEn", credit.getCreatedIn().getMotitular() != null ? credit.getCreatedIn().getMotitular() : "............................");
            if (credit.getCreatedIn().getPhones().size() > 0) {
                items.put("officePhone1", credit.getCreatedIn().getPhones().get(0).getNumber());
            } else {
                items.put("officePhone1", ".....................");
                items.put("officePhone2", ".....................");
            }
            if (credit.getCreatedIn().getPhones().size() > 1) {
                items.put("officePhone1", credit.getCreatedIn().getPhones().get(0).getNumber());
                items.put("officePhone2", credit.getCreatedIn().getPhones().get(1).getNumber());
            }
        }

        if (credit.getGuarantor1() != null) {
            items.put("guarantor1", credit.getGuarantor1());
            items.put("g1name", credit.getGuarantor1().getName().length() > 0 ? credit.getGuarantor1().getName() : "...........");
            items.put("g1midname", credit.getGuarantor1().getMidname().length() > 0 ? credit.getGuarantor1().getMidname() : "...........");
            items.put("g1surname", credit.getGuarantor1().getSurname().length() > 0 ? credit.getGuarantor1().getSurname() : "............");
            items.put("g1egn", credit.getGuarantor1().getEgn().length() > 0 ? credit.getGuarantor1().getEgn() : "............");
            items.put("g1lkno", credit.getGuarantor1().getLkNo().length() > 0 ? credit.getGuarantor1().getLkNo() : "............");
            items.put("g1lkissuedate", credit.getGuarantor1().getLkIssueDate() != null ? dtFormater.print(credit.getGuarantor1().getLkIssueDate()) : "..........");
            items.put("g1lkissueplace", credit.getGuarantor1().getLkIssueLocation() != null ? credit.getGuarantor1().getLkIssueLocation() : "............");

            if (credit.getGuarantor1().getAddresses().size() > 0) {
                items.put("g1address0", PrintUtils.print(credit.getGuarantor1().getAddresses().get(0), PrintFlag.PRINT_PRETY));
                if (credit.getGuarantor1().getAddresses().size() > 2) {
                    items.put("g1address1", PrintUtils.print(credit.getGuarantor1().getAddresses().get(1), PrintFlag.PRINT_PRETY));
                }
            } else {
                items.put("g1address0", ".......................................................");
                items.put("g1address1", ".......................................................");
            }
            items.put("g1workplace", credit.getGuarantor1().getWorkplace() != null ? credit.getGuarantor1().getWorkplace() : "................");
            if (credit.getGuarantor1().getPhones().size() > 0) {
                items.put("g1phone", credit.getGuarantor1().getPhones().get(0).getNumber());
            } else {
                items.put("g1phone", "...............");
            }
        } else {
            items.put("g1name", ".............");
            items.put("g1midname", ".............");
            items.put("g1surname", ".............");
            items.put("g1egn", "...........");
            items.put("g1lkno", ".............");
            items.put("g1lkissuedate", ".............");
            items.put("g1lkissueplace", ".............");
            items.put("g1address0", "............................................");
            items.put("g1address1", "............................................");
            items.put("g1workplace", "....................");
            items.put("g1phone", "...............");
        }

        if (credit.getGuarantor2() != null) {
            items.put("guarantor2", credit.getGuarantor2());
            items.put("g2name", credit.getGuarantor2().getName().length() > 0 ? credit.getGuarantor2().getName() : "...........");
            items.put("g2midname", credit.getGuarantor2().getMidname().length() > 0 ? credit.getGuarantor2().getMidname() : "...........");
            items.put("g2surname", credit.getGuarantor2().getSurname().length() > 0 ? credit.getGuarantor2().getSurname() : "............");
            items.put("g2egn", credit.getGuarantor2().getEgn().length() > 0 ? credit.getGuarantor2().getEgn() : "............");
            items.put("g2lkno", credit.getGuarantor2().getLkNo().length() > 0 ? credit.getGuarantor2().getLkNo() : "............");
            items.put("g2lkissuedate", credit.getGuarantor2().getLkIssueDate() != null ? dtFormater.print(credit.getGuarantor2().getLkIssueDate()) : "..........");
            items.put("g2lkissueplace", credit.getGuarantor2().getLkIssueLocation() != null ? credit.getGuarantor2().getLkIssueLocation() : "............");
            if (credit.getGuarantor2().getAddresses().size() > 0) {
                items.put("g2address0", PrintUtils.print(credit.getGuarantor2().getAddresses().get(0), PrintFlag.PRINT_PRETY));
                if (credit.getGuarantor1().getAddresses().size() > 2) {
                    items.put("g2address1", PrintUtils.print(credit.getGuarantor2().getAddresses().get(1), PrintFlag.PRINT_PRETY));
                }
            } else {
                items.put("g1address0", ".......................................................");
                items.put("g1address1", ".......................................................");
            }
            items.put("g2workplace", credit.getGuarantor2().getWorkplace() != null ? credit.getGuarantor2().getWorkplace() : "................");
            if (credit.getGuarantor2().getPhones().size() > 0) {
                items.put("g2phone", credit.getGuarantor2().getPhones().get(0).getNumber());
            } else {
                items.put("g2phone", "...............");
            }

        } else {
            items.put("g2name", ".............");
            items.put("g2midname", ".............");
            items.put("g2surname", ".............");
            items.put("g2egn", "...........");
            items.put("g2lkno", ".............");
            items.put("g2lkissuedate", ".............");
            items.put("g2lkissueplace", ".............");
            items.put("g2address0", ".............................................");
            items.put("g2address1", ".............................................");
            items.put("g2workplace", "....................");
            items.put("g2phone", "...............");
        }


        return items;
    }

    public Document getAgreementDocument() {
        return getDocument(DocumentTemplatePurpose.AGREEMENT_1);
    }


    public Document getContractDocument1() {
        return getDocument(DocumentTemplatePurpose.DPK1);
    }


    public Document getContractDocument2() {
        return getDocument(DocumentTemplatePurpose.DPK2);
    }


    public Document getContractAppendix1Document() {
        return getDocument(DocumentTemplatePurpose.DPK_APPENDIX1);
    }


    public Document getContractGuarantor1Document() {
        return getDocument(DocumentTemplatePurpose.CONTRACT_GUARANTOR1);
    }


    public Document getContractGuarantor2Document() {
        return getDocument(DocumentTemplatePurpose.CONTRACT_GUARANTOR2);
    }


    public Document getOutcomeOrderDocument() {
        return getDocument(DocumentTemplatePurpose.OUTCOME_ORDER);
    }


    public Document getInsuranceNoteDocument() {
        return getDocument(DocumentTemplatePurpose.INSURANCE_NOTE1);
    }


    public Document getContractAppendix2Document() {
        return getDocument(DocumentTemplatePurpose.DPK_APPENDIX2);
    }

    public Document getDocument(DocumentTemplatePurpose purpose) {
        Document document = new Document();
        try {
            document = documentFactory.generateDocument(purpose, getTemplateItems());
        } catch (Exception e) {
            document.setSource(String.format("Error in rendering: %s<br/>%s", purpose, e.getMessage()));
        }
        return document;
    }
}
