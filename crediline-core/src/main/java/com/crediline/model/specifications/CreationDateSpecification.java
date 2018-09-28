package com.crediline.model.specifications;

import com.crediline.dao.common.AbstractSpecification;
import com.crediline.dao.common.PotentialJoin;
import com.crediline.dao.common.Specifications;
import com.crediline.model.PersistedVersional;
import com.crediline.model.PersistedVersional_;
import org.joda.time.LocalDateTime;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 7/26/14.
 */
public class CreationDateSpecification<T extends PersistedVersional> extends AbstractSpecification<T> {
    private LocalDateTime startDate;
    private LocalDateTime endDate;


    public CreationDateSpecification(LocalDateTime day) {
        this.startDate = day;
        this.endDate = day;
    }

    public CreationDateSpecification(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public Predicate toPredicate(PotentialJoin<?, T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>(2);
        if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get().get(PersistedVersional_.creationDate), startDate));
        }

        if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get().get(PersistedVersional_.creationDate), endDate));
        }
        return Specifications.and(predicates, cb);
    }
}
