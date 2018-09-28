package com.crediline.files.fiscalprinter;

import com.crediline.files.FileWriter;
import com.crediline.model.Payment;
import com.crediline.utils.LocaleUtils;
import com.crediline.utils.PreferencesUtils;
import com.crediline.utils.PrintUtils;
import com.crediline.utils.SessionUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

public class PrinterFileCreator {

    public static final String FISCAL_PRINTER_FILENAME = "receipt.txt";
    private static final String INTEREST_TEMPLATE = "S,1,______,_,__;" + LocaleUtils.getLocaliziedMessage("common.LIHVA") + ";%s;1.000;1;1;1;0;0;\r\n" +
            "T,1,______,_,__;0;%s\r\n";
    private static final String FEE_TEMPLATE = "S,1,______,_,__;" + LocaleUtils.getLocaliziedMessage("common.TAKSAOBRDOC") + ";%s;1.000;1;1;2;0;0\r\n" +
            "T,1,______,_,__;0;%s;\r\n";
    private static final String RECEIPT_REPORT = "Z,1,______,_,__;0; ";
    private static final String RECEIPT_REPORT_WITH_RESET = "Z,1,______,_,__; ";

    private FileWriter fileWriter;
    private String sessionId;

    public PrinterFileCreator() {
        this.sessionId = SessionUtils.getSessionId();
        fileWriter = new FileWriter();
    }

    public File createInterestsFile(List<Payment> payments) {
        StringBuilder builder = new StringBuilder();
        for (Payment payment : payments) {
            BigDecimal sum = payment.getInterest();
            builder.append(String.format(INTEREST_TEMPLATE, PrintUtils.print(sum, Locale.ENGLISH), PrintUtils.print(sum, Locale.ENGLISH)));
            payment.setIsBilled(true);
        }

        return fileWriter.write(builder.toString(), generateSourceFilename());
    }

    public File createInterestsFile(BigDecimal sum) {
        return fileWriter.write(String.format(INTEREST_TEMPLATE,
                PrintUtils.print(sum, Locale.ENGLISH),
                PrintUtils.print(sum, Locale.ENGLISH)),
                generateSourceFilename());
    }

    public File createReceiptReport(boolean reset) {
        String data = null;
        if (reset) {
            data = RECEIPT_REPORT_WITH_RESET;
        } else {
            data = RECEIPT_REPORT;
        }

        return fileWriter.write(data, generateSourceFilename());
    }

    public File createFeeFile(BigDecimal sum) {
        BigDecimal fee = getFeeValue(sum);
        String data = String.format(FEE_TEMPLATE, PrintUtils.print(fee, Locale.ENGLISH), PrintUtils.print(fee, Locale.ENGLISH));

        return fileWriter.write(data, generateSourceFilename());
    }

    private BigDecimal getFeeValue(BigDecimal sum) {
        BigDecimal smallCreditFeePreference = new BigDecimal(PreferencesUtils.getSmallCreditFeePreference());
        BigDecimal bigCreditFeePreference = new BigDecimal(PreferencesUtils.getBigCreditFeePreference());

        BigDecimal fee = null;

        int compared = sum.compareTo(new BigDecimal(999));
        if (compared < 0 || compared == 0) {//sum<=999
            //fee = sum*smallCreditFeePreference/100 - get smallCreditFeePreference percents from sum
            fee = (sum.multiply(smallCreditFeePreference)).divide(new BigDecimal(100));
        } else if (compared > 0) {//sum>999
            fee = bigCreditFeePreference;
        }

        return fee;
    }

    public String generateSourceFilename() {
        return sessionId + "_" + FISCAL_PRINTER_FILENAME;
    }

}