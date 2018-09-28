package com.crediline.utils;

/**
 * Created by dimer on 3/21/14.
 */
public interface IDigitSpeller {
    String spellout(Integer integerDigit);

    String spellout(Float floatDigit);

    String sayThreeDigitNumber(String number, int range);

    String sayTwoDigitNumber(String number, int range);

    String sayOneDigitNumber(String number, int range);
}
