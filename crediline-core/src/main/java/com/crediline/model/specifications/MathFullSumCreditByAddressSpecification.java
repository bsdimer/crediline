package com.crediline.model.specifications;

import com.crediline.dao.common.AbstractSpecification;
import com.crediline.dao.common.PotentialJoin;
import com.crediline.dao.common.Potentials;
import com.crediline.dao.common.Specifications;
import com.crediline.model.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 7/26/14.
 */
public class MathFullSumCreditByAddressSpecification extends AbstractSpecification<Credit> {
    private Address address;

    public MathFullSumCreditByAddressSpecification(Address address) {
        this.address = address;
    }

    @Override
    public Predicate toPredicate(PotentialJoin<?, Credit> creditRoot, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        City city = address.getCity();
        Street street = address.getStreet();
        String number = address.getNumber();

        PotentialJoin<Credit, Person> creditPersonJoin = Potentials.join(creditRoot, Credit_.person);
        predicates.add(new PersonAddressSpecification(
                new AddressCitySpecification(city),
                new AddressStreetSpecification(street),
                new AddressNumberSpecification(number)
        ).toPredicate(creditPersonJoin, query, builder));

        return Specifications.and(predicates, builder);
    }
}
