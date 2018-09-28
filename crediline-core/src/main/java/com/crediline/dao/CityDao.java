package com.crediline.dao;

import com.crediline.model.City;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dimer on 1/14/14.
 */

@Repository("cityDao")
public interface CityDao extends CredilineRepository<City> {
    List<City> findByName(String name, Pageable pageable);

    List<City> findByNameLike(String name, Pageable pageable);
}