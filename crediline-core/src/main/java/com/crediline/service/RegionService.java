package com.crediline.service;

import com.crediline.dao.RegionDao;
import com.crediline.model.Region;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by dimer on 2/21/14.
 */

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
@Service("regionService")
public class RegionService extends AbstractService<Region> {
    @Inject
    private RegionDao repository;

    public RegionDao getRepository() {
        return repository;
    }
}
