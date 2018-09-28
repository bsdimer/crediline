package com.crediline.mb;

import com.crediline.documents.CreditDocumentHelper;
import com.crediline.documents.DocumentFactory;
import com.crediline.exceptions.ApplicationSpecifficExeption;
import com.crediline.files.Printer;
import com.crediline.files.print.PDFFileCreator;
import com.crediline.model.*;
import com.crediline.service.CreditService;
import com.crediline.service.DocumentTemplateService;
import com.crediline.utils.CalculatorUtil;
import com.crediline.utils.LocaleUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.logging.Logger;

import static com.crediline.utils.LocaleUtils.getLocaliziedMessage;

/**
 * Created by dimer on 1/17/14.
 */

/**
 * Must be crated in custom scope which is alive along with wizard creation and destruction
 */
@Component("creditWizard")
@Scope("prototype")
public class CreditWizardMB extends CommonManagedBean implements Serializable, ITabBean, ITemplatable {
    private static final long serialVersionUID = 7254809287787652455L;

    private boolean skip;

    private static Logger logger = Logger.getLogger(CreditWizardMB.class.getName());

    @Inject
    private ApplicationScopedMB applicationScopedMB;

    @Inject
    private DocumentTemplateService documentTemplateService;

    @Inject
    private CreditService creditService;

    @Inject
    private CalculatorUtil calculatorUtil;

    @Inject
    private ApplicationScopedMB applicationScoped;

    private int documentsTabActiveIndex = 0;
    private Credit credit;
    private BigDecimal applicationFee = new BigDecimal(0d);
    private LocalDateTime startDueDate;
    private List<Payment> payments;
    private Document selectedDocument;

    private Map<DocumentTemplatePurpose, DocumentWrapper> documents = new HashMap<>();

    private Person selectedPerson;
    private Credit selectedCredit;
    private PersonAdvancedInfo personAdvancedInfo;

    private boolean staticFee;
    private Currency defaultCurrency = Currency.getInstance("BGN");
    private Currency creditCurrency = defaultCurrency;
    private int currentLevel = 1;
    private Document agreementDocument;
    private Document contractDocument1;
    private Document contractDocument2;
    private Document contractAppendix1Document;
    private Document contractGuarantor1Document;
    private Document contractGuarantor2Document;
    private Document outcomeOrderDocument;
    private Document insuranceNoteDocument;
    private Document contractAppendix2Document;

    @Inject
    private DocumentFactory documentFacotry;
    private CreditDocumentHelper creditDocumentHelper;

    public CreditWizardMB() {
        logger.info("Creation of CreditWizardMB bean");
    }

    public BigDecimal getInterest() {
        return credit.getOriginalRate().multiply(BigDecimal.valueOf(100d));
    }

    public void setInterest(BigDecimal value) {
        credit.setOriginalRate(value.divide(BigDecimal.valueOf(100d)));
    }

    public String getCreditSum() {
        if (credit.getOriginalBasis() != null) {
            return credit.getOriginalBasis().toString();
        }
        return "";
    }

    public void setCreditSum(String creditSum) {
        credit.setOriginalBasis(BigDecimal.valueOf(Double.parseDouble(creditSum)));
    }

    public Integer getCreditPeriod() {
        return credit.getPeriod();
    }

    public void setCreditPeriod(Integer creditPeriods) {
        credit.setPeriod(creditPeriods);
    }


    @PostConstruct
    public void init() {
        credit = new Credit();
        credit.setBasis(getApplicationScopedMB().getMinimalCreditSum());
        credit.setPeriod(getApplicationScopedMB().getMinimalCreditPeriod());
        credit.setInterest(getApplicationScopedMB().getDefaultIntereset());
        credit.setOriginalRate(getApplicationScopedMB().getDefaultIntereset());
        //credit.setOriginalBasis(getApplicationScopedMB().getMinimalCreditSum());

        credit.setPickUpDate(LocalDateTime.now());
        selectedCredit = credit;
        selectedDocument = new Document();
        startDueDate = LocalDateTime.now();
        applicationFee = BigDecimal.valueOf(0d);
        payments = new ArrayList<>();
        staticFee = false;
        personAdvancedInfo = new PersonAdvancedInfo(creditService, documentTemplateService);

        agreementDocument = new Document();
        contractDocument1 = new Document();
        contractDocument2 = new Document();
        contractAppendix1Document = new Document();
        contractGuarantor1Document = new Document();
        contractGuarantor2Document = new Document();
        outcomeOrderDocument = new Document();
        insuranceNoteDocument = new Document();
        contractAppendix2Document = new Document();

        creditDocumentHelper = new CreditDocumentHelper(credit);
    }

    @Transactional
    public void save() throws ApplicationSpecifficExeption {
        credit.setCreditState(CreditState.IN_PROGRESS);

        // Save outcome
        Transaction creditOutcome = new Transaction();
        creditOutcome.setState(TransactionState.COMPLETED);
        creditOutcome.setSum(credit.getBasis().multiply(BigDecimal.valueOf(-1)));
        creditOutcome.setReason(TransactionReason.CREDIT);
        creditOutcome.setCredit(credit);
        credit.setOutcome(creditOutcome);

        //Save insurance
        Insurance creditInsurance = new Insurance();
        creditInsurance.setCredit(credit);
        creditInsurance.setSum(getCalculatorUtil().calculateAmountInsured(credit));
        creditInsurance.setStatus(InsuranceStatus.NOT_PAID);
        credit.setInsurance(creditInsurance);

        calculatorUtil.sync(credit);
        // credit.setDocuments(generateDocuments());
        creditService.save(credit);
        generateDocuments();

        /*resetForm();*/

        currentLevel = 2;
    }

    private Map<DocumentTemplatePurpose, DocumentWrapper> generateDocuments() {
        documents = new HashMap<>();
        Map<String, Object> items = getTemplateItems();

        DocumentTemplate template;

        for (DocumentTemplatePurpose documentTemplatePurpose : DocumentTemplatePurpose.values()) {
            template = documentTemplateService.findByDocumentTemplatePurpose(documentTemplatePurpose);
            if (template != null && template.getSource().length() > 0) {

                if ((documentTemplatePurpose.equals(DocumentTemplatePurpose.DPK1) && credit.getPeriod() == 1) ||
                        (documentTemplatePurpose.equals(DocumentTemplatePurpose.DPK2) && credit.getPeriod() > 1)) {
                    DocumentWrapper documentWrapper = new DocumentWrapper(documentFacotry.generateDocument(template, items));
                    if (documentWrapper != null && documentWrapper.getDocument().getSource().length() > 0) {
                        documents.put(template.getDocumentTemplatePurpose(), documentWrapper);
                    }
                }

                if (template.getDocumentTemplatePurpose().equals(DocumentTemplatePurpose.AGREEMENT_1) ||
                        template.getDocumentTemplatePurpose().equals(DocumentTemplatePurpose.OUTCOME_ORDER) ||
                        template.getDocumentTemplatePurpose().equals(DocumentTemplatePurpose.DPK_APPENDIX2) ||
                        template.getDocumentTemplatePurpose().equals(DocumentTemplatePurpose.DPK_APPENDIX1) ||
                        template.getDocumentTemplatePurpose().equals(DocumentTemplatePurpose.INSURANCE_NOTE1)
                        ) {
                    DocumentWrapper documentWrapper = new DocumentWrapper(documentFacotry.generateDocument(template, items));
                    if (documentWrapper != null && documentWrapper.getDocument().getSource().length() > 0) {
                        documents.put(template.getDocumentTemplatePurpose(), documentWrapper);
                    }
                }
            }
        }


        return documents;
    }


    public List<Payment> getPayments() {
        return payments;
    }

    public void recalculate() {
        // Prepare credit
        credit.setBasis(credit.getOriginalBasis());
        credit.setInterest(credit.getOriginalRate());

        // Calculate insurance amount
        BigDecimal insuranceAmount = calculatorUtil.calculateInsuranceAmount(credit);

        // Fix the credit according to the new rate and basis (set the interest over the insurance)
        credit.setBasis(insuranceAmount.add(credit.getOriginalBasis()));
        credit.setInterest(applicationScoped.getMaximalRate());

        // Recalculate payments
        credit.setPayments(calculatorUtil.calculatePayments(credit));
        applicationFee = credit.getApplicationFee();
    }

    public BigDecimal getIrr() {
        if (credit != null) {
            return credit.getIrr();
        }
        return BigDecimal.valueOf(0d);
    }

    public BigDecimal getGpr() {
        if (credit != null) {
            return credit.getGpr();
        }
        return BigDecimal.valueOf(0d);
    }


    public String getPeriodWord() {
        if (credit.getPeriod() != 1) {
            return getLocaliziedMessage("common.months");
        } else {
            return getLocaliziedMessage("common.month");
        }
    }

    public String getCreditPercent() {
        return credit.getInterest().multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP).toString();
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public void onTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    @Override
    public Boolean getClosable() {
        return true;
    }


    public Boolean getHasGuarantor1() {
        return credit.getGuarantor1() != null;
    }

    public Boolean getHasGuarantor2() {
        return credit.getGuarantor2() != null;
    }

    /*public void onPaymentEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }*/

