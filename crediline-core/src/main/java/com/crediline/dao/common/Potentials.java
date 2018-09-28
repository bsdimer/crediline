package com.crediline.dao.common;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.*;

/**
 * Created by peter on 7/26/14.
 */
public class Potentials {
    public static <X, Y> PotentialJoin<X, Y> join(From<?, X> from, SingularAttribute<X, Y> attribute) {
        return new PotentialSingularJoin<X, Y>(from, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(From<?, X> from, SingularAttribute<X, Y> attribute, JoinType joinType) {
        return new PotentialSingularJoin<X, Y>(from, joinType, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(From<?, X> from, ListAttribute<X, Y> attribute) {
        return new PotentialListJoin<>(from, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(From<?, X> from, ListAttribute<X, Y> attribute, JoinType joinType) {
        return new PotentialListJoin<X, Y>(from, joinType, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(From<?, X> from, CollectionAttribute<X, Y> attribute) {
        return new PotentialCollectionJoin<>(from, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(From<?, X> from, CollectionAttribute<X, Y> attribute, JoinType joinType) {
        return new PotentialCollectionJoin<X, Y>(from, joinType, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(From<?, X> from, SetAttribute<X, Y> attribute) {
        return new PotentialSetJoin<>(from, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(From<?, X> from, SetAttribute<X, Y> attribute, JoinType joinType) {
        return new PotentialSetJoin<X, Y>(from, joinType, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(From<?, X> from, MapAttribute<X, ?, Y> attribute) {
        return new PotentialMapJoin<>(from, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(From<?, X> from, MapAttribute<X, ?, Y> attribute, JoinType joinType) {
        return new PotentialMapJoin<X, Y>(from, joinType, attribute);
    }


    public static <X, Y> PotentialJoin<X, Y> join(PotentialJoin<?, X> from, SingularAttribute<X, Y> attribute) {
        return new PotentialSingularJoin<X, Y>(from, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(PotentialJoin<?, X> from, SingularAttribute<X, Y> attribute, JoinType joinType) {
        return new PotentialSingularJoin<X, Y>(from, joinType, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(PotentialJoin<?, X> from, ListAttribute<X, Y> attribute) {
        return new PotentialListJoin<>(from, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(PotentialJoin<?, X> from, ListAttribute<X, Y> attribute, JoinType joinType) {
        return new PotentialListJoin<X, Y>(from, joinType, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(PotentialJoin<?, X> from, CollectionAttribute<X, Y> attribute) {
        return new PotentialCollectionJoin<>(from, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(PotentialJoin<?, X> from, CollectionAttribute<X, Y> attribute, JoinType joinType) {
        return new PotentialCollectionJoin<X, Y>(from, joinType, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(PotentialJoin<?, X> from, SetAttribute<X, Y> attribute) {
        return new PotentialSetJoin<>(from, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(PotentialJoin<?, X> from, SetAttribute<X, Y> attribute, JoinType joinType) {
        return new PotentialSetJoin<X, Y>(from, joinType, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(PotentialJoin<?, X> from, MapAttribute<X, ?, Y> attribute) {
        return new PotentialMapJoin<>(from, attribute);
    }

    public static <X, Y> PotentialJoin<X, Y> join(PotentialJoin<?, X> from, MapAttribute<X, ?, Y> attribute, JoinType joinType) {
        return new PotentialMapJoin<X, Y>(from, joinType, attribute);
    }


    public static <X, Y> PotentialJoin<X, Y> get(Join<X, Y> join) {
        return new JoinWrapper<>(join);
    }

    public static <X, Y> PotentialJoin<X, Y> get(From<X, Y> from) {
        return new FromWrapper<>(from);
    }
}