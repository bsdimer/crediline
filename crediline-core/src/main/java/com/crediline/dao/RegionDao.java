package com.crediline.dao;

import com.crediline.model.Region;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dimer on 1/14/14.
 */

@Repository("regionDao")
public interface RegionDao extends CredilineRepository<Region> {
    List<Region> findByName(String name);
}