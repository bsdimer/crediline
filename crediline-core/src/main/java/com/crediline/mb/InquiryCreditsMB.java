package com.crediline.mb;

import com.crediline.dao.common.PotentialJoin;
import com.crediline.dao.common.Potentials;
import com.crediline.dao.common.Specifications;
import com.crediline.documents.DocumentCompiler;
import com.crediline.files.print.PDFFileCreator;
import com.crediline.model.*;
import com.crediline.model.specifications.*;
import com.crediline.service.CreditService;
import com.crediline.service.DocumentTemplateService;
import com.crediline.service.OfficeService;
import com.crediline.utils.CalculatorUtil;
import com.crediline.utils.LocaleUtils;
import com.google.common.collect.ArrayListMultimap;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDateTime;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by dimer on 3/27/14.
 */

@Component("inquiryCredits")
@Scope("prototype")
public class InquiryCreditsMB extends CommonManagedBean implements ITabBean, ITemplatable {
    private static final long serialVersionUID = 331543323699875392L;

    @Inject
    private OfficeService officeService;

    @Inject
    private CreditService creditService;

    @Inject
    private DocumentTemplateService documentTemplateService;

    @Inject
    private CalculatorUtil calculatorUtil;

    // Filter fields
    private Office selectedOffice;
    private Region selectedRegion;
    private LocalDateTime issuedAfter;
    private LocalDateTime issuedBefore;
    private LocalDateTime inOverdueAfter;
    private LocalDateTime inOverdueBefore;
    private String creditId;
    private String personName;
    private String personMidname;
    private String personSurname;
    private String personEgn;
    private City selectedCity;
    private Street selectedStreet;
    private String selectedNumber;
    private User issuer;
    private CreditState creditState;
    private Province selectedProvince;
    private Credit selectedCredit;

    //the same field as the field above but this field is using from another component
    private Credit sCredit;
    private Person selectedPerson;
    private Boolean withHalfPayments;
    private List<Credit> result = new ArrayList<Credit>();
    private LocalDateTime overdueFromDate;
    private LocalDateTime overdueToDate;
    private List<Credit> filteredCredits;
    private Document overdueCreditsDocument;
    private PersonAdvancedInfo personAdvancedInfo;
    private List<Office> regionalOffices;
    private String invalidationReason;

    public CalculatorUtil getCalculatorUtil() {
        return calculatorUtil;
    }

    public void setCalculatorUtil(CalculatorUtil calculatorUtil) {
        this.calculatorUtil = calculatorUtil;
    }


    public class CreditSearchSpecification implements Specification<Credit> {
        @Override
        public Predicate toPredicate(Root<Credit> creditRoot, CriteriaQuery<?> query, CriteriaBuilder builder) {
            List<Predicate> predicates = new ArrayList<>();
            PotentialJoin<Credit, Person> creditPersonJoin = Potentials.join(creditRoot, Credit_.person);
            predicates.add(new PersonNameSpecification(personName, personMidname, personSurname).toPredicate(creditPersonJoin, query, builder));
            predicates.add(new PersonAddressSpecification(
                    new AddressCitySpecification(selectedCity),
                    new AddressStreetSpecification(selectedStreet),
                    new AddressNumberSpecification(selectedNumber)
            ).toPredicate(creditPersonJoin, query, builder));

            if (!StringUtils.isEmpty(personEgn)) {
                predicates.add(builder.like(creditPersonJoin.get().get(Person_.egn), personEgn + "%"));
            }

           /* if (issuer != null) {
                predicates.add(builder.equal(creditRoot.get(Credit_.issuer), issuer));
            }*/

            if (creditId != null && creditId.length() > 0) {
                predicates.add(builder.equal(creditRoot.get(Credit_.id), Long.parseLong(creditId)));
            }

            return Specifications.and(predicates, builder);
        }
    }

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
        result = creditService.findAll(Specifications.and(
                new CreditStateSpecification(creditState),
                new CreditIssuedInSpecification(selectedOffice, selectedRegion, getCurrentUser()),
                new CreationDateSpecification<Credit>(issuedAfter, issuedBefore),
                new CreditDueSpecification(inOverdueAfter, inOverdueBefore),
                new CreditSearchSpecification()));
        /*Expression expression = builder.sum(creditRoot.get("sum"));
        criteriaQuery.select(expression);
        sum = getCreditService().findByCriteria(criteriaQuery);*/
    }

    public void printCredits() {
        if (creditState.equals(CreditState.OVERDUE)) {

        } else {
            DocumentTemplate overdueDocumentTemplate = documentTemplateService.findOne(16L);
            DocumentCompiler documentCompiler = new DocumentCompiler(this, overdueDocumentTemplate);
            documentCompiler.compile();
            this.setOverdueCreditsDocument(documentCompiler.getDocument());
        }
    }

    public StreamedContent getPrintInPDF() {
        try {
            DocumentTemplate documentTemplate = documentTemplateService.findByDocumentTemplatePurpose(DocumentTemplatePurpose.ST_CREDITS_LIST_OVERDUE);
            final ArrayListMultimap<Person, Credit> sortedResult = ArrayListMultimap.create();
            for (Credit credit : result) {
                sortedResult.put(credit.getPerson(), credit);
            }

            Document document = DocumentCompiler.getCompiler().compile(new HashMap<String, Object>() {
                {
                    put("persons", sortedResult.asMap());
                }
            }, documentTemplate).getDocument();
            PDFFileCreator pdfFileCreator = new PDFFileCreator(document.getSource());
            File file = pdfFileCreator.createPDFFile();
            FileInputStream fileInputStream = null;

            fileInputStream = new FileInputStream(file.getAbsolutePath());
            return new DefaultStreamedContent(fileInputStream, "application/pdf", file.getName());
        } catch (FileNotFoundException | RuntimeException e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error in converting in PDF"));
        }
        return null;
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
        if (selectedCredit != null) {
            getCalculatorUtil().sync(selectedCredit);
        }
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
