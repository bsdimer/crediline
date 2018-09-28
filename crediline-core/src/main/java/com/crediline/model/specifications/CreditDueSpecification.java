package com.crediline.model.specifications;

import com.crediline.dao.common.AbstractSpecification;
import com.crediline.dao.common.PotentialJoin;
import com.crediline.dao.common.Specifications;
import com.crediline.model.Credit;
import com.crediline.model.Credit_;
import org.joda.time.LocalDateTime;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 7/26/14.
 */
public class CreditDueSpecification extends AbstractSpecification<Credit> {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public CreditDueSpecification(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public Predicate toPredicate(PotentialJoin<?, Credit> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>(2);
        if (startDate != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get().get(Credit_.dueDate), startDate));
        }

        if (endDate != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get().get(Credit_.dueDate), endDate));
        }
        return Specifications.and(predicates, cb);
    }
}
