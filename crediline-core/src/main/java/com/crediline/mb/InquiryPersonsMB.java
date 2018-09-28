package com.crediline.mb;

import com.crediline.dao.common.Specifications;
import com.crediline.model.*;
import com.crediline.model.specifications.*;
import com.crediline.service.CreditService;
import com.crediline.service.DocumentTemplateService;
import com.crediline.service.PersonService;
import com.crediline.utils.CalculatorUtil;
import org.joda.time.LocalDateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dimer on 3/27/14.
 */

@Component("inquiryPersons")
@Scope("prototype")
public class InquiryPersonsMB extends CommonManagedBean implements ITabBean, ITemplatable {
    private static final long serialVersionUID = 331543323699875392L;

    @Inject
    private PersonService personService;

    @Inject
    private CreditService creditService;

    @Inject
    private DocumentTemplateService documentTemplateService;

    @Inject
    private PersonMB updatePersonBean;

    /*@Inject*/
    private CalculatorUtil calculatorUtil;

    private City selectedCity;
    private Credit selectedCredit;
    private Street selectedStreet;
    private Province selectedProvince;
    private Person selectedPerson;
    private Person sPerson;
    private LocalDateTime issuedBefore;
    private LocalDateTime issuedAfter;
    private List<Person> result = new ArrayList<Person>();
    private List<Person> filteredPersons;
    private String rangeSelector;
    private Person person;
    private PersonAdvancedInfo personAdvancedInfo;
    private String selectedNumber;
    private String comment;

    @PostConstruct
    public void init() {
        person = new Person();
        selectedPerson = new Person();
        personAdvancedInfo = new PersonAdvancedInfo(creditService, documentTemplateService);
    }

    public void addComment() {
        Comment newComment = new Comment();
        newComment.setCreatedBy(getCurrentUser());
        newComment.setCreationDate(new LocalDateTime());
        newComment.setValue(comment);

        sPerson.getComments().add(newComment);
        personService.save(sPerson);
    }

    public CalculatorUtil getCalculatorUtil() {
        return calculatorUtil;
    }

    public void setCalculatorUtil(CalculatorUtil calculatorUtil) {
        this.calculatorUtil = calculatorUtil;
    }

    public static class PersonFetchSpecification implements Specification<Person> {
        @Override
        public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            Fetch<Person, Address> addressFetch = root.fetch(Person_.addresses);
            addressFetch.fetch(Address_.street);
            addressFetch.fetch(Address_.city);
            return null;
        }
    }

    public void search() {
        result = personService.findAll(Specifications.and(
                new PersonFetchSpecification(),
                new PersonAddressSpecification(
                        new AddressCitySpecification(selectedCity),
                        new AddressStreetSpecification(selectedStreet),
                        new AddressNumberSpecification(selectedNumber)),
                new CreationDateSpecification<Person>(issuedBefore, issuedAfter),
                person == null ? null : new PersonNameSpecification(person.getName(), person.getMidname(), person.getSurname())));
    }

    @Override
    public Boolean getClosable() {
        return true;
    }

    public LocalDateTime getIssuedBefore() {
        return issuedBefore;
    }

    public void setIssuedBefore(LocalDateTime issuedBefore) {
        this.issuedBefore = issuedBefore;
    }

    public LocalDateTime getIssuedAfter() {
        return issuedAfter;
    }

    public void setIssuedAfter(LocalDateTime issuedAfter) {
        this.issuedAfter = issuedAfter;
    }


    public City getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }

    public Street getSelectedStreet() {
        return selectedStreet;
    }

    public void setSelectedStreet(Street selectedStreet) {
        this.selectedStreet = selectedStreet;
    }


    public Province getSelectedProvince() {
        return selectedProvince;
    }

    public void setSelectedProvince(Province selectedProvince) {
        this.selectedProvince = selectedProvince;
    }

    public List<Person> getResult() {
        return result;
    }

    public void setResult(List<Person> result) {
        this.result = result;
    }


    public List<Person> getFilteredPersons() {
        return filteredPersons;
    }

    public void setFilteredPersons(List<Person> filteredPersons) {
        this.filteredPersons = filteredPersons;
    }




    @Override
    public Map<String, Object> getTemplateItems() {
        Map<String, Object> itemMap = new HashMap<String, Object>();
        itemMap.put("persons", getResult());
        return itemMap;
    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
        updatePersonBean.setPerson(selectedPerson);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getRangeSelector() {
        return rangeSelector;
    }

    public void setRangeSelector(String rangeSelector) {
        this.rangeSelector = rangeSelector;
    }

    public Credit getSelectedCredit() {
        if (selectedCredit != null) {
            getCalculatorUtil().sync(selectedCredit);
        }
        return selectedCredit;
    }

    public void setSelectedCredit(Credit selectedCredit) {
        this.selectedCredit = selectedCredit;
    }

    public PersonMB getUpdatePersonBean() {
        return updatePersonBean;
    }

    public void setUpdatePersonBean(PersonMB updatePersonBean) {
        this.updatePersonBean = updatePersonBean;
    }

    public PersonAdvancedInfo getPersonAdvancedInfo() {
        return personAdvancedInfo;
    }

    public void setPersonAdvancedInfo(PersonAdvancedInfo personAdvancedInfo) {
        this.personAdvancedInfo = personAdvancedInfo;
    }

    public String getSelectedNumber() {
        return selectedNumber;
    }

    public void setSelectedNumber(String selectedNumber) {
        this.selectedNumber = selectedNumber;
    }


    public Person getsPerson() {
        return sPerson;
    }


    public void setsPerson(Person sPerson) {
        this.sPerson = sPerson;
    }


    public String getComment() {
        return comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }
}
