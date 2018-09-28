package com.crediline.service;

import com.crediline.dao.StreetDao;
import com.crediline.model.Street;
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

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
@Service("streetService")
public class StreetService extends AbstractService<Street> {
    @Inject
    private StreetDao repository;

    public StreetDao getRepository() {
        return repository;
    }

    public List<Street> findByName(String name, Pageable pageable) {
        return repository.findByName(name, pageable);
    }

    public List<Street> findByNameLike(String name, Pageable pageRequest) {
        return repository.findByNameLike(name.trim() + "%", pageRequest);
    }
}
