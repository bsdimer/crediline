package com.crediline.dao;

import java.util.List;

/**
 * Created by dimer on 1/29/14.
 */
public interface IGenericDao<T, K> {
    void beginTransaction();

    void commit();

    void rollback();

    void closeTransaction();

    void commitAndCloseTransaction();

    void flush();

    void joinTransaction();

    void save(T entity);

    void delete(T entity);

    T update(T entity);

    T find(K entityID);

    T findReferenceOnly(K entityID);

    // Using the unchecked because JPA does not have a
    // em.getCriteriaBuilder().createQuery()<T> method
    @SuppressWarnings({"unchecked", "rawtypes"})
    List<T> findAll();
}