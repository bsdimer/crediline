package com.crediline.model.specifications;

import com.crediline.dao.common.AbstractSpecification;
import com.crediline.dao.common.PotentialJoin;
import com.crediline.dao.common.Potentials;
import com.crediline.dao.common.Specifications;
import com.crediline.model.Address;
import com.crediline.model.Person;
import com.crediline.model.Person_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 7/26/14.
 */
public class PersonAddressSpecification extends AbstractSpecification<Person> {
    private AbstractSpecification<Address>[] components;

    @SafeVarargs
    public PersonAddressSpecification(AbstractSpecification<Address>... components) {
        this.components = components;
    }

    @Override
    public Predicate toPredicate(PotentialJoin<?, Person> personRoot, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<Predicate>();

        PotentialJoin<Person, Address> personAddressJoin = Potentials.join(personRoot, Person_.addresses);
        for (AbstractSpecification<Address> component : components) {
            predicates.add(component.toPredicate(personAddressJoin, query, builder));
        }
        return Specifications.and(predicates, builder);
    }
}
