package com.crediline.mb;

import com.crediline.documents.DocumentCompiler;
import com.crediline.model.*;
import com.crediline.service.CreditService;
import com.crediline.service.DocumentTemplateService;
import com.crediline.service.OfficeService;
import com.crediline.utils.LocaleUtils;
import org.joda.time.LocalDateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dimer on 3/27/14.
 */
// TODO: This has been copy-pasted from InquiryCreditsMB. The source has changed dramatically since that. This class must be deleted, and started anew
@Component("inquiryOverdueCreditsMB")
@Scope("prototype")
public class InquiryOverdueCreditsMB extends CommonManagedBean implements ITabBean, ITemplatable {
    private static final long serialVersionUID = 331543323699875392L;

    @Inject
    private OfficeService officeService;

    @Inject
    private CreditService creditService;

    @Inject
    private DocumentTemplateService documentTemplateService;

    private Region selectedRegion;
    private Office selectedOffice;
    private City selectedCity;
    private Street selectedStreet;
    private String selectedNumber;
    private Province selectedProvince;
    private Credit selectedCredit;
    //the same field as the field above but this field is using from another component
    private Credit sCredit;
    private Person selectedPerson;
    private User issuer;
    private LocalDateTime issuedBefore;
    private LocalDateTime issuedAfter;
    private LocalDateTime inOverdueBefore;
    private LocalDateTime inOverdueAfter;
    private Boolean withHalfPayments;
    private List<Credit> result = new ArrayList<Credit>();
    private LocalDateTime overdueFromDate;
    private LocalDateTime overdueToDate;
    private List<Credit> filteredCredits;
    private CreditState creditState;
    private Document overdueCreditsDocument;
    private PersonAdvancedInfo personAdvancedInfo;
    private String personName;
    private String personMidname;
    private String personSurname;
    private String personEgn;
    private List<Office> regionalOffices;
    private String creditId;
    private String invalidationReason;

    @PostConstruct
    public void init() {
        regionalOffices = officeService.findAll();
        overdueCreditsDocument = new Document();
        personAdvancedInfo = new PersonAdvancedInfo(creditService, documentTemplateService);
    }

    public void invalidate() {
        sCredit.setCreditState(CreditState.INVALID);
        sCredit.setInvalidationReason(invalidationReason);
        creditService.save(sCredit);

        invalidationReason = null;
    }

    private SelectItem[] createFilterOptions() {
        SelectItem[] options = new SelectItem[5];
        options[0] = new SelectItem("", LocaleUtils.getLocaliziedMessage("common.choose"));
        options[1] = new SelectItem(CreditState.DECLINED, LocaleUtils.getLocaliziedMessage("common.realCreditState.".concat(CreditState.DECLINED.toString())));
        options[2] = new SelectItem(CreditState.IN_PROGRESS, LocaleUtils.getLocaliziedMessage("common.realCreditState.".concat(CreditState.IN_PROGRESS.toString())));
        options[3] = new SelectItem(CreditState.OVERDUE, LocaleUtils.getLocaliziedMessage("common.realCreditState.".concat(CreditState.OVERDUE.toString())));
        options[4] = new SelectItem(CreditState.CLOSED, LocaleUtils.getLocaliziedMessage("common.realCreditState.".concat(CreditState.CLOSED.toString())));
        options[5] = new SelectItem(CreditState.INVALID, LocaleUtils.getLocaliziedMessage("common.realCreditState.".concat(CreditState.INVALID.toString())));
        return options;
    }

