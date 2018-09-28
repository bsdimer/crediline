package com.crediline.dao;

import com.crediline.model.Province;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dimer on 1/14/14.
 */

@Repository("provinceDao")
public interface ProvinceDao extends CredilineRepository<Province> {
    List<Province> findByName(String name, Pageable pageable);

    List<Province> findByNameLike(String name, Pageable pageable);
}

