package com.crediline.dao;

import com.crediline.model.Insurance;
import org.springframework.stereotype.Repository;

/**
 * Created by dimer on 1/14/14.
 */

@Repository("insuranceDao")
public interface InsuranceDao extends CredilineRepository<Insurance> {
}