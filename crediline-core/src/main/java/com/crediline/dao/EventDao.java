package com.crediline.dao;

import com.crediline.model.Event;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dimer on 1/14/14.
 */

@Repository
public interface EventDao extends CredilineRepository<Event> {
    List<Event> findAll(Sort sort);
}