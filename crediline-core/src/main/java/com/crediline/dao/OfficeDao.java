package com.crediline.dao;

import com.crediline.model.Office;
import com.crediline.model.Region;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dimer on 1/14/14.
 */

@Repository("officeDao")
public interface OfficeDao extends CredilineRepository<Office> {

    List<Office> findByName(String name, Pageable pageable);

    List<Office> findByRegion(Region region);

    List<Office> findByNameLike(String name, Pageable pageRequest);
}