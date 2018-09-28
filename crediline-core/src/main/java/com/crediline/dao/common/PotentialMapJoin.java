package com.crediline.dao.common;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.MapAttribute;

/**
 * Created by peter on 7/26/14.
 */
public class PotentialMapJoin<X, Y> extends PotentialJoin<X, Y> {
    protected MapAttribute<X, ?, Y> attribute;

    public PotentialMapJoin(From<?, X> from, MapAttribute<X, ?, Y> attribute) {
        super(from);
        this.attribute = attribute;
    }

    public PotentialMapJoin(From<?, X> from, JoinType joinType, MapAttribute<X, ?, Y> attribute) {
        super(from, joinType);
        this.attribute = attribute;
    }

    public PotentialMapJoin(PotentialJoin<?, X> chain, MapAttribute<X, ?, Y> attribute) {
        super(chain);
        this.attribute = attribute;
    }

    public PotentialMapJoin(PotentialJoin<?, X> chain, JoinType joinType, MapAttribute<X, ?, Y> attribute) {
        super(chain, joinType);
        this.attribute = attribute;
    }

    @Override
    protected Join<X, Y> constructJoin(From<?, X> from) {
        return from.join(attribute);
    }
}