    public void decline(ActionEvent actionEvent) {
        if (credit.getPerson() != null) {
            credit.setCreditState(CreditState.DECLINED);
            try {
                credit.setCurrency(Currency.getInstance(Locale.getDefault()));
                creditService.save(credit);
                resetForm();
            } catch (Exception e) {
                showMessageInDialog(FacesMessage.SEVERITY_ERROR, "Cannot save declined credit", e.getMessage());
            }
        } else {
            showMessageInDialog(FacesMessage.SEVERITY_WARN, "Важно", "Моля изберете потребител!");
        }
    }

    public String getCurrentDateTime() {
        DateTimeFormatter dtFormater = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").withLocale(Locale.US);
        return dtFormater.print(credit.getCreationDate());
    }

    @PreDestroy
    public void onDestroy() {

    }

    @Override
    public Map<String, Object> getTemplateItems() throws IllegalArgumentException {
        Map<String, Object> items = new HashMap<String, Object>();
        CreditDocumentHelper creditDocumentHelper = new CreditDocumentHelper(credit);
        try {
            return creditDocumentHelper.getTemplateItems();
        } catch (Exception e) {
            showMessageInDialog(FacesMessage.SEVERITY_ERROR, LocaleUtils.getLocaliziedMessage("common.error"), e.getMessage());
        }
        return items;
    }


    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    public Integer getPersonRating() {
        if (credit != null
                && credit.getPerson() != null
                && credit.getPerson().getRating() != null) {
            return credit.getPerson().getRating();
        }
        return 0;
    }

    public void setPersonRating(Integer rating) {
    }

    public Integer getGuarantor1Rating() {
        if (credit != null && credit.getGuarantor1() != null && credit.getGuarantor1().getRating() != null) {
            return credit.getGuarantor1().getRating();
        }
        return 0;
    }

    public void setGuarantor1Rating(Integer rating) {
    }

    public Integer getGuarantor2Rating() {
        if (credit != null && credit.getGuarantor2() != null && credit.getGuarantor2().getRating() != null) {
            return credit.getGuarantor2().getRating();
        }
        return 0;
    }

    public void setGuarantor2Rating(Integer rating) {
    }

    public Credit getSelectedCredit() {
        return selectedCredit;
    }

    public void setSelectedCredit(Credit selectedCredit) {
        this.selectedCredit = selectedCredit;
    }

    public void updateInsurance() {
    }

    public BigDecimal getApplicationFee() {
        return BigDecimal.valueOf(0d);
        /*if (staticFee) {
            return credit.getApplicationFee();
        }
        return applicationFee;*/
    }

    public void setApplicationFee(BigDecimal fee) {
        applicationFee = fee;
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

    public ApplicationScopedMB getApplicationScopedMB() {
        return applicationScopedMB;
    }

    public LocalDateTime getStartDueDate() {
        return startDueDate;
    }

    public void setStartDueDate(LocalDateTime startDueDate) {
        this.startDueDate = startDueDate;
    }

    public Date getToday() {
        return LocalDateTime.now().toDate();
    }

    public void updateStartDueDate() {
        getCalculatorUtil().fixStartDueDate(credit, startDueDate);
    }

    public void applicationFeeChangeListener() {
        staticFee = true;
        credit.setApplicationFee(applicationFee);
    }

    public void setDocuments(Map<DocumentTemplatePurpose, DocumentWrapper> documents) {
        this.documents = documents;
    }


    public Map<DocumentTemplatePurpose, DocumentWrapper> getDocuments() {
        return documents;
    }

    public Document getSelectedDocument() {
        return selectedDocument;
    }

    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }

    public void printDocument(Document document) {
        this.selectedDocument = document;
    }

    public void isUserDepricated(FacesContext ctx, UIComponent component, Object value)
            throws ValidatorException {
        Person person = (Person) value;
        if (person.getAddresses().size() == 0) {
            /*if (getCurrentUser().getRole().equals(Role.ROLE_SUPERADMIN)
                    || getCurrentUser().getRole().equals(Role.ROLE_ADMIN)) {
                return;
            }*/
            throw new ValidatorException(new FacesMessage(getLocaliziedMessage("common.validateMessage.noAddress")));
        }

    }

    public void onPersonSelect(SelectEvent event) {
        if (event.getObject() != null) {
            selectedPerson = (Person) event.getObject();
            // generateAgreementDocument();
        }
    }

    public void printFee4Agreement() {
        Printer printer = new Printer();
        printer.printFee(applicationScopedMB.getFee4Agreement());
    }

    public void printApplicationFee() {
        if (credit != null) {
            Printer printer = new Printer();
            printer.printFee(credit.getApplicationFee());
        }
    }

