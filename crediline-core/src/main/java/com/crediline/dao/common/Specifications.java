package com.crediline.dao.common;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.List;

/**
 * Created by peter on 7/25/14.
 */
public final class Specifications {
    private Specifications() {
    }

    @SafeVarargs
    public static <T> Specification<T> and(Specification<T>... components) {
        return new Conjunction<>(components);
    }

    @SafeVarargs
    public static <T> Specification<T> or(Specification<T>... components) {
        return new Disjunction<>(components);
    }

    public static <T> Specification<T> not(Specification<T> original) {
        return new Negation<>(original);
    }

    public static Predicate and(List<Predicate> predicates, CriteriaBuilder builder) {
        Predicate[] predicatesArray = toArray(predicates);
        return predicatesArray.length == 0 ? null : builder.and(predicatesArray);
    }

    public static Predicate or(List<Predicate> predicates, CriteriaBuilder builder) {
        Predicate[] predicatesArray = toArray(predicates);
        return predicatesArray.length == 0 ? null : builder.or(predicatesArray);


    }

    public static Predicate[] toArray(List<Predicate> predicates) {
        Collection<Predicate> notNullPredicates = Collections2.filter(predicates, Predicates.notNull());
        return notNullPredicates.toArray(new Predicate[notNullPredicates.size()]);
    }
}
