package com.crediline.dao.common;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

/**
 * Created by peter on 7/25/14.
 */
public abstract class PotentialJoin<X, Y> {
    protected From<?, X> from;
    protected PotentialJoin<?, X> chain;
    protected Join<X, Y> join;
    protected JoinType joinType;

    public PotentialJoin(From<?, X> from) {
        this.from = from;
    }

    public PotentialJoin(From<?, X> from, JoinType joinType) {
        this.from = from;
        this.joinType = joinType;
    }

    public PotentialJoin(PotentialJoin<?, X> chain) {
        this.chain = chain;
    }

    public PotentialJoin(PotentialJoin<?, X> chain, JoinType joinType) {
        this.chain = chain;
        this.joinType = joinType;
    }


    public From<X, Y> get() {
        return getJoin();
    }

    public Join<X, Y> getJoin() {
        if (join == null) {
            join = constructJoin(chain == null ? from : chain.get());
        }
        return join;
    }

    protected abstract Join<X, Y> constructJoin(From<?, X> from);
}
