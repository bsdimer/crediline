package com.crediline.dao.common;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by peter on 7/25/14.
 */
public class Negation<T> implements Specification<T> {
    private Specification<T> original;

    public Negation(Specification<T> original) {
        this.original = original;
    }

    @Override
    public Predicate toPredicate(Root<T> tRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.not(original.toPredicate(tRoot, criteriaQuery, criteriaBuilder));
    }
}
