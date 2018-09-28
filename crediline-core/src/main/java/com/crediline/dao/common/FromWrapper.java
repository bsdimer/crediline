package com.crediline.dao.common;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;

/**
 * Created by peter on 7/26/14.
 */
public class FromWrapper<X, Y> extends PotentialJoin<X, Y> {
    private From<X, Y> from;

    public FromWrapper(From<X, Y> from) {
        super((From<?, X>) null);
        this.from = from;
    }

    @Override
    public From<X, Y> get() {
        return from;
    }

    @Override
    public Join<X, Y> getJoin() {
        return null;
    }

    @Override
    protected Join<X, Y> constructJoin(From<?, X> from) {
        throw new UnsupportedOperationException();
    }
}
