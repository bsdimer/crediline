package com.crediline.dao.common;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;

/**
 * Created by peter on 7/26/14.
 */
public class JoinWrapper<X, Y> extends PotentialJoin<X, Y> {
    private Join<X, Y> wrapped;

    public JoinWrapper(Join<X, Y> join) {
        super((From<?, X>) null);
        this.wrapped = join;
    }

    @Override
    public From<X, Y> get() {
        return wrapped;
    }

    @Override
    public Join<X, Y> getJoin() {
        return wrapped;
    }

    @Override
    protected Join<X, Y> constructJoin(From<?, X> from) {
        throw new UnsupportedOperationException();
    }
}
