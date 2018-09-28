package com.crediline.model.specifications;

import com.crediline.model.CreditState;

/**
 * Created by peter on 7/26/14.
 */
public class CreditApprovedSpecification extends CreditStateSpecification {
    public CreditApprovedSpecification() {
        super(CreditState.INITIAL, CreditState.DECLINED, CreditState.INVALID);
    }
}
