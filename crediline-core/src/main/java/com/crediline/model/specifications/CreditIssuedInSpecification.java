package com.crediline.model.specifications;

import com.crediline.dao.common.AbstractSpecification;
import com.crediline.dao.common.PotentialJoin;
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
public class CreditIssuedInSpecification extends AbstractSpecification<Credit> {
    private Office office;
    private Region region;
    private User currentUser;

    public CreditIssuedInSpecification(Office office, Region region, User currentUser) {
        this.office = office;
        this.region = region;
        this.currentUser = currentUser;
    }

    @Override
    public Predicate toPredicate(PotentialJoin<?, Credit> creditRoot, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (office != null && office.getId() != null) {
            predicates.add(builder.equal(creditRoot.get().get(Credit_.createdIn), office));
        }

        if (currentUser.getRole().equals(Role.ROLE_SUPERADMIN)) {
            // Superadmin can view credits for all regions
            if (region != null && region.getId() != null) {
                predicates.add(builder.equal(creditRoot.get().get(Credit_.createdIn).get(Office_.region), region));
            }
        } else {
            // Every other role can view only the credits from the region to which it belongs
            region = currentUser.getOffice().getRegion();
            predicates.add(builder.equal(creditRoot.get().get(Credit_.createdIn).get(Office_.region), region));
        }
        return Specifications.and(predicates, builder);
    }
}
