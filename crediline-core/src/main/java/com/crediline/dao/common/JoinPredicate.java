package com.crediline.dao.common;

import com.google.common.base.Predicate;

import javax.persistence.criteria.Join;
import javax.persistence.metamodel.Attribute;

/**
 * Created by peter on 7/27/14.
 */
public class JoinPredicate<T> implements Predicate<Join<T, ?>> {
    private Attribute<T, ?> attribute;

    public JoinPredicate(Attribute<T, ?> attribute) {
        this.attribute = attribute;
    }

    @Override
    public boolean apply(Join<T, ?> personJoin) {
        return personJoin.getAttribute().equals(attribute);
    }
}
