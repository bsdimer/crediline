package com.crediline.dao.common;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by peter on 7/25/14.
 */
public class Conjunction<T> extends CompositeSpcification<T> {
    private Specification<T>[] components;

    public Conjunction(Specification<T>... components) {
        super(components);
    }

    @Override
    public Predicate toPredicate(Root<T> tRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(toPredicatesInternal(tRoot, criteriaQuery, criteriaBuilder));
    }
}
