package com.crediline.dao.common;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 7/25/14.
 */
public abstract class CompositeSpcification<T> implements Specification<T> {
    private Specification<T>[] components;

    public CompositeSpcification(Specification<T>[] components) {
        this.components = components;
    }

    protected Predicate[] toPredicatesInternal(Root<T> tRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>(components.length);
        for (Specification<T> component : components) {
            if (component != null) {
                Predicate predicate = component.toPredicate(tRoot, criteriaQuery, criteriaBuilder);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }
        }
        return predicates.toArray(new Predicate[predicates.size()]);
    }
}
