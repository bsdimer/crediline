package com.crediline.service;

import com.crediline.dao.PaymentDao;
import com.crediline.model.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by dimer on 2/21/14.
 */

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
@Service("paymentService")
public class PaymentService extends AbstractService<Payment> {
    @Inject
    private PaymentDao repository;

    public PaymentDao getRepository() {
        return repository;
    }
}
