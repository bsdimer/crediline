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
public class CreditByStreetSpecification extends AbstractSpecification<Credit> {
    private Street street;

    public CreditByStreetSpecification(Street street) {
        this.street = street;
    }

    @Override
    public Predicate toPredicate(PotentialJoin<?, Credit> creditRoot, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        PotentialJoin<Credit, Person> creditPersonJoin = Potentials.join(creditRoot, Credit_.person);
        predicates.add(new PersonAddressSpecification(
                new AddressStreetSpecification(street)
        ).toPredicate(creditPersonJoin, query, builder));

        return Specifications.and(predicates, builder);
    }
}
