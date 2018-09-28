package com.crediline.model.specifications;

import com.crediline.dao.common.AbstractSpecification;
import com.crediline.dao.common.PotentialJoin;
import com.crediline.dao.common.Specifications;
import com.crediline.model.Credit;
import com.crediline.model.CreditState;
import com.crediline.model.Credit_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 7/26/14.
 */
public class CreditActiveSpecification extends AbstractSpecification<Credit> {


    @Override
    public Predicate toPredicate(PotentialJoin<?, Credit> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>(2);
        predicates.add(cb.equal(root.get().get(Credit_.creditState), CreditState.OVERDUE));
        predicates.add(cb.equal(root.get().get(Credit_.creditState), CreditState.IN_PROGRESS));
        return Specifications.or(predicates, cb);
    }
}
