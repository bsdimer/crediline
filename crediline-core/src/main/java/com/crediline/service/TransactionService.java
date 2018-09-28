package com.crediline.service;

import com.crediline.dao.TransactionDao;
import com.crediline.model.Identifiable;
import com.crediline.model.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by dimer on 2/21/14.
 */

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
@Service("transactionService")
public class TransactionService extends AbstractService<Transaction> {
    @Inject
    private TransactionDao repository;

    public TransactionDao getRepository() {
        return repository;
    }
}
