package com.crediline.utils;

import com.google.common.base.Splitter;

import java.util.*;

/**
 * Created by dimer on 3/20/14.
 */
public class DigitSpellerBG implements IDigitSpeller {

    public static Locale getLocale() {
        return LocaleUtils.getLocale("bg_BG");
    }

    private Map<String, String> digits_BG_bg = new HashMap<String, String>() {{
        put("0", "нула");
        put("1", "един");
        put("2", "два");
        put("3", "три");
        put("4", "четири");
        put("5", "пет");
        put("6", "шест");
        put("7", "седем");
        put("8", "осем");
        put("9", "девет");
    }};

    private Map<String, String> from10to20Digits_BG_bg = new HashMap<String, String>() {{
        put("10", "десет");
        put("11", "единадесет");
        put("12", "дванадесет");
        put("13", "тринадесет");
        put("14", "четиринадесет");
        put("15", "петнадесет");
        put("16", "шестнадесет");
        put("17", "седемнадесет");
        put("18", "осемнадесет");
        put("19", "деветнадесет");
    }};

    private Map<String, String> tensDigits_BG_bg = new HashMap<String, String>() {{
        put("1", "десет");
        put("2", "двадесет");
        put("3", "тридесет");
        put("4", "четирдесет");
        put("5", "петдесет");
        put("6", "шестдесет");
        put("7", "седемдесет");
        put("8", "осемдесет");
        put("9", "деветдесет");
    }};

    private Map<String, String> hundredsDigits_BG_bg = new HashMap<String, String>() {{
        put("1", "сто");
        put("2", "двеста");
        put("3", "триста");
        put("4", "четиристотин");
        put("5", "петстотин");
        put("6", "шестстотин");
        put("7", "седемстотин");
        put("8", "осемстотин");
        put("9", "десетстотин");
    }};


    private Map<Integer, String> rangesPluralBig_BG_bg = new HashMap<Integer, String>() {{
        put(RANGE_SINGLES, "");
        put(RANGE_THOUSANDS, "хиляди");
        put(RANGE_BILLIONS, "милиона");
        put(RANGE_TRILLION, "милярда");
    }};

    private Map<Integer, String> rangesBig_BG_bg = new HashMap<Integer, String>() {{
        put(RANGE_SINGLES, "");
        put(RANGE_THOUSANDS, "хиляда");
        put(RANGE_BILLIONS, "милион");
        put(RANGE_TRILLION, "милярд");
    }};

    private String unionWord = "и";

    private String pointWord = "цяло и";

    public final int RANGE_SINGLES = 0;
    public final int RANGE_THOUSANDS = 1;
    public final int RANGE_BILLIONS = 2;
    public final int RANGE_TRILLION = 3;

    public DigitSpellerBG() {
    }


    @Override
    public String spellout(Integer integerDigit) {
        Object[] trippledParts = split2Range(String.valueOf(integerDigit), 3).toArray();
        StringBuilder sb = new StringBuilder();

        for (int i = trippledParts.length - 1; i >= 0; i--) {
            int partLen = ((String) trippledParts[i]).length();
            switch (partLen) {
                case 3:
                    sb.append(sayThreeDigitNumber((String) trippledParts[i], i));
                    break;
                case 2:
                    sb.append(sayTwoDigitNumber((String) trippledParts[i], i));
                    break;
                case 1:
                    sb.append(sayOneDigitNumber((String) trippledParts[i], i));
                    break;
            }
        }
        return sb.toString();
    }

    @Override
    public String spellout(Float floatDigit) {
        String[] parts = floatDigit.toString().split("\\.");
        StringBuilder sb = new StringBuilder();
        if (parts.length > 1 && !"0".equals(parts[0])) {
            sb.append(spellout(Integer.parseInt(parts[0])).concat(" "));
            sb.append(pointWord.concat(" "));
            sb.append(spellout(Integer.parseInt(parts[1])));
        }
        return sb.toString();
    }


    private ArrayList<String> split2Range(String part, int interval) {
        ArrayList<String> result = new ArrayList<String>();
        String reversedString = new StringBuffer(part).reverse().toString();
        Iterable<String> pieces = Splitter.fixedLength(interval).split(reversedString);
        for (String piece : pieces) {
            result.add(new StringBuffer(piece).reverse().toString());
        }
        return result;
    }