    public void search() {
 /*       CriteriaBuilder builder = getCreditService().getCriteriaBuilder();
        CriteriaQuery<Credit> criteriaQuery = builder.createQuery(Credit.class);
        Root creditRoot = criteriaQuery.from(Credit.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        *//*predicates.add(builder.notEqual(creditRoot.get("creditState"), CreditState.INITIAL));*//*
        predicates.add(builder.equal(creditRoot.get("creditState"), CreditState.OVERDUE));
        criteriaQuery.orderBy(getCreditService().getCriteriaBuilder().desc(creditRoot.get("id")));

        if (selectedOffice != null && selectedOffice.getId() != null) {
            Predicate predicate = builder.equal(creditRoot.get("issuedIn"), selectedOffice);
            predicates.add(predicate);
        }

        if (getCurrentUser().getRole().equals(Role.ROLE_SUPERADMIN)) {
            // Superadmin can view credits for all regions
            if (selectedRegion != null && selectedRegion.getId() != null) {
                Predicate predicate = builder.equal(creditRoot.get("issuedIn").get("region"), selectedRegion);
                predicates.add(predicate);
            }
        } else {
            // Every other role can view only the credits from the region to which it belongs
            selectedRegion = getCurrentUser().getOffice().getRegion();
            Predicate predicate = builder.equal(creditRoot.get("issuedIn").get("region"), selectedRegion);
            predicates.add(predicate);
        }

        if (issuedAfter != null) {
            Predicate predicate = builder.greaterThanOrEqualTo(creditRoot.get("creationDate"), issuedAfter);
            predicates.add(predicate);
        }

        if (issuedBefore != null) {
            Predicate predicate = builder.lessThanOrEqualTo(creditRoot.get("creationDate"), issuedBefore);
            predicates.add(predicate);
        }

        if (inOverdueAfter != null) {
            Predicate predicate = builder.greaterThanOrEqualTo(creditRoot.get("dueDate"), inOverdueAfter);
            predicates.add(predicate);
        }

        if (inOverdueBefore != null) {
            Predicate predicate = builder.lessThanOrEqualTo(creditRoot.get("dueDate"), inOverdueBefore);
            predicates.add(predicate);
        }

        if (personName != null && personName.length() > 0) {
            ValidationUtils.validateSQLRequestParameter(personName);
            Join creditPersonJoin = creditRoot.join("person");
            Predicate predicate = builder.like(creditPersonJoin.get("name"), personName);
            predicates.add(predicate);
        }

        if (personMidname != null && personMidname.length() > 0) {
            ValidationUtils.validateSQLRequestParameter(personMidname);
            Join creditPersonJoin = creditRoot.join("person");
            Predicate predicate = builder.like(creditPersonJoin.get("midname"), personMidname);
            predicates.add(predicate);
        }

        if (personSurname != null && personSurname.length() > 0) {
            ValidationUtils.validateSQLRequestParameter(personSurname);
            Join creditPersonJoin = creditRoot.join("person");
            Predicate predicate = builder.like(creditPersonJoin.get("surname"), personSurname);
            predicates.add(predicate);
        }

        if (personEgn != null && personEgn.length() > 0) {
            ValidationUtils.validateSQLRequestParameter(personEgn);
            Join creditPersonJoin = creditRoot.join("person");
            Predicate predicate = builder.like(creditPersonJoin.get("egn"), personEgn);
            predicates.add(predicate);
        }

        if (selectedCity != null) {
            Join creditPersonJoin = creditRoot.join("person");
            Join personAddressJoin = creditPersonJoin.join("addresses");
            Join addressCityJoin = personAddressJoin.join("city");
            Predicate predicate = builder.equal(addressCityJoin, selectedCity);
            predicates.add(predicate);
        }

        if (selectedStreet != null) {
            Join creditPersonJoin = creditRoot.join("person");
            Join personAddressJoin = creditPersonJoin.join("addresses");
            Join addressStreetJoin = personAddressJoin.join("street");
            Predicate predicate = builder.equal(addressStreetJoin, selectedStreet);
            predicates.add(predicate);
        }

        if (selectedNumber != null && selectedNumber.length() > 0) {
            ValidationUtils.validateSQLRequestParameter(selectedNumber);
            Join creditPersonJoin = creditRoot.join("person");
            Join personAddressJoin = creditPersonJoin.join("addresses");
            Predicate predicate = builder.like(personAddressJoin.get("number"), selectedNumber);
            predicates.add(predicate);
        }

        if (issuer != null) {
            Predicate predicate = builder.equal(creditRoot.get("issuer"), issuer);
            predicates.add(predicate);
        }

        if (predicates.size() > 0) {
            Predicate fullPredicate = builder.and(predicates.toArray(new Predicate[predicates.size()]));
            criteriaQuery.where(fullPredicate);
        }

        criteriaQuery.distinct(true);
        result = getCreditService().findByCriteria(criteriaQuery);

        *//*Expression expression = builder.sum(creditRoot.get("sum"));
        criteriaQuery.select(expression);
        sum = getCreditService().findByCriteria(criteriaQuery);*/
    }

    public void printCredits() {
        if (creditState.equals(CreditState.OVERDUE)) {
            DocumentTemplate overdueDocumentTemplate = documentTemplateService.findByName("просрочени кредити");
            DocumentCompiler documentCompiler = new DocumentCompiler(this, overdueDocumentTemplate);
            documentCompiler.compile();
            this.setOverdueCreditsDocument(documentCompiler.getDocument());

        } else {
            DocumentTemplate overdueDocumentTemplate = documentTemplateService.findByName("всички кредити");
            DocumentCompiler documentCompiler = new DocumentCompiler(this, overdueDocumentTemplate);
            documentCompiler.compile();
            this.setOverdueCreditsDocument(documentCompiler.getDocument());
        }
    }

    @Override
    public Boolean getClosable() {

        return true;
    }

    public Region getSelectedRegion() {
        return selectedRegion;
    }

