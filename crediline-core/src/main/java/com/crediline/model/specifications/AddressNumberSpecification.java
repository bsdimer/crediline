package com.crediline.model.specifications;

import com.crediline.dao.common.AbstractSpecification;
import com.crediline.dao.common.PotentialJoin;
import com.crediline.model.Address;
import com.crediline.model.Address_;
import org.apache.commons.lang.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

/**
 * Created by peter on 7/26/14.
 */
public class AddressNumberSpecification extends AbstractSpecification<Address> {
    private String number;

    public AddressNumberSpecification(String number) {
        this.number = number;
    }

    @Override
    public Predicate toPredicate(PotentialJoin<?, Address> personAddressJoin, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (!StringUtils.isEmpty(number)) {
            return builder.equal(personAddressJoin.get().get(Address_.number), number);
        }
        return null;
    }
}
