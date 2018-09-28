package com.crediline.model;

import com.crediline.utils.CalculatorUtil;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created by dimer on 8/25/14.
 */
public class CreditEntityListener {


    @PrePersist
    public void onCreditPrePersist(Credit credit) {
        CalculatorUtil calculatorUtil = new CalculatorUtil();
        calculatorUtil.sync(credit);
    }

    @PreUpdate
    public void onCreditPreUpdate(Credit credit) {
        CalculatorUtil calculatorUtil = new CalculatorUtil();
        calculatorUtil.sync(credit);
    }
}
