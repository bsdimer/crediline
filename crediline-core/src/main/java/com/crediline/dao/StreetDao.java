package com.crediline.dao;

import com.crediline.model.Street;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dimer on 1/14/14.
 */

@Repository("streetDao")
public interface StreetDao extends CredilineRepository<Street> {
    List<Street> findByName(String name, Pageable pageable);

    List<Street> findByNameLike(String name, Pageable pageRequest);
}