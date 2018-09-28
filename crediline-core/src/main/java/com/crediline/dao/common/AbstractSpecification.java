package com.crediline.dao.common;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by peter on 7/26/14.
 */
public abstract class AbstractSpecification<T> implements Specification<T> {
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return toPredicate(Potentials.get(root), query, cb);
    }

    public abstract Predicate toPredicate(PotentialJoin<?, T> root, CriteriaQuery<?> query, CriteriaBuilder cb);
}
