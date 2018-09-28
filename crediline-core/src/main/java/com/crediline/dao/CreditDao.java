package com.crediline.dao;

import com.crediline.model.*;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by dimer on 1/14/14.
 */

@Repository("creditDao")
public interface CreditDao extends CredilineRepository<Credit> {

    public static final String BASE_DEEP_QUERY = "select DISTINCT c from Credit c left join fetch c.payments p ";
    public static final String CREDIT_APPROVED_PREDICATE = "and c.creditState != 'INITIAL' and c.creditState != 'DECLINED' and c.creditState != 'INVALID'";
    public static final String ACTIVE_CREDIT_PREDICATE = " (c.creditState = 'IN_PROGRESS' or c.creditState = 'OVERDUE') ";

    List<Credit> findByPerson(Person person);

    @Query(BASE_DEEP_QUERY + "where c.person = :person")
    List<Credit> findByPersonDeep(Person person);

    @Query(BASE_DEEP_QUERY +
            "left join fetch c.person pr " +
            "where c.creationDate >= :fromDate and c.creationDate < :toDate " +
            CREDIT_APPROVED_PREDICATE)
    List<Credit> findApprovedCreditsInRangeDeep(LocalDateTime fromDate,
                                                LocalDateTime toDate);

    @Query(BASE_DEEP_QUERY +
            "left join fetch c.person pr " +
            "where c.creationDate < :toDate " +
            "and c.isRegistered <> true " +
            CREDIT_APPROVED_PREDICATE)
    List<Credit> findInProgressNotRegisteredCreditsToDate(LocalDateTime toDate);

    @Query("select DISTINCT c from Credit c " +
            "where c.creationDate >= :day and c.creationDate < :day " +
            CREDIT_APPROVED_PREDICATE)
    List<Credit> findApprovedCreditsByDay(LocalDateTime day);

    @Query("select count(c) from Credit c " +
            "where c.creationDate >= :day and c.creationDate < :day " +
            CREDIT_APPROVED_PREDICATE)
    BigInteger findApprovedCreditsCountByDay(LocalDateTime day);

    @Query(BASE_DEEP_QUERY + "where c.person = :person " + "and c.creditState = 'IN_PROGRESS'")
    Credit findInProgressCreditByPersonDeep(Person person);

    @Query("select c from Credit c where c.person = :person and c.creditState = 'IN_PROGRESS'")
    List<Credit> findInProgressCreditByPerson(@Param("person") Person person);

    @Query("select c from Credit c where c.person = :person and " + ACTIVE_CREDIT_PREDICATE)
    List<Credit> findActiveCreditByPerson(@Param("person") Person person);

    @Modifying
    @Query("update Credit c set c.isRegistered = :isRegistered where c.id = :id")
    void updateIsRegistered(Long id, Boolean isRegistered);

    @Query("select c from Credit c where c.guarantor1 = :person or c.guarantor2 = :person ")
    List<Credit> findByGuarantor(@Param("person") Person person);

    @Query("select c from Credit c where c.guarantor1 = :person or c.guarantor2 = :person and " + ACTIVE_CREDIT_PREDICATE)
    List<Credit> findActiveByGuarantor(@Param("person") Person person);

    List<Credit> findByGuarantor1(Person person);

    List<Credit> findByGuarantor2(Person person);

    @Query("select c from Credit c where c.person = :person and c.creditState = :state")
    List<Credit> findCreditsByPersonAndCreditState(@Param("person") Person person, @Param("state") CreditState state);

    @Query("select SUM(c.fullSum) from Credit c where c.person = :person and " + ACTIVE_CREDIT_PREDICATE)
    BigDecimal findActiveFullSumByPerson(@Param("person") Person person);

    @Query("select SUM(c.oustandingSum) from Credit c where c.person = :person and " + ACTIVE_CREDIT_PREDICATE)
    BigDecimal findDueSumByPerson(@Param("person") Person person);

    @Query("select SUM(c.basis) from Credit c where c.person = :person and " + ACTIVE_CREDIT_PREDICATE)
    BigDecimal findActiveBasisByPerson(@Param("person") Person person);

    @Query("select SUM(c.interest) from Credit c where c.person = :person and " + ACTIVE_CREDIT_PREDICATE)
    BigDecimal findActiveInterestByPerson(@Param("person") Person person);

    @Query("select SUM(c.returnedSum) from Credit c where c.person = :person and " + ACTIVE_CREDIT_PREDICATE)
    BigDecimal findActiveReturnedByPerson(@Param("person") Person person);

    @Query("select SUM(c.fullSum) from Credit c " +
            "join c.person p " +
            "join p.addresses a " +
            "join a.city ct " +
            "join a.street str " +
            "where a.city = :city and a.street = :street and a.number like :number and " + ACTIVE_CREDIT_PREDICATE)
    BigDecimal findAddressActiveFullSumByAddress(@Param("city") City city, @Param("street") Street street, @Param("number") String number);

    @Query("select SUM(c.oustandingSum) from Credit c " +
            "join c.person p " +
            "join p.addresses a " +
            "join a.city ct " +
            "join a.street str " +
            "where a.city = :city and a.street = :street and a.number like :number and " + ACTIVE_CREDIT_PREDICATE)
    BigDecimal findDueByAddress(@Param("city") City city, @Param("street") Street street, @Param("number") String number);

    @Query("select SUM(c.basis) from Credit c " +
            "join c.person p " +
            "join p.addresses a " +
            "join a.city ct " +
            "join a.street str " +
            "where a.city = :city and a.street = :street and a.number like :number and " + ACTIVE_CREDIT_PREDICATE)
    BigDecimal findAddressActiveBasisSum(@Param("city") City city, @Param("street") Street street, @Param("number") String number);

    @Query("select SUM(c.returnedSum) from Credit c " +
            "join c.person p " +
            "join p.addresses a " +
            "join a.city ct " +
            "join a.street str " +
            "where a.city = :city and a.street = :street and a.number like :number and " + ACTIVE_CREDIT_PREDICATE)
    BigDecimal findAddressActiveReturnedSum(@Param("city") City city, @Param("street") Street street, @Param("number") String number);

    @Query("select SUM(p.outstandingSum) from Payment p" +
            " join p.credit c " +
            " where c.person = :person and p.dueDate > :startDate and p.dueDate < :endDate")
    BigDecimal findDueSumForThePeriod(@Param("person") Person person, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("select SUM(p.outstandingSum) from Payment p" +
            " join p.credit c " +
            " where c.person = :person and p.dueDate < :endDate")
    BigDecimal findDueSumByPerson(@Param("person") Person person, @Param("endDate") LocalDateTime endDate);

    @Query("select SUM(c.basis) from Credit c " +
            "join c.person p " +
            "join p.addresses a " +
            "join a.city ct " +
            "join a.street str " +
            "where a.city = :city and a.street = :street and " + ACTIVE_CREDIT_PREDICATE)
    BigDecimal findStreetActiveBasisSum(@Param("city") City city, @Param("street") Street street);

    @Query("select SUM(c.returnedSum) from Credit c " +
            "join c.person p " +
            "join p.addresses a " +
            "join a.city ct " +
            "join a.street str " +
            "where a.city = :city and a.street = :street and " + ACTIVE_CREDIT_PREDICATE)
    BigDecimal findStreetActiveReturnedSum(@Param("city") City city, @Param("street") Street street);

    @Query("select SUM(c.oustandingSum) from Credit c " +
            "join c.person p " +
            "join p.addresses a " +
            "join a.city ct " +
            "join a.street str " +
            "where a.city = :city and a.street = :street and " + ACTIVE_CREDIT_PREDICATE)
    BigDecimal findStreetDueSum(@Param("city") City city, @Param("street") Street street);

    @Query("select SUM(c.basis) from Credit c " +
            "join c.person p " +
            "where ( c.guarantor1 = :person or c.guarantor2 = :person ) and " + ACTIVE_CREDIT_PREDICATE)
    BigDecimal findGuarantorActiveBasisSum(Person person);

    @Query("select SUM(c.returnedSum) from Credit c " +
            "join c.person p " +
            "where ( c.guarantor1 = :person or c.guarantor2 = :person ) and " + ACTIVE_CREDIT_PREDICATE)
    BigDecimal findGuarantorActiveReturnedSum(Person person);

    @Query("select SUM(c.oustandingSum) from Credit c " +
            "join c.person p " +
            "where ( c.guarantor1 = :person or c.guarantor2 = :person ) and " + ACTIVE_CREDIT_PREDICATE)
    BigDecimal findGuarantorDueSum(Person person);
}