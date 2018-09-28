package com.crediline.service;

import com.crediline.dao.CityDao;
import com.crediline.dao.CredilineRepository;
import com.crediline.model.City;
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
@Service("cityService")
public class CityService extends AbstractService<City> {

    @Inject
    private CityDao repository;

    @Override
    protected CredilineRepository<City> getRepository() {
        return repository;
    }

    public List<City> findByName(String name, Pageable pageable) {
        return repository.findByName(name, pageable);
    }

    public List<City> findByNameLike(String name, Pageable pageRequest) {
        return repository.findByNameLike(name.trim() + "%", pageRequest);
    }
}
