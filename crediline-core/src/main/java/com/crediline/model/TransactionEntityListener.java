package com.crediline.model;

import com.crediline.utils.CalculatorUtil;

import javax.persistence.*;

/**
 * Created by dimer on 8/27/14.
 */
public class TransactionEntityListener {

    @PreUpdate
    void preUpdate(Transaction transaction) {
        transaction.setState(TransactionState.COMPLETED);
    }

    @PrePersist
    void prePersist(Transaction transaction) {
        transaction.setState(TransactionState.COMPLETED);
    }

    @PostRemove
    void postRemove(Transaction transaction) {
        if (transaction.getCredit() != null) {
            CalculatorUtil calculatorUtil = new CalculatorUtil();
            calculatorUtil.sync(transaction.getCredit());
        }
    }

    @PostUpdate
    void postUpdate(Transaction transaction) {
        if (transaction.getCredit() != null) {
            CalculatorUtil calculatorUtil = new CalculatorUtil();
            calculatorUtil.sync(transaction.getCredit());
        }
    }

    @PostPersist
    void postPersist(Transaction transaction) {
        if (transaction.getCredit() != null) {
            CalculatorUtil calculatorUtil = new CalculatorUtil();
            calculatorUtil.sync(transaction.getCredit());
        }
    }
}
