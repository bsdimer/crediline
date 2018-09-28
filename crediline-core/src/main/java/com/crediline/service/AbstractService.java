package com.crediline.service;

import com.crediline.dao.CredilineRepository;
import com.crediline.model.Identifiable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by peter on 7/27/14.
 */
public abstract class AbstractService<T extends Identifiable> implements CredilineRepository<T> {
    @Override
    public List<T> findAll() {
        return getRepository().findAll();
    }

    public <S extends T> S save(S entity) {
        return getRepository().save(entity);
    }

    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        return getRepository().save(entities);
    }

    @Override
    public T findOne(Long aLong) {
        return getRepository().findOne(aLong);
    }

    @Override
    public boolean exists(Long aLong) {
        return getRepository().exists(aLong);
    }

    @Override
    public Iterable<T> findAll(Iterable<Long> longs) {
        return getRepository().findAll(longs);
    }

    public List<T> findAll(Specification<T> specification) {
        return getRepository().findAll(specification);
    }

    public List<T> findAll(Specification<T> specification, Sort sort) {
        return getRepository().findAll(specification, sort);
    }

    public List<T> findAll(Specification<T> specification, Pageable pageable) {
        return getRepository().findAll(specification, pageable);
    }

    @Override
    public long count() {
        return getRepository().count();
    }

    @Override
    public void delete(Long aLong) {
        getRepository().delete(aLong);
    }

    public void delete(T entity) {
        getRepository().delete(entity);
    }

    public void delete(Iterable<? extends T> entities) {
        getRepository().delete(entities);
    }

    @Override
    public void deleteAll() {
        getRepository().deleteAll();
    }

    protected abstract CredilineRepository<T> getRepository();
}