    public void resetForm() {
        selectedPerson = null;
        selectedDocument = null;
        selectedCredit = null;
        init();
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public Currency getCreditCurrency() {
        return creditCurrency;
    }

    public void setCreditCurrency(Currency creditCurrency) {
        this.creditCurrency = creditCurrency;
    }

    public CalculatorUtil getCalculatorUtil() {
        return calculatorUtil;
    }

    public void setCalculatorUtil(CalculatorUtil calculatorUtil) {
        this.calculatorUtil = calculatorUtil;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setAgreementDocument(Document agreementDocument) {
        this.agreementDocument = agreementDocument;
    }

    public Document getAgreementDocument() {
        return getDocument(DocumentTemplatePurpose.AGREEMENT_1);
    }

    public void setContractDocument1(Document contractDocument1) {
        this.contractDocument1 = contractDocument1;
    }

    public Document getContractDocument1() {
        return getDocument(DocumentTemplatePurpose.DPK1);
    }

    public void setContractDocument2(Document contractDocument2) {
        this.contractDocument2 = contractDocument2;
    }

    public Document getContractDocument2() {
        return getDocument(DocumentTemplatePurpose.DPK2);
    }

    public void setContractAppendix1Document(Document contractAppendix1Document) {
        this.contractAppendix1Document = contractAppendix1Document;
    }

    public Document getContractAppendix1Document() {
        return getDocument(DocumentTemplatePurpose.DPK_APPENDIX1);
    }

    public void setContractGuarantor1Document(Document contractGuarantor1Document) {
        this.contractGuarantor1Document = contractGuarantor1Document;
    }

    public Document getContractGuarantor1Document() {
        return getDocument(DocumentTemplatePurpose.CONTRACT_GUARANTOR1);
    }

    public void setContractGuarantor2Document(Document contractGuarantor2Document) {
        this.contractGuarantor2Document = contractGuarantor2Document;
    }

    public Document getContractGuarantor2Document() {
        return getDocument(DocumentTemplatePurpose.CONTRACT_GUARANTOR2);
    }

    public void setOutcomeOrderDocument(Document outcomeOrderDocument) {
        this.outcomeOrderDocument = outcomeOrderDocument;
    }

    public Document getOutcomeOrderDocument() {
        return getDocument(DocumentTemplatePurpose.OUTCOME_ORDER);
    }

    public void setInsuranceNoteDocument(Document insuranceNoteDocument) {
        this.insuranceNoteDocument = insuranceNoteDocument;
    }

    public Document getInsuranceNoteDocument() {
        return getDocument(DocumentTemplatePurpose.INSURANCE_NOTE1);
    }

    public void setContractAppendix2Document(Document contractAppendix2Document) {
        this.contractAppendix2Document = contractAppendix2Document;
    }

    public Document getContractAppendix2Document() {
        return getDocument(DocumentTemplatePurpose.DPK_APPENDIX2);
    }

    public String getDefaultCommandId(String uniqueIndex) {
        return "showSaveDialogBtn_" + uniqueIndex;
    }

    private Document getDocument(DocumentTemplatePurpose purpose) {
        return creditDocumentHelper.getDocument(purpose);
    }

    public String getConfirmMessage() {
        return LocaleUtils.getLocaliziedMessage("common.saveConfirm");
    }

    public void moveTabBack(ActionEvent actionEvent) {
        documentsTabActiveIndex = (documentsTabActiveIndex <= 0) ? 0 : documentsTabActiveIndex - 1;
    }

    public void moveTabForward(ActionEvent actionEvent) {
        documentsTabActiveIndex = (documentsTabActiveIndex >= documents.size() - 1) ? documents.size() - 1 : documentsTabActiveIndex + 1;
    }

    public int getDocumentsTabActiveIndex() {
        return documentsTabActiveIndex;
    }

    public void setDocumentsTabActiveIndex(int documentsTabActiveIndex) {
        this.documentsTabActiveIndex = documentsTabActiveIndex;
    }

    public void printCurrentDocumentPDF() {
        DocumentWrapper documentWrapper = (DocumentWrapper) documents.values().toArray()[documentsTabActiveIndex];
        PDFFileCreator pdfFileCreator = new PDFFileCreator(documentWrapper.getDocument().getSource());
        File file = pdfFileCreator.createPDFFile();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute(String.format("printPDF('/temp/%s')", file.getName()));
    }

    public void printCurrentDocumentHTML() {
        DocumentWrapper documentWrapper = (DocumentWrapper) documents.values().toArray()[documentsTabActiveIndex];
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute(String.format("printEditorContent('%s')", documentWrapper.getUicomponent().getClientId()));
    }

    /*public void printEditorContent() {
        DocumentWrapper documentWrapper = (DocumentWrapper) documents.values().toArray()[documentsTabActiveIndex];
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute(String.format("printEditorPDF('%s')", documentWrapper.getUicomponent().getClientId()));
    }*/

}


