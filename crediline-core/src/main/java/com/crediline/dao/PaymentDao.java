package com.crediline.dao;

import com.crediline.model.Payment;
import org.springframework.stereotype.Repository;

/**
 * Created by dimer on 1/14/14.
 */

@Repository("paymentDao")
public interface PaymentDao extends CredilineRepository<Payment> {
}