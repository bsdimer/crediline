package com.crediline.model.specifications;

import com.crediline.dao.common.AbstractSpecification;
import com.crediline.dao.common.PotentialJoin;
import com.crediline.dao.common.Specifications;
import com.crediline.model.Person;
import com.crediline.model.Person_;
import org.apache.commons.lang.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 7/26/14.
 */
public class PersonNameSpecification extends AbstractSpecification<Person> {
    private String name;
    private String midname;
    private String surname;

    public PersonNameSpecification(String fullName) {
        String[] nameParts = fullName.split(" ");
        switch (nameParts.length) {
            case 3:
                surname = nameParts[2];
            case 2:
                midname = nameParts[1];
            case 1:
                name = nameParts[0];
        }
    }

    public PersonNameSpecification(String name, String midname, String surname) {
        this.name = name;
        this.midname = midname;
        this.surname = surname;
    }

    @Override
    public Predicate toPredicate(PotentialJoin<?, Person> personRoot, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isEmpty(name)) {
            predicates.add(builder.like(personRoot.get().get(Person_.name), name + "%"));
        }
        if (!StringUtils.isEmpty(midname)) {
            predicates.add(builder.like(personRoot.get().get(Person_.midname), midname + "%"));
        }
        if (!StringUtils.isEmpty(surname)) {
            predicates.add(builder.like(personRoot.get().get(Person_.surname), surname + "%"));
        }

        return Specifications.and(predicates, builder);
    }
}
