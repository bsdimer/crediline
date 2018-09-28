package com.crediline.model.specifications;

import com.crediline.dao.common.AbstractSpecification;
import com.crediline.dao.common.PotentialJoin;
import com.crediline.model.Address;
import com.crediline.model.Address_;
import com.crediline.model.Street;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

/**
 * Created by peter on 7/26/14.
 */
public class AddressStreetSpecification extends AbstractSpecification<Address> {
    private Street street;

    public AddressStreetSpecification(Street street) {
        this.street = street;
    }

    @Override
    public Predicate toPredicate(PotentialJoin<?, Address> personAddressJoin, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (street != null) {
            return builder.equal(personAddressJoin.get().get(Address_.street), street);
        }
        return null;
    }
}
