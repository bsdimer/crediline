package com.crediline.dao.common;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.SetAttribute;

/**
 * Created by peter on 7/26/14.
 */
public class PotentialSetJoin<X, Y> extends PotentialJoin<X, Y> {
    protected SetAttribute<X, Y> attribute;

    public PotentialSetJoin(From<?, X> from, SetAttribute<X, Y> attribute) {
        super(from);
        this.attribute = attribute;
    }

    public PotentialSetJoin(From<?, X> from, JoinType joinType, SetAttribute<X, Y> attribute) {
        super(from, joinType);
        this.attribute = attribute;
    }

    public PotentialSetJoin(PotentialJoin<?, X> chain, SetAttribute<X, Y> attribute) {
        super(chain);
        this.attribute = attribute;
    }

    public PotentialSetJoin(PotentialJoin<?, X> chain, JoinType joinType, SetAttribute<X, Y> attribute) {
        super(chain, joinType);
        this.attribute = attribute;
    }

    @Override
    protected Join<X, Y> constructJoin(From<?, X> from) {
        return from.join(attribute);
    }
}