    public void setSelectedRegion(Region selectedRegion) {
        this.selectedRegion = selectedRegion;
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

    public Office getSelectedOffice() {
        return selectedOffice;
    }

    public void setSelectedOffice(Office selectedOffice) {
        this.selectedOffice = selectedOffice;
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

    public Boolean getWithHalfPayments() {
        return withHalfPayments;
    }

    public void setWithHalfPayments(Boolean withHalfPayments) {
        this.withHalfPayments = withHalfPayments;
    }

    public Province getSelectedProvince() {
        return selectedProvince;
    }

    public void setSelectedProvince(Province selectedProvince) {
        this.selectedProvince = selectedProvince;
    }

    public List<Credit> getResult() {
        return result;
    }

    public void setResult(List<Credit> result) {
        this.result = result;
    }

    public LocalDateTime getOverdueFromDate() {
        return overdueFromDate;
    }

    public void setOverdueFromDate(LocalDateTime overdueFromDate) {
        this.overdueFromDate = overdueFromDate;
    }

    public LocalDateTime getOverdueToDate() {
        return overdueToDate;
    }

    public void setOverdueToDate(LocalDateTime overdueToDate) {
        this.overdueToDate = overdueToDate;
    }

    public List<Credit> getFilteredCredits() {
        return filteredCredits;
    }

    public void setFilteredCredits(List<Credit> filteredCredits) {
        this.filteredCredits = filteredCredits;
    }

    public User getIssuer() {
        return issuer;
    }

    public void setIssuer(User issuer) {
        this.issuer = issuer;
    }

    public LocalDateTime getInOverdueBefore() {
        return inOverdueBefore;
    }

    public void setInOverdueBefore(LocalDateTime inOverdueBefore) {
        this.inOverdueBefore = inOverdueBefore;
    }

    public LocalDateTime getInOverdueAfter() {
        return inOverdueAfter;
    }

    public void setInOverdueAfter(LocalDateTime inOverdueAfter) {
        this.inOverdueAfter = inOverdueAfter;
    }

    public CreditState getCreditState() {
        return creditState;
    }

    public void setCreditState(CreditState creditState) {
        this.creditState = creditState;
    }

    public Document getOverdueCreditsDocument() {
        return overdueCreditsDocument;
    }

    public void setOverdueCreditsDocument(Document overdueCreditsDocument) {
        this.overdueCreditsDocument = overdueCreditsDocument;
    }



    @Override
    public Map<String, Object> getTemplateItems() {

        Map<String, Object> itemMap = new HashMap<String, Object>();
        itemMap.put("credits", getResult());
        if (inOverdueBefore != null) {
            itemMap.put("fromDate", inOverdueBefore.toString("dd/MM/yyyy"));
        } else {
            itemMap.put("fromDate", "");
        }

        if (inOverdueAfter != null) {
            itemMap.put("toDate", inOverdueBefore.toString("dd/MM/yyyy"));
        } else {
            itemMap.put("toDate", LocaleUtils.getLocaliziedMessage("common.today"));
        }

        return itemMap;
    }

    public Credit getSelectedCredit() {
        return selectedCredit;
    }

    public void setSelectedCredit(Credit selectedCredit) {
        this.selectedCredit = selectedCredit;
    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public List<Credit> getCreditsInGuarantor() {
        if (selectedPerson != null) {
            return creditService.findByGuarantor(selectedPerson);
        }
        return new ArrayList<>();
    }

    public PersonAdvancedInfo getPersonAdvancedInfo() {
        return personAdvancedInfo;
    }

    public void setPersonAdvancedInfo(PersonAdvancedInfo personAdvancedInfo) {
        this.personAdvancedInfo = personAdvancedInfo;
    }

    public String getPersonName() {
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
    }

    public String getPersonEgn() {
        return personEgn;
    }

    public void setPersonEgn(String personEgn) {
        this.personEgn = personEgn;
    }

    /*public Boolean getIsSuperAdmin() {
        return getCurrentUser().getRole().equals(Role.ROLE_SUPERADMIN);
    }*/

    public List<Office> getRegionalOffices() {
        if (!getCurrentUser().getRole().equals(Role.ROLE_SUPERADMIN)) {
            return officeService.findByRegion(getCurrentUser().getOffice().getRegion());
        }
        return regionalOffices;
    }

    public void setRegionalOffices(List<Office> regionalOffices) {
        this.regionalOffices = regionalOffices;
    }

    public String getSelectedNumber() {
        return selectedNumber;
    }

    public void setSelectedNumber(String selectedNumber) {
        this.selectedNumber = selectedNumber;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getInvalidationReason() {
        return invalidationReason;
    }

    public void setInvalidationReason(String invalidationReason) {
        this.invalidationReason = invalidationReason;
    }

    public Credit getsCredit() {
        return sCredit;
    }

    public void setsCredit(Credit sCredit) {
        this.sCredit = sCredit;
    }
}
