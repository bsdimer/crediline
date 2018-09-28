package com.crediline.model.specifications;

import com.crediline.dao.common.AbstractSpecification;
import com.crediline.dao.common.PotentialJoin;
import com.crediline.dao.common.Specifications;
import com.crediline.model.Address;
import com.crediline.model.Address_;
import org.apache.commons.lang.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 7/26/14.
 */
public class AddressDetailsSpecification extends AbstractSpecification<Address> {
    String quarter;
    String apartment;
    String floor;

    public AddressDetailsSpecification(String quarter, String apartment, String floor) {
        this.quarter = quarter;
        this.apartment = apartment;
        this.floor = floor;
    }

    @Override
    public Predicate toPredicate(PotentialJoin<?, Address> personAddressJoin, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isEmpty(quarter)) {
            predicates.add(builder.equal(personAddressJoin.get().get(Address_.quarter), quarter));
        }
        if (!StringUtils.isEmpty(apartment)) {
            predicates.add(builder.equal(personAddressJoin.get().get(Address_.apartment), apartment));
        }
        if (!StringUtils.isEmpty(floor)) {
            predicates.add(builder.equal(personAddressJoin.get().get(Address_.floor), floor));
        }
        return Specifications.and(predicates, builder);
    }
}
