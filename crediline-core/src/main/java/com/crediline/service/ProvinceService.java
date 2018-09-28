package com.crediline.service;

import com.crediline.dao.ProvinceDao;
import com.crediline.model.Province;
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
@Service("provinceService")
public class ProvinceService extends AbstractService<Province> {
    @Inject
    private ProvinceDao repository;

    public ProvinceDao getRepository() {
        return repository;
    }

    public List<Province> findByName(String name, Pageable pageable) {
        return repository.findByName(name, pageable);
    }

    public List<Province> findByNameLike(String name, Pageable pageRequest) {
        return repository.findByNameLike(name.trim() + "%", pageRequest);
    }
}
