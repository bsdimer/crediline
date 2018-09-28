package com.crediline.service;

import com.crediline.dao.CreditDao;
import com.crediline.model.*;
import com.crediline.model.specifications.*;
import com.crediline.utils.CalculatorUtil;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by dimer on 2/21/14.
 */

@Service("creditService")
public class CreditService extends AbstractService<Credit> {

    @Inject
    CreditDao repository;

    @Inject
    CalculatorUtil calculator;

    public CreditDao getRepository() {
        return repository;
    }

    public List<Credit> findByGuarantor1(Person person) {
        return repository.findByGuarantor1(person);
    }

    public List<Credit> findApprovedCreditsInRangeDeep(LocalDateTime fromDate, LocalDateTime toDate) {
        return repository.findApprovedCreditsInRangeDeep(fromDate, toDate);
    }

    public List<Credit> findInProgressCreditByPerson(Person person) {
        return repository.findInProgressCreditByPerson(person);
    }

    public List<Credit> findActiveCreditByPerson(Person person) {
        return repository.findActiveCreditByPerson(person);
    }

    public List<Credit> findInProgressNotRegisteredCreditsToDate(LocalDateTime toDate) {
        return repository.findInProgressNotRegisteredCreditsToDate(toDate);
    }

    public List<Credit> findByGuarantor(Person person) {
        return repository.findByGuarantor(person);
    }

    public List<Credit> findActiveByGuarantor(Person person) {
        return repository.findActiveByGuarantor(person);
    }

    public List<Credit> findByPerson(Person person) {
        return repository.findByPerson(person);
    }

    public void updateIsRegistered(Long id, Boolean isRegistered) {
        repository.updateIsRegistered(id, isRegistered);
    }

    public BigInteger findApprovedCreditsCountByDay(LocalDateTime day) {
        return repository.findApprovedCreditsCountByDay(day);
    }

    public List<Credit> findByPersonDeep(Person person) {
        return repository.findByPersonDeep(person);
    }

    public Credit findInProgressCreditByPersonDeep(Person person) {
        return repository.findInProgressCreditByPersonDeep(person);
    }

    public List<Credit> findByGuarantor2(Person person) {
        return repository.findByGuarantor2(person);
    }

    public List<Credit> findApprovedCreditsByDay(LocalDateTime day) {
        return repository.findApprovedCreditsByDay(day);
    }

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

    @Transactional
    public Credit saveWithSync(Credit credit) {
        calculator.sync(credit);
        return repository.save(credit);
    }


    public List<Credit> findClosedCreditsByPerson(Person person) {
        return repository.findCreditsByPersonAndCreditState(person, CreditState.CLOSED);
    }

    public List<Credit> findCreditsOnStreet(Street street) {
        return repository.findAll(new CreditByStreetSpecification(street));
    }

    public List<Credit> findActiveCreditsOnStreet(Street street) {
        return repository.findAll(new CreditByStreetActiveSpecification(street));
    }

    public List<Credit> findCreditsOnAddress(Address address) {
        return repository.findAll(new CreditByAddressSpecification(address));
    }

    public List<Credit> findActiveCreditsOnAddress(Address address) {
        return repository.findAll(new CreditByAddressActiveSpecification(address));
    }

    public List<Credit> findCreditsOnCity(City city) {
        return repository.findAll(new CreditByCitySpecification(city));
    }

    public List<Credit> findActiveCreditsOnCity(City city) {
        return repository.findAll(new CreditByCityActiveSpecification(city));
    }

    // by Person

    public BigDecimal findActiveFullSumByPerson(Person person) {
        return repository.findActiveFullSumByPerson(person);
    }

    public BigDecimal findActiveBasisByPerson(Person person) {
        return repository.findActiveBasisByPerson(person);
    }

    public BigDecimal findActiveInterestByPerson(Person person) {
        return repository.findActiveInterestByPerson(person);
    }

    public BigDecimal findDueSumByPerson(Person person) {
        return repository.findDueSumByPerson(person);
    }

    public BigDecimal findDueSumByPerson(Person person, LocalDateTime endDate) {
        return repository.findDueSumByPerson(person, endDate);
    }


    // By Address
    public BigDecimal findAddressActiveFullSumByAddress(Address address) {
        return repository.findAddressActiveFullSumByAddress(address.getCity(), address.getStreet(), address.getNumber().concat("%"));
    }

    public BigDecimal findDueSumByAddress(Address address) {
        return repository.findDueByAddress(address.getCity(), address.getStreet(), address.getNumber().concat("%"));
    }


    public BigDecimal findDueSumForThePeriod(Person person, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findDueSumForThePeriod(person, startDate, endDate);
    }


    public BigDecimal findActiveReturnedByPerson(Person person) {
        return repository.findActiveReturnedByPerson(person);
    }

    public BigDecimal findAddressActiveBasisSum(Address address) {
        return repository.findAddressActiveBasisSum(address.getCity(), address.getStreet(), address.getNumber());
    }

    public BigDecimal findAddressActiveReturnedSum(Address address) {
        return repository.findAddressActiveReturnedSum(address.getCity(), address.getStreet(), address.getNumber());
    }

    public BigDecimal findStreetActiveBasisSum(Address address) {
        return repository.findStreetActiveBasisSum(address.getCity(), address.getStreet());
    }

    public BigDecimal findStreetActiveReturnedSum(Address address) {
        return repository.findStreetActiveReturnedSum(address.getCity(), address.getStreet());
    }

    public BigDecimal findStreetDueSum(Address address) {
        return repository.findStreetDueSum(address.getCity(), address.getStreet());
    }

    public BigDecimal findGuarantorActiveBasisSum(Person address) {
        return repository.findGuarantorActiveBasisSum(address);
    }

    public BigDecimal findGuarantorActiveReturnedSum(Person selectedPerson) {
        return repository.findGuarantorActiveReturnedSum(selectedPerson);
    }

    public BigDecimal findGuarantorDueSum(Person selectedPerson) {
        return repository.findGuarantorDueSum(selectedPerson);
    }
}
