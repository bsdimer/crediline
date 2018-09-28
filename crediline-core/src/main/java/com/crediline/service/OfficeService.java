package com.crediline.service;

import com.crediline.dao.OfficeDao;
import com.crediline.model.Office;
import com.crediline.model.Region;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by dimer on 2/21/14.
 */

/**
 * This class should be changed to use AoP to update the accessible properties of the entities
 */

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
@Service("officeService")
public class OfficeService extends AbstractService<Office> {
    @Inject
    private OfficeDao repository;

    public OfficeDao getRepository() {
        return repository;
    }

    public List<Office> findByName(String name, Pageable pageable) {
        return repository.findByName(name, pageable);
    }

    public List<Office> findByRegion(Region region) {
        return repository.findByRegion(region);
    }

    public List<Office> findByNameLike(String name, Pageable pageRequest) {
        return repository.findByNameLike(name.trim() + "%", pageRequest);
    }
}
