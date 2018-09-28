package com.crediline.service;

import com.crediline.dao.PersonDao;
import com.crediline.dao.common.PotentialJoin;
import com.crediline.dao.common.Potentials;
import com.crediline.model.Credit;
import com.crediline.model.Person;
import com.crediline.model.Person_;
import com.crediline.model.specifications.CreationDateSpecification;
import com.crediline.model.specifications.CreditApprovedSpecification;
import com.crediline.model.specifications.PersonNameSpecification;
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by dimer on 2/21/14.
 */

/**
 * This class should be changed to use AoP to update the accessible properties of the entities
 */

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
@Service("personService")
public class PersonService extends AbstractService<Person> {

    @Inject
    private PersonDao repository;

    public PersonDao getRepository() {
        return repository;
    }

    public List<Person> findByName(String name) {
        return repository.findByName(name);
    }

    public List<Person> findByEgn(String egn) {
        return repository.findByEgn(egn);
    }

    public List<Person> findByName(String name, Pageable pageable) {
        return repository.findByName(name, pageable);
    }

    public List<Person> findByEgnLike(String egn, Pageable pageable) {
        return repository.findByEgnLike(egn.trim() + "%", pageable);
    }

    public List<Person> findByEgnLikeEager(String egn) {
        return repository.findByEgnLikeEager(egn.trim() + "%");
    }

    public List<Person> findByEgnLikeEager(String egn, Pageable pageable) {
        return repository.findByEgnLikeEager(egn.trim() + "%", pageable);
    }

    public List<Person> findByEgn(String egn, Pageable pageable) {
        return repository.findByEgn(egn, pageable);
    }

    public List<Person> findBorrowersInRange(LocalDateTime fromDate, LocalDateTime toDate) {
        return findAll(new BorrowerInRangeSpecification(fromDate, toDate));
    }

    public List<Person> findByFullName(String name, int limit) {
        return findAll(new PersonNameSpecification(name), new PageRequest(0, limit));
    }

    public List<Person> findByFullNameEager(String fullName, int limit) {
        String[] nameParts = fullName.split("\\s+");
        switch (nameParts.length) {
            case 3:
                return repository.findByFullNameLikeEager(nameParts[0], nameParts[1], nameParts[2]);
            case 2:
                return repository.findByFullNameLikeEager(nameParts[0], nameParts[1]);
            case 1:
                return repository.findByFullNameLikeEager(nameParts[0]);
        }
        return findAll(new PersonNameSpecification(fullName), new PageRequest(0, limit));
    }

    public static class BorrowerInRangeSpecification implements Specification<Person> {
        private LocalDateTime fromDate;
        private LocalDateTime toDate;

        public BorrowerInRangeSpecification(LocalDateTime toDate, LocalDateTime fromDate) {
            this.toDate = toDate;
            this.fromDate = fromDate;
        }

        @Override
        public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            PotentialJoin<Person, Credit> creditJoin = Potentials.get(root.join(Person_.credits));
            return cb.and(
                    cb.ge(cb.count(creditJoin.get()), 1),
                    new CreationDateSpecification<Credit>(fromDate, toDate).toPredicate(creditJoin, query, cb),
                    new CreditApprovedSpecification().toPredicate(creditJoin, query, cb));

        }
    }
}
