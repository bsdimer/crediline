package com.crediline.dao;

import com.crediline.dao.common.Specifications;
import com.crediline.model.Credit;
import com.crediline.model.CreditState;
import com.crediline.model.Credit_;
import com.crediline.model.Person;
import com.crediline.model.specifications.CreationDateSpecification;
import com.crediline.model.specifications.CreditApprovedSpecification;
import com.crediline.model.specifications.CreditStateSpecification;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by dimer on 1/14/14.
 */

@Repository("creditDaoSpecification")
public abstract class CreditDao_Specification implements CrudRepository<Credit, Long> {
    public abstract List<Credit> findAll(Specification<Credit> specification);

    public abstract Credit findOne(Specification<Credit> specification);

    public abstract long count(Specification<Credit> specification);

    public List<Credit> findByPerson(Person person) {
        return findAll(new CreditPersonPredicate(person));
    }

    public List<Credit> findByPersonDeep(Person person) {
        return findAll(Specifications.and(
                new CreditPaymentsFetchModifier(),
                new CreditPersonPredicate(person)));
    }

    public List<Credit> findApprovedCreditsInRangeDeep(LocalDateTime fromDate, LocalDateTime toDate) {
        return findAll(Specifications.and(
                new CreditPaymentsFetchModifier(),
                new CreditPersonFetchModifier(),
                new CreationDateSpecification(fromDate, toDate),
                new CreditApprovedSpecification()));
    }

    public List<Credit> findInProgressNotRegisteredCreditsToDate(LocalDateTime toDate) {
        return findAll(Specifications.and(
                new CreditPaymentsFetchModifier(),
                new CreditPersonFetchModifier(),
                new CreationDateSpecification(null, toDate),
                Specifications.not(new CreditRegisteredPredicate()),
                new CreditApprovedSpecification()));
    }

    public List<Credit> findApprovedCreditsByDay(LocalDateTime day) {
        return findAll(Specifications.and(
                new CreationDateSpecification(day),
                new CreditApprovedSpecification()));
    }

    public BigInteger findApprovedCreditsCountByDay(LocalDateTime day) {
        return BigInteger.valueOf(count(Specifications.and(
                new CreationDateSpecification(day),
                new CreditApprovedSpecification()
        )));
    }

    public Credit findInProgressCreditByPersonDeep(Person person) {
        return findOne(Specifications.and(
                new CreditPaymentsFetchModifier(),
                new CreditPersonPredicate(person),
                new CreditStateSpecification(CreditState.IN_PROGRESS)
        ));
    }

    public Credit findInProgressCreditByPerson(Person person) throws NoResultException {
        return findOne(Specifications.and(
                new CreditPersonPredicate(person),
                new CreditStateSpecification(CreditState.IN_PROGRESS)
        ));
    }

    @Modifying
    @Query("update Credit c set c.isRegistered = :isRegistered where c.id = :id")
    public abstract void updateIsRegistered(Long id, Boolean isRegistered);

    public List<Credit> findByGuarantor(Person person, int position) {
        switch (position) {
            case 0:
                return findByGuarantor(person);
            case 1:
                return findByGuarantor1(person);
            case 2:
                return findByGuarantor2(person);
        }

        return null;
    }

    @Query("select c from Credit c where c.guarantor1 = :person or c.guarantor2 = :person ")
    public abstract List<Credit> findByGuarantor(Person person);

    public abstract List<Credit> findByGuarantor1(Person person);

    public abstract List<Credit> findByGuarantor2(Person person);


    public static class CreditPaymentsFetchModifier implements Specification<Credit> {
        @Override
        public Predicate toPredicate(Root<Credit> creditRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            creditRoot.fetch(Credit_.payments);
            return null;
        }
    }

    public static class CreditPersonFetchModifier implements Specification<Credit> {
        @Override
        public Predicate toPredicate(Root<Credit> creditRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            creditRoot.fetch(Credit_.person);
            return null;
        }
    }

    public static class CreditPersonPredicate implements Specification<Credit> {
        private Person person;

        public CreditPersonPredicate(Person person) {
            this.person = person;
        }

        @Override
        public Predicate toPredicate(Root<Credit> creditRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            return criteriaBuilder.equal(creditRoot.get(Credit_.period), person);
        }
    }

    public static class CreditRegisteredPredicate implements Specification<Credit> {
        @Override
        public Predicate toPredicate(Root<Credit> creditRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            return criteriaBuilder.isTrue(creditRoot.get(Credit_.isRegistered));
        }
    }
}