package com.crediline.model.specifications;

import com.crediline.dao.common.AbstractSpecification;
import com.crediline.dao.common.PotentialJoin;
import com.crediline.model.Address;
import com.crediline.model.Address_;
import com.crediline.model.City;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

/**
 * Created by peter on 7/26/14.
 */
public class AddressCitySpecification extends AbstractSpecification<Address> {
    private City city;

    public AddressCitySpecification(City city) {
        this.city = city;
    }

    @Override
    public Predicate toPredicate(PotentialJoin<?, Address> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (city != null) {
            return cb.equal(root.get().get(Address_.city), city);
        }
        return null;
    }
}
