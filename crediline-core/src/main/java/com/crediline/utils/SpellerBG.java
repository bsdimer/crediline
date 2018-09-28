package com.crediline.utils;

import java.math.BigDecimal;

/**
 * Created by dimer on 3/23/14.
 */
public class SpellerBG {

    private static String currencyNames = LocaleUtils.getLocaliziedMessage("common.currency.bgfullpl");
    private static String currencyName = LocaleUtils.getLocaliziedMessage("common.currency.bgfull");
    private static String currencyCoins = LocaleUtils.getLocaliziedMessage("common.currency.bgcoinfullpl");
    private static String currencyCoin = LocaleUtils.getLocaliziedMessage("common.currency.bgcoinfull");
    private static String percent = LocaleUtils.getLocaliziedMessage("common.percent");
    private static String percents = LocaleUtils.getLocaliziedMessage("common.percents");

    private static String concatenateWord = LocaleUtils.getLocaliziedMessage("common.and");
    private static String fullAnd = LocaleUtils.getLocaliziedMessage("common.fullAnd");

    public static synchronized String speelCurrency(Float value) {
        String[] parts = value.toString().split("\\.");
        DigitSpellerBG digitSpeller = new DigitSpellerBG();

        String leftPart = digitSpeller.spellout(Integer.parseInt(parts[0]));
        String rightPart = digitSpeller.spellout(Integer.parseInt(parts[1]));

        StringBuilder sb = new StringBuilder();
        sb.append(leftPart.concat(currencyNames + " "));
        sb.append(concatenateWord + " ");
        sb.append(rightPart.concat(currencyCoins + " "));

        return sb.toString();
    }

    public static synchronized String speelCurrency(BigDecimal bdvalue) {
        Float value = bdvalue.floatValue();
        String[] parts = value.toString().split("\\.");
        DigitSpellerBG digitSpeller = new DigitSpellerBG();

        String leftPart = digitSpeller.spellout(Integer.parseInt(parts[0]));
        String rightPart = digitSpeller.spellout(Integer.parseInt(parts[1]));

        StringBuilder sb = new StringBuilder();
        sb.append(leftPart.concat(currencyNames + " "));
        sb.append(concatenateWord + " ");
        sb.append(rightPart.concat(currencyCoins + " "));

        return sb.toString();
    }

    public static synchronized String spellPercent(Float value) {
        String[] parts = value.toString().split("\\.");
        DigitSpellerBG digitSpeller = new DigitSpellerBG();

        String leftPart = digitSpeller.spellout(Integer.parseInt(parts[0]));
        String rightPart = digitSpeller.spellout(Integer.parseInt(parts[1]));

        StringBuilder sb = new StringBuilder();
        sb.append(leftPart.concat(" "));

        if (Integer.parseInt(parts[1]) != 0) {
            sb.append(fullAnd.concat(" "));
            sb.append(rightPart.concat(" "));
        }

        if (value == 1) {
            sb.append(percent);
        } else {
            sb.append(percents);
        }

        return sb.toString();
    }

    public static synchronized String spellPercent(BigDecimal bdvalue) {
        Float value = bdvalue.floatValue();
        String[] parts = value.toString().split("\\.");
        DigitSpellerBG digitSpeller = new DigitSpellerBG();

        String leftPart = digitSpeller.spellout(Integer.parseInt(parts[0]));
        String rightPart = digitSpeller.spellout(Integer.parseInt(parts[1]));

        StringBuilder sb = new StringBuilder();
        sb.append(leftPart.concat(" "));

        if (Integer.parseInt(parts[1]) != 0) {
            sb.append(fullAnd.concat(" "));
            sb.append(rightPart.concat(" "));
        }

        if (value == 1) {
            sb.append(percent);
        } else {
            sb.append(percents);
        }

        return sb.toString();
    }

    public static synchronized String spellPercent(Integer value) {
        String[] parts = value.toString().split("\\.");
        DigitSpellerBG digitSpeller = new DigitSpellerBG();

        String leftPart = digitSpeller.spellout(Integer.parseInt(parts[0]));
        String rightPart = digitSpeller.spellout(Integer.parseInt(parts[1]));

        StringBuilder sb = new StringBuilder();
        sb.append(leftPart.concat(" "));

        if (Integer.parseInt(parts[1]) != 0) {
            sb.append(fullAnd.concat(" "));
            sb.append(rightPart.concat(" "));
        }

        if (value == 1) {
            sb.append(percent);
        } else {
            sb.append(percents);
        }

        return sb.toString();
    }

    public static String spellInteger(Integer period) {
        DigitSpellerBG digitSpeller = new DigitSpellerBG();
        return digitSpeller.spellout(period);
    }

}
