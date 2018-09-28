package com.crediline.mb;

import com.crediline.model.City;
import com.crediline.model.Credit;
import com.crediline.model.Person;
import com.crediline.model.Street;
import com.crediline.model.specifications.AddressCitySpecification;
import com.crediline.model.specifications.AddressNumberSpecification;
import com.crediline.model.specifications.AddressStreetSpecification;
import com.crediline.model.specifications.PersonAddressSpecification;
import com.crediline.service.CreditService;
import com.crediline.service.PersonService;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dimer on 3/27/14.
 */

@Component("inquiryFuturePayments")
@Scope("request")
public class InquiryFuturePaymentsMB extends CommonManagedBean implements ITabBean, ITemplatable {
    private static final long serialVersionUID = 9215433287498751212L;

    @Inject
    private PersonService personService;

    @Inject
    private CreditService creditService;

    private LocalDateTime endDate;
    private LocalDateTime startDate;
    private Person person;
    private City city;
    private Street street;
    private String number;
    private String apartment;
    private String quarter;
    private String entrance;
    private String floor;
    private List<Person> result = new ArrayList<>();
    private BigDecimal sumForThePeriod;
    private BigDecimal fullSumForThePeriod;
    private BigDecimal dueSumUntilNow;

    @PostConstruct
    public void init() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        startDate = LocalDateTime.parse(LocalDateTime.now().toString("dd/MM/yyyy 00:00:00"), formatter);
        endDate = LocalDateTime.now().plusDays(1);
    }


    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public List<Person> getResult() {
        return result;
    }

    public void setResult(List<Person> result) {
        this.result = result;
    }

    public BigDecimal getSumForThePeriod() {
        sumForThePeriod = BigDecimal.valueOf(0d);
        for (Person person : result) {
            // sumForThePeriod = sumForThePeriod.add(person.getDueSumForPeriod(startDate, endDate));
            sumForThePeriod = creditService.findDueSumForThePeriod(person, startDate, endDate);
        }
        return sumForThePeriod;
    }

    public void setSumForThePeriod(BigDecimal sumForThePeriod) {
        this.sumForThePeriod = sumForThePeriod;
    }

    /*public BigDecimal getFullSumForThePeriod() {
        fullSumForThePeriod = BigDecimal.valueOf(0d);
        for (Person person : result) {
            fullSumForThePeriod = fullSumForThePeriod.add(person.getDueSum());
            sumForThePeriod = creditService.findDueSumForThePeriod(person, startDate, endDate);
        }
        return fullSumForThePeriod;
    }

    public void setFullSumForThePeriod(BigDecimal fullSumForThePeriod) {
        this.fullSumForThePeriod = fullSumForThePeriod;
    }*/

    public void setDueSumUntilNow(BigDecimal dueSumUntilNow) {
        this.dueSumUntilNow = dueSumUntilNow;
    }

    public BigDecimal getDueSumUntilNow() {
        dueSumUntilNow = BigDecimal.valueOf(0d);
        for (Person person : result) {
            dueSumUntilNow = dueSumUntilNow.add(creditService.findDueSumByPerson(person, LocalDateTime.now()));
        }
        return dueSumUntilNow;
    }

    @Override
    public Boolean getClosable() {
        return true;
    }


    @Override
    public Map<String, Object> getTemplateItems() {
        return null;
    }

    public void searchOnStreet() {
        result = personService.findAll(new PersonAddressSpecification(
                new AddressCitySpecification(city),
                new AddressStreetSpecification(street)),
                new Sort("id"));
    }

    public void searchOnStreet(Person person) {
        if (person != null) {
            city = person.getAddresses().get(0).getCity();
            street = person.getAddresses().get(0).getStreet();
        }
        searchOnStreet();
    }

    public void searchOnStreetNo() {
        result = personService.findAll(new PersonAddressSpecification(
                new AddressCitySpecification(city),
                new AddressStreetSpecification(street),
                new AddressNumberSpecification(number)),
                new Sort("id"));
    }

    public void searchOnStreetNo(Person person) {
        if (person != null) {
            city = person.getAddresses().get(0).getCity();
            street = person.getAddresses().get(0).getStreet();
            number = person.getAddresses().get(0).getNumber();
        }
        searchOnStreetNo();

    }

    public void searchOnCity() {
        result = personService.findAll(new PersonAddressSpecification(
                new AddressCitySpecification(city)),
                new Sort("id"));
    }

    public void searchOnCity(Person person) {
        if (person != null) {
            city = person.getAddresses().get(0).getCity();
        }
        searchOnCity();
    }

    public BigDecimal dueSumForPeriod(Person person) {
        return creditService.findDueSumForThePeriod(person, startDate, endDate);
    }

    public List<Credit> getCreditsInGuarantor() {
        if (person != null) {
            return creditService.findByGuarantor(person);
        }
        return new ArrayList<>();
    }

    /*public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonMidname() {
        return personMidname;
    }

    public void setPersonMidname(String personMidname) {
        this.personMidname = personMidname;
    }

    public String getPersonSurname() {
        return personSurname;
    }

    public void setPersonSurname(String personSurname) {
        this.personSurname = personSurname;
    }*/
}
