package com.crediline.dao.common;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.ListAttribute;

/**
 * Created by peter on 7/26/14.
 */
public class PotentialListJoin<X, Y> extends PotentialJoin<X, Y> {
    protected ListAttribute<X, Y> attribute;

    public PotentialListJoin(From<?, X> from, ListAttribute<X, Y> attribute) {
        super(from);
        this.attribute = attribute;
    }

    public PotentialListJoin(From<?, X> from, JoinType joinType, ListAttribute<X, Y> attribute) {
        super(from, joinType);
        this.attribute = attribute;
    }

    public PotentialListJoin(PotentialJoin<?, X> chain, ListAttribute<X, Y> attribute) {
        super(chain);
        this.attribute = attribute;
    }

    public PotentialListJoin(PotentialJoin<?, X> chain, JoinType joinType, ListAttribute<X, Y> attribute) {
        super(chain, joinType);
        this.attribute = attribute;
    }

    @Override
    protected Join<X, Y> constructJoin(From<?, X> from) {
        return from.join(attribute);
    }
}
