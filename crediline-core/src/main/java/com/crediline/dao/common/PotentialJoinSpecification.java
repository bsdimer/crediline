package com.crediline.dao.common;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * Created by peter on 7/26/14.
 */
public interface PotentialJoinSpecification<T> {
    void populatePredicates(PotentialJoin<?, T> personAddressJoin, CriteriaQuery<?> query, CriteriaBuilder builder, List<Predicate> predicates);
}
