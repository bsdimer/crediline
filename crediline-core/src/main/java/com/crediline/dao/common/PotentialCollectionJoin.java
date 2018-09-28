package com.crediline.dao.common;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.CollectionAttribute;

/**
 * Created by peter on 7/26/14.
 */
public class PotentialCollectionJoin<X, Y> extends PotentialJoin<X, Y> {
    protected CollectionAttribute<X, Y> attribute;

    public PotentialCollectionJoin(From<?, X> from, CollectionAttribute<X, Y> attribute) {
        super(from);
        this.attribute = attribute;
    }

    public PotentialCollectionJoin(From<?, X> from, JoinType joinType, CollectionAttribute<X, Y> attribute) {
        super(from, joinType);
        this.attribute = attribute;
    }

    public PotentialCollectionJoin(PotentialJoin<?, X> chain, CollectionAttribute<X, Y> attribute) {
        super(chain);
        this.attribute = attribute;
    }

    public PotentialCollectionJoin(PotentialJoin<?, X> chain, JoinType joinType, CollectionAttribute<X, Y> attribute) {
        super(chain, joinType);
        this.attribute = attribute;
    }

    @Override
    protected Join<X, Y> constructJoin(From<?, X> from) {
        return from.join(attribute);
    }
}
