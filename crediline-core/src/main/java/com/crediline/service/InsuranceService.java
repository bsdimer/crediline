package com.crediline.service;

import com.crediline.dao.InsuranceDao;
import com.crediline.model.Insurance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by dimer on 2/21/14.
 */

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
@Service("insuranceService")
public class InsuranceService extends AbstractService<Insurance> {
    @Inject
    private InsuranceDao repository;

    public InsuranceDao getRepository() {
        return repository;
    }
}
