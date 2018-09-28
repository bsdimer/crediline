package com.crediline.dao;

import com.crediline.model.Identifiable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Created by peter on 7/26/14.
 */
@NoRepositoryBean
public interface CredilineRepository<T extends Identifiable> extends CrudRepository<T, Long> {
    List<T> findAll();

    List<T> findAll(Specification<T> specification);

    List<T> findAll(Specification<T> specification, Sort sort);

    List<T> findAll(Specification<T> specification, Pageable pageable);
}
