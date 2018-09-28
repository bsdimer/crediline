package com.crediline.files.register;

import com.crediline.files.FileWriter;
import com.crediline.files.register.model.BorrowerData;
import com.crediline.files.register.model.CreditContractData;
import com.crediline.files.register.model.CreditStateData;
import com.crediline.files.register.nomenclature.*;
import com.crediline.model.Credit;
import com.crediline.model.Person;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CreditRegisterFileCreator {
    public static String CREDILINE_CODE = "BGR00044";

    private FileWriter fileWriter;

    public CreditRegisterFileCreator() {
        fileWriter = new FileWriter();
    }

    public List<BorrowerData> createBorrowersRegister(List<Person> borrowers, LocalDateTime fromDate) throws Exception {
        List<BorrowerData> borrowersData = createBorrowerData(borrowers, fromDate);
        fileWriter.write(borrowersData, BorrowerData.getFilename());

        return borrowersData;
    }

    public List<CreditContractData> createCreditContractsRegister(List<Credit> credits) throws Exception {
        List<CreditContractData> creditContractsData = createCreditContractsData(credits);
        fileWriter.write(creditContractsData, CreditContractData.getFilename());

        return creditContractsData;
    }

    public List<CreditStateData> createCreditsStateRegister(List<Credit> credits) throws Exception {
        List<CreditStateData> creditsStateData = createCreditsStateData(credits);
        fileWriter.write(creditsStateData, CreditStateData.getFilename());

        return creditsStateData;
    }

    private List<BorrowerData> createBorrowerData(List<Person> borrowers, LocalDateTime fromDate) throws Exception {
        LocalDateTime now = new LocalDateTime();
        List<BorrowerData> data = new ArrayList<BorrowerData>();

        try {
            for (Person person : borrowers) {
                BorrowerData borrowerData = new BorrowerData();
                //1
                borrowerData.setBORR_DATE(now);
                //10
                borrowerData.setBORR_BAE(CREDILINE_CODE);
                //We get persons which have new credits in range fromDate and toDate
                //So if person has credit before fromDate that means that this is not the first credit for relevant person
                //19
                borrowerData.setBORR_REC(person.hasCreditBeforeDate(fromDate) ? RecordType.TYPE_2.getValue() : RecordType.TYPE_1.getValue());
                //22
                borrowerData.setBORR_TYPE(BorrowerType.TYPE_1.getValue());
                //24
                borrowerData.setBORR_ID(getBorrowerID(person));
                //38
                borrowerData.setBORR_NAME(person.getFullName());
                //99
                borrowerData.setBORR_ADR(person.getHomeAddress()
                        .printShort());
                //160
                //			borrowerData.setBORR_INF(BORR_INF);
                //221
                borrowerData.setBORR_STAT(JuridicalBorrowerState.STATE_1998
                        .getValue());
                //227
                borrowerData.setBORR_SECT(InstitutionalSector.SECTOR_3312
                        .getValue());
                //232
                borrowerData.setBORR_BRAN(EconomicActivity.ACTIVITY_00.getValue());

                data.add(borrowerData);
            }
            return data;
        } catch (NullPointerException | IllegalArgumentException exception) {
            throw new Exception("exception.nullpointer", exception);
        }
    }

    private String getBorrowerID(Person person) {
        // STODO Person have to have personTypeEnum
        // switch (person.getType()) {
        // case 1:
        // break;
        // case 2:
        // break;
        // default:
        // break;
        // }

        return person.getEgn();
    }

    private List<CreditContractData> createCreditContractsData(List<Credit> credits) throws Exception {
        LocalDateTime now = new LocalDateTime();
        List<CreditContractData> creditContractsData = new ArrayList<CreditContractData>();
        try {
            for (Credit credit : credits) {
                CreditContractData data = new CreditContractData();
                //1
                data.setCRED_DATE(now);
                //10
                data.setCRED_BAE(CREDILINE_CODE);
                //19
                data.setCRED_REC(RecordType.TYPE_3.getValue());
                //22
                data.setCRED_NUM(credit.getId().toString());
                //43
                data.setCRED_BORR(getBorrowerID(credit.getPerson()));
                //57
                data.setCRED_SPEC(CreditType.TYPE_101.getValue());
                //61
                data.setCRED_PURP(CreditPurpose.TYPE_208.getValue());
                //65
                data.setCRED_CURR(credit.getCurrency().getCurrencyCode());
                //69
                data.setCRED_ORG(credit.getBasis());
                //80
                data.setCRED_BGL(credit.getBasis());
                //91
                data.setCRED_DAT1(credit.getCreationDate());
                //100
                data.setCRED_DAT2(credit.getDueDate());
                //TODO take a look is it right if currency is different from BGL
                //109
                data.setCRED_COLR(credit.getBasis().add(credit.getInterestSum()));
                //120
                data.setCRED_COLT1(CollateralType.TYPE_330.getValue());
                //124
                data.setCRED_COLR1(credit.getBasis());
                //135
                //			data.setCRED_COLT2(CRED_COLT2);//Empty field
                //139
                //			data.setCRED_COLR2(CRED_COLR2);//Empty field
                //150
                data.setCRED_COLT3(CollateralType.TYPE_317.getValue());
                //154
                data.setCRED_COLR3(credit.getInterestSum());
                //165
                data.setCRED_INTR_TYPE(InterestType.TYPE_10.getValue());
                //168
                //			data.setCRED_INTR_BASE(CRED_INTR_BASE);//Empty field
                //171
                data.setCRED_INTR(credit.getAnnualInterest().multiply(new BigDecimal(100)));
                //178
                data.setCRED_GRACE_PER(GracePeriod.P_90.getValue());
                //181
                data.setCRED_FIN(FinancingSource.SOURCE_90900.getValue());
                //187
                data.setCRED_SH_PAY(RepaymentScheme.SCHEME_701.getValue());
                //191
                //			data.setCRED_PREDOG(CRED_PREDOG);//Empty field
                //194
                //			data.setCRED_PREDOG_REASON(CRED_PREDOG_REASON);//Empty field
                //197
                //			data.setCRED_PREDOG_OLDNUM(CRED_PREDOG_OLDNUM);//Empty field
                //218
                //			data.setCRED_TYPE112(CRED_TYPE112);//Empty field
                //221
                //			data.setCRED_TYPE112_BULSTAT(CRED_TYPE112_BULSTAT);//Empty field
                //235
                //			data.setCRED_REFINANS(CRED_REFINANS);//Empty field
                //238
                //			data.setCRED_REFINANS_BAE(CRED_REFINANS_BAE);//Empty field
                //247
                //			data.setCRED_REFINANS_OLDNUM(CRED_REFINANS_OLDNUM);//Empty field

                creditContractsData.add(data);
            }

            return creditContractsData;
        } catch (NullPointerException | IllegalArgumentException exception) {
            throw new Exception("exception.nullpointer", exception);
        }
    }

    private List<CreditStateData> createCreditsStateData(List<Credit> credits) throws Exception {
        LocalDateTime now = new LocalDateTime();
        List<CreditStateData> creditContractsData = new ArrayList<CreditStateData>();
        try {
            for (Credit credit : credits) {
                CreditStateData data = new CreditStateData();
                //1
                data.setCUCR_DATE(now);
                //10
                data.setCUCR_BAE(CREDILINE_CODE);
                //19
                data.setCUCR_REC(credit.isItRepaid() ? RecordType.TYPE_6.getValue() : RecordType.TYPE_5.getValue());
                //22
                data.setCUCR_CRED(credit.getId().toString());
                //43
                data.setCUCR_BORR(getBorrowerID(credit.getPerson()));
                //57
                data.setCUCR_CATG(credit.getOverdueDays() > 90 ? CreditCategory.CATEGORY_403.getValue() : CreditCategory.CATEGORY_401.getValue());
                //61
                data.setCUCR_SUMA(credit.getBasis());
                //72
                data.setCUCR_PRINC(credit.getOustandingSum());
                //83
                //			data.setCUCR_PRINC_OVER(CUCR_PRINC_OVER);//Empty field
                //94
                data.setCUCR_OVER_INTER(credit.isInOverdue() ? credit.getOverdueInterest() : null);
                //105
                //			data.setCUCR_JUD_DUES(CUCR_JUD_DUES);//Empty field
                //116
                //			data.setCUCR_COND_BAL(CUCR_COND_BAL);//Empty field
                //127
                data.setCUCR_TOT_BALANS(credit.isItRepaid() ? new BigDecimal(0) : credit.getBasis());
                //138
                data.setCUCR_DEVAL(credit.getBasis());
                //149
                //			data.setCUCR_OFFBAL_DUES(CUCR_OFFBAL_DUES);//Empty field
                //160
                //			data.setCUCR_COND_OFF(CUCR_COND_OFF);//Empty field
                //171
                //			data.setCUCR_OVER_OFF(CUCR_OVER_OFF);//Empty field
                //182
                //			data.setCUCR_TOT_OFFBAL(CUCR_TOT_OFFBAL);//Empty field
                //193
                data.setCUCR_DEVAL_REASON(credit.isInOverdue() ? DevalueReason.REASON_803.getValue() : new String());
                //197
                data.setCUCR_EXP_NOM(getPeriodOverdue(credit));
                //201
                data.setCUCR_INTR(credit.getAnnualInterest().multiply(new BigDecimal(100)));
                //208
                //			data.setCUCR_PROV_RISK(CUCR_PROV_RISK);//Empty field
                //219
                //			data.setCUCR_PROV_MSS(CUCR_PROV_MSS);//Empty field
                //STODO check is it correct with example file got from Niki
                //230
                data.setCUCR_COLR(credit.getBasis().add(credit.getInterestSum()));
                //241
                data.setCUCR_COLT1(CollateralType.TYPE_330.getValue());
                //245
                data.setCUCR_COLR1(credit.getBasis());
                //256
                //			data.setCUCR_COLT2(CUCR_COLT2);//Empty field
                //260
                //			data.setCUCR_COLR2(CUCR_COLR2);//Empty field
                //271
                data.setCUCR_COLT3(CollateralType.TYPE_317.getValue());
                //275
                data.setCUCR_COLR3(credit.getInterestSum());

                creditContractsData.add(data);
            }

            return creditContractsData;
        } catch (NullPointerException | IllegalArgumentException exception) {
            throw new Exception("exception.nullpointer", exception);
        }
    }

    private String getPeriodOverdue(Credit credit) {
        String CUCR_EXP_NOM = new String();
        if (credit.isInOverdue()) {
            int overdueDays = credit.getOverdueDays();
            if (overdueDays >= 90 && overdueDays <= 180) {
                CUCR_EXP_NOM = OverdueDebtPeriod.PERIOD_73.getValue();
            } else if (overdueDays > 180) {
                CUCR_EXP_NOM = OverdueDebtPeriod.PERIOD_74.getValue();
            }
        }
        return CUCR_EXP_NOM;
    }
}