    @Override
    public String sayThreeDigitNumber(String number, int range) {
        StringBuilder sb = new StringBuilder();
        String lastTwo = getMostRightDigits(number, 2);

        // when 0xx
        if (number.charAt(0) == '0') {
            // when 00x
            if (number.charAt(1) == '0' && number.charAt(2) != '0') {
                sb.append(unionWord.concat(" "));
                sb.append(sayOneDigitNumber(Character.toString(number.charAt(2)), RANGE_SINGLES));
                return sb.toString().concat(addRangleWord(range, false));
            }

            // when 01x
            if (number.charAt(1) == '1') {
                sb.append(unionWord.concat(" "));
                sb.append(sayTwoDigitNumber(lastTwo, RANGE_SINGLES).concat(" "));
                return sb.toString().concat(addRangleWord(range, false));
            }

            // when 02x
            if (Integer.parseInt(Character.toString(number.charAt(1))) > 1) {
                if (Integer.parseInt(Character.toString(number.charAt(2))) > 1) {
                    // when 022
                    sb.append(sayTwoDigitNumber(lastTwo, RANGE_SINGLES).concat(" "));
                } else {
                    // when 020
                    sb.append(unionWord.concat(" "));
                    sb.append(sayTwoDigitNumber(lastTwo, RANGE_SINGLES).concat(" "));
                }
                return sb.toString().concat(addRangleWord(range, false));
            }
        } else {

            // when 200
            if (number.charAt(1) == '0' && number.charAt(2) == '0') {
                sb.append(unionWord.concat(" "));
                sb.append(hundredsDigits_BG_bg.get(Character.toString(number.charAt(0))).concat(" "));
                return sb.toString().concat(addRangleWord(range, false));
            }

            // when 201
            if (number.charAt(1) == '0') {
                sb.append(hundredsDigits_BG_bg.get(Character.toString(number.charAt(0))).concat(" "));
                sb.append(unionWord.concat(" "));
                sb.append(sayOneDigitNumber(Character.toString(number.charAt(2)), RANGE_SINGLES).concat(" "));
                return sb.toString().concat(addRangleWord(range, false));
            }

            // when 21x or when 220
            if (number.charAt(1) == '1' || number.charAt(2) == '0') {
                sb.append(hundredsDigits_BG_bg.get(Character.toString(number.charAt(0))).concat(" "));
                sb.append(unionWord.concat(" "));
                sb.append(sayTwoDigitNumber(lastTwo, RANGE_SINGLES).concat(" "));
                return sb.toString().concat(addRangleWord(range, false));
            }

            sb.append(hundredsDigits_BG_bg.get(Character.toString(number.charAt(0))).concat(" "));
            sb.append(sayTwoDigitNumber(lastTwo, RANGE_SINGLES).concat(" "));
        }

        return sb.toString().concat(addRangleWord(range, false));
    }


    @Override
    public String sayTwoDigitNumber(String number, int range) {
        StringBuilder sb = new StringBuilder();

        // when 0x
        if (number.charAt(0) == '0') {
            return sb.toString().concat(addRangleWord(range, false));
        }

        // when 1x
        if (number.charAt(0) == '1') {
            sb.append(from10to20Digits_BG_bg.get(number).concat(" "));
            return sb.toString().concat(addRangleWord(range, false));
        }

        // when x0
        if (number.charAt(1) == '0') {
            sb.append(tensDigits_BG_bg.get(Character.toString(number.charAt(0))).concat(" "));
            return sb.toString().concat(addRangleWord(range, false));
        } else {
            // when xx
            sb.append(tensDigits_BG_bg.get(Character.toString(number.charAt(0))).concat(" "));
            sb.append(unionWord.concat(" "));
            sb.append(sayOneDigitNumber(Character.toString(number.charAt(1)), RANGE_SINGLES).concat(" "));
        }

        return sb.toString().concat(addRangleWord(range, false));
    }

    @Override
    public String sayOneDigitNumber(String number, int range) {
        StringBuilder sb = new StringBuilder();

        if ("1".equals(number) && range != RANGE_SINGLES) {
            sb.append(rangesBig_BG_bg.get(range).concat(" "));
            return sb.toString();
        }

        if (range == RANGE_SINGLES) {
            sb.append(digits_BG_bg.get(number).concat(" "));
        } else {
            sb.append(digits_BG_bg.get(Character.toString(number.charAt(0))).concat(" "));
            sb.append(addRangleWord(range, true));
        }

        return sb.toString();
    }

    private String addRangleWord(int range, Boolean plural) {
        if (plural) {
            return rangesPluralBig_BG_bg.get(range).concat(" ");
        }
        return rangesBig_BG_bg.get(range).concat(" ");
    }

    private String getMostRightDigits(String number, int i) {
        return number.substring(number.length() - i, i + 1);
    }

}
