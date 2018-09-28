package com.crediline.mb;

import com.crediline.dao.common.AbstractSpecification;
import com.crediline.dao.common.PotentialJoin;
import com.crediline.dao.common.Potentials;
import com.crediline.dao.common.Specifications;
import com.crediline.documents.DocumentCompiler;
import com.crediline.model.*;
import com.crediline.model.specifications.*;
import com.crediline.service.CreditService;
import com.crediline.service.DocumentTemplateService;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dimer on 6/12/14.
 */
public class PersonAdvancedInfo implements ITemplatable {

    private CreditService creditService;
    private DocumentTemplateService documentTemplateService;

    private LocalDateTime endDate;
    private LocalDateTime startDate;
    private String additionalSearchResult;
    private Person person;
    private City personCity;
    private Street personStreet;
    private String personNumber;
    private String personApartment;
    private String personQuarter;
    private String personEntrance;
    private String personFloor;
    private List<Credit> result = new ArrayList<>();
    private BigDecimal sumForThePeriod;
    private BigDecimal fullSumForThePeriod;


    private BigDecimal dueSumUntilNow;


    public PersonAdvancedInfo(CreditService creditService, DocumentTemplateService documentTemplateService) {
        this.creditService = creditService;
        this.documentTemplateService = documentTemplateService;
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        endDate = LocalDateTime.now().plusDays(1);
        startDate = LocalDateTime.parse(LocalDateTime.now().toString("dd/MM/yyyy 00:00:00"), formatter);
    }

    public class SearchSpecification extends CreditDueSpecification {
        private AbstractSpecification<Address>[] components;

        @SafeVarargs
        protected SearchSpecification(AbstractSpecification<Address>... components) {
            super(startDate, endDate);
            this.components = components;
        }

        @Override
        public Predicate toPredicate(Root<Credit> creditRoot, CriteriaQuery<?> query, CriteriaBuilder builder) {
            List<Predicate> predicates = new ArrayList<Predicate>();
            predicates.add(super.toPredicate(creditRoot, query, builder));
            predicates.add(creditRoot.get(Credit_.creditState).in(CreditState.IN_PROGRESS, CreditState.OVERDUE));

            PotentialJoin<Credit, Person> creditPersonJoin = Potentials.join(creditRoot, Credit_.person);
            PotentialJoin<Person, Address> personAddressJoin = Potentials.join(creditPersonJoin, Person_.addresses);
            for (AbstractSpecification<Address> component : components) {
                predicates.add(component.toPredicate(personAddressJoin, query, builder));
            }
            return Specifications.and(predicates, builder);
        }
    }

    public List<Credit> search(Person person, String additionalSearchResult, Specification<Credit> searchSpecification) {
        this.additionalSearchResult = additionalSearchResult;

        result = creditService.findAll(searchSpecification, new Sort("id"));

        personCity = person.getAddresses().get(0).getCity();
        personStreet = person.getAddresses().get(0).getStreet();
        personNumber = person.getAddresses().get(0).getNumber();
        personApartment = person.getAddresses().get(0).getApartment();
        personQuarter = person.getAddresses().get(0).getQuarter();
        personEntrance = person.getAddresses().get(0).getEntrance();
        personFloor = person.getAddresses().get(0).getFloor();

        sumForThePeriod = BigDecimal.valueOf(0d);
        fullSumForThePeriod = BigDecimal.valueOf(0d);
        for (Credit credit : result) {
            sumForThePeriod = sumForThePeriod.add(credit.getDueSumForPeriod(startDate, endDate));
            fullSumForThePeriod = fullSumForThePeriod.add(credit.getOustandingSum());
            dueSumUntilNow = dueSumUntilNow.add(credit.getDueSumUntilNow());
        }

        if (result.size() > 0) {
            this.additionalSearchResult = getResultDocument();
        } else {
            this.additionalSearchResult = "None";
        }
        return result;
    }

    public List<Credit> searchOnStreet(Person person) {
        return search(person, "searchOnStreet", new SearchSpecification(
                new AddressCitySpecification(personCity),
                new AddressStreetSpecification(personStreet)));
    }

    public List<Credit> searchOnNumber(Person person) {
        return search(person, "searchOnNumber", new SearchSpecification(
                new AddressCitySpecification(personCity),
                new AddressStreetSpecification(personStreet),
                new AddressNumberSpecification(personNumber)));
    }

    public List<Credit> searchOnAddress(Person person) {
        return search(person, "searchOnAddress", new SearchSpecification(
                new AddressCitySpecification(personCity),
                new AddressStreetSpecification(personStreet),
                new AddressNumberSpecification(personNumber),
                new AddressDetailsSpecification(personQuarter, personApartment, personFloor)));
    }

    public List<Credit> searchOnCity(Person person) {
        return search(person, "searchOnCity", new SearchSpecification(
                new AddressCitySpecification(personCity)));
    }

    public List<Credit> getCreditsInGuarantor() {
        if (person != null) {
            return creditService.findByGuarantor(person);
        }
        return new ArrayList<>();
    }


    public String getAdditionalSearchResult() {
        return additionalSearchResult;
    }

    public void setAdditionalSearchResult(String additionalSearchResult) {
        this.additionalSearchResult = additionalSearchResult;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }


    @Override
    public Map<String, Object> getTemplateItems() {
        Map<String, Object> items = new HashMap<String, Object>();
        items.put("startDate", startDate);
        items.put("endDate", endDate);
        items.put("credits", result);
        items.put("sumForPeriod", sumForThePeriod);
        items.put("fullSumForThePeriod", fullSumForThePeriod);
        return items;
    }

    public String getResultDocument() {
        DocumentCompiler documentCompiler = new DocumentCompiler(this, documentTemplateService.findByName("вноски по адрес"));
        documentCompiler.compile();
        return documentCompiler.toString();
    }

    public void setSumForThePeriod(BigDecimal sumForThePeriod) {
        this.sumForThePeriod = sumForThePeriod;
    }

    public BigDecimal getDueSumUntilNow() {
        return dueSumUntilNow;
    }
}


