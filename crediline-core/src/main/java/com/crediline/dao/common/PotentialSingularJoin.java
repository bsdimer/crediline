package com.crediline.dao.common;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.SingularAttribute;

/**
 * Created by peter on 7/26/14.
 */
public class PotentialSingularJoin<X, Y> extends PotentialJoin<X, Y> {
    protected SingularAttribute<X, Y> attribute;

    public PotentialSingularJoin(From<?, X> from, SingularAttribute<X, Y> attribute) {
        super(from);
        this.attribute = attribute;
    }

    public PotentialSingularJoin(From<?, X> from, JoinType joinType, SingularAttribute<X, Y> attribute) {
        super(from, joinType);
        this.attribute = attribute;
    }

    public PotentialSingularJoin(PotentialJoin<?, X> chain, SingularAttribute<X, Y> attribute) {
        super(chain);
        this.attribute = attribute;
    }

    public PotentialSingularJoin(PotentialJoin<?, X> chain, JoinType joinType, SingularAttribute<X, Y> attribute) {
        super(chain, joinType);
        this.attribute = attribute;
    }

    @Override
    protected Join<X, Y> constructJoin(From<?, X> from) {
        return from.join(attribute);
    }
}
