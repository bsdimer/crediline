package com.crediline.mb;

import com.crediline.documents.DocumentCompiler;
import com.crediline.model.*;
import com.crediline.service.CreditService;
import com.crediline.service.DocumentTemplateService;
import com.crediline.service.PaymentService;
import com.crediline.utils.CalculatorUtil;
import com.crediline.utils.Currency;
import com.crediline.utils.LocaleUtils;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.joda.time.LocalDateTime;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Component("incomeBean")
@Scope("prototype")
public class IncomeMB extends CommonManagedBean implements Serializable, ITabBean {
    private static final long serialVersionUID = 3756049523908932832L;

    @Inject
    private DocumentTemplateService documentTemplateService;

    @Inject
    private CreditService creditService;

    @Inject
    private PaymentService paymentService;

    @Inject
    private CalculatorUtil calculatorUtil;

    private Person selectedPerson;
    private Credit selectedCredit;
    private Payment selectedPayment;
    private BigDecimal incomeSum;
    private List<Payment> selectedBillPayments = new ArrayList<Payment>();
    private BigDecimal outstandingSumToPay;
    private PersonAdvancedInfo personAdvancedInfo;
    private List<Payment> paidBillPayments;
    private PaymentMethod paymentMethod;
    //ToDo: must implement support for multiple currencies
    private Currency currency;
    private String incomeOrder = "";

    private BigDecimal dueSumUntilNow = BigDecimal.valueOf(0d);
    private BigDecimal dueSumForAddress = BigDecimal.valueOf(0d);
    private BigDecimal dueSumForStreet = BigDecimal.valueOf(0d);
    private boolean skip;
    private Map<Transaction, ReceiptProxy> receipts = new HashMap<>();
    private Payment earlyPaidPayment;
    private List<Transaction> transactions = new ArrayList<>();
    private List<Credit> credits;
    private LocalDateTime activeStartDate;
    private LocalDateTime activeEndDate;
    private List<String> selectedActiveCreditTypes = new ArrayList<String>() {{
        add("active");
    }};
    private List<Credit> closedCredits = new ArrayList<>();
    private List<Credit> gurantOnCredits = new ArrayList<>();
    private LocalDateTime creditsOnStreetStartDate;
    private LocalDateTime creditsOnStreetEndDate;
    private List<String> selectedOnStreetCreditTypes = new ArrayList<String>() {{
        add("active");
    }};
    private List<Credit> creditsOnStreet = new ArrayList<>();
    private List<Credit> creditsOnAddress = new ArrayList<>();
    private List<Credit> creditsOnCity = new ArrayList<>();
    private LocalDateTime creditsOnAddressStartDate;
    private LocalDateTime creditsOnAddressEndDate;
    private List<String> selectedOnAddressCreditTypes = new ArrayList<String>() {{
        add("active");
    }};
    private BigDecimal addressDueSum = BigDecimal.valueOf(0d);
    private BigDecimal activeBasisByPerson = BigDecimal.valueOf(0d);
    private BigDecimal activeReturnedByPerson = BigDecimal.valueOf(0d);
    private BigDecimal activeDueSumByPerson = BigDecimal.valueOf(0d);
    private BigDecimal addressActiveBasisSum = BigDecimal.valueOf(0d);
    private BigDecimal addressActiveReturnedSum = BigDecimal.valueOf(0d);
    private BigDecimal streetActiveBasisSum = BigDecimal.valueOf(0d);
    private BigDecimal streetActiveReturnedSum = BigDecimal.valueOf(0d);
    private BigDecimal streetDueSum = BigDecimal.valueOf(0d);
    private BigDecimal guarantorActiveBasisSum = BigDecimal.valueOf(0d);
    private BigDecimal guarantorActiveReturnedSum = BigDecimal.valueOf(0d);
    private BigDecimal guarantorDueSum = BigDecimal.valueOf(0d);
    private LocalDateTime creditsOnGuarantorStartDate;
    private LocalDateTime creditsOnGuarantorEndDate;
    private List<String> selectedOnGuarantorCreditTypes = new ArrayList<String>() {{
        add("active");
    }};

    @PostConstruct
    public void init() {
        incomeSum = new BigDecimal(0d);
        paymentMethod = PaymentMethod.IN_CACHE;
        outstandingSumToPay = new BigDecimal(0d);
        personAdvancedInfo = new PersonAdvancedInfo(creditService, documentTemplateService);

        selectedPerson = null;
        selectedCredit = null;
        selectedPayment = null;
        incomeSum = BigDecimal.valueOf(0d);
        selectedBillPayments = new ArrayList<Payment>();
        outstandingSumToPay = BigDecimal.valueOf(0d);

        dueSumUntilNow = BigDecimal.valueOf(0d);
        dueSumForAddress = BigDecimal.valueOf(0d);
        dueSumForStreet = BigDecimal.valueOf(0d);

        receipts = new HashMap<>();

        transactions = new ArrayList<>();
        credits = new ArrayList<>();
        selectedActiveCreditTypes = new ArrayList<String>() {{
            add("active");
        }};
        closedCredits = new ArrayList<>();
        gurantOnCredits = new ArrayList<>();
        selectedOnStreetCreditTypes = new ArrayList<String>() {{
            add("active");
        }};
        creditsOnStreet = new ArrayList<>();
        creditsOnAddress = new ArrayList<>();
        creditsOnCity = new ArrayList<>();
        selectedOnAddressCreditTypes = new ArrayList<String>() {{
            add("active");
        }};
        selectedOnGuarantorCreditTypes = new ArrayList<String>() {{
            add("active");
        }};
        addressDueSum = BigDecimal.valueOf(0d);
        activeBasisByPerson = BigDecimal.valueOf(0d);
        activeReturnedByPerson = BigDecimal.valueOf(0d);
        activeDueSumByPerson = BigDecimal.valueOf(0d);
        addressActiveBasisSum = BigDecimal.valueOf(0d);
        addressActiveReturnedSum = BigDecimal.valueOf(0d);
        streetActiveBasisSum = BigDecimal.valueOf(0d);
        streetActiveReturnedSum = BigDecimal.valueOf(0d);
        streetDueSum = BigDecimal.valueOf(0d);
        guarantorActiveBasisSum = BigDecimal.valueOf(0d);
        guarantorActiveReturnedSum = BigDecimal.valueOf(0d);
        guarantorDueSum = BigDecimal.valueOf(0d);
    }

    @Override
    public Boolean getClosable() {
        return true;
    }

    public void printContent(String content) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute(String.format("printContent('%s')", content));
    }

    @Transactional
    public void save() {
        Set<Transaction> transactionSet = receipts.keySet();

        for (Transaction transaction : transactionSet) {
            ReceiptProxy receiptProxy = receipts.get(transaction);
            if (receiptProxy.receiptValue != null) {
                // Printer.createPrinter().printInterest(receiptProxy.receiptValue);
            }

            Credit credit = creditService.findOne(transaction.getCredit().getId());
            credit.getTransactions().add(transaction);
            getTransactionService().save(transaction);
            calculatorUtil.sync(credit);
            getCreditService().save(credit);

            // Generate income order document
            receiptProxy.incomeOrder = DocumentCompiler
                    .getCompiler()
                    .compile(receiptProxy.getDocumentAttributes(), documentTemplateService.findByDocumentTemplatePurpose(DocumentTemplatePurpose.INCOME_ORDER))
                    .getDocument();
            incomeOrder = incomeOrder.concat(receiptProxy.incomeOrder.getSource());
        }

        resetForm();

        /*if ((incomeSum.compareTo(BigDecimal.valueOf(0d)) > 0) && selectedCredit != null) {

            String printRecipt = propertyUtil.getProperties().getProperty("print.recipt");
            if (selectedBillPayments.size() > 0 && printRecipt.equals("true")) {
                Printer printer = new Printer();
                printer.printInterests(selectedBillPayments);
            }

                *//*income.setState(TransactionState.COMPLETED);*//*
            creditService.save(selectedCredit);*/

                /*getTabViewMB().closeByBean(this);
        }
        */
    }

    public void calculateEarlyPaid(Payment payment) {
//    	payment.getCredit().calculateOutstandingSumToPayWithInsurance();
        /*dueSum = selectedPerson.getDueSum();*/
        creditService.findDueSumByPerson(selectedPerson);
        paymentService.save(payment);
        calculatorUtil.sync(payment.getCredit());
    }

    private void resetForm() {
        /*this.selectedPerson = null;
        this.selectedCredit = null;
        credits = new ArrayList<>();
        creditsOnAddress = new ArrayList<>();
        creditsOnStreet = new ArrayList<>();
        creditsOnCity = new ArrayList<>();
        transactions = new ArrayList<>();*/
        init();

    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person person) {
        selectedPerson = person;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public void onPersonSelect() {
        // Selected person is not already assigned
        if (selectedPerson != null) {
            credits = creditService.findActiveCreditByPerson(selectedPerson);
            creditsOnAddress = creditService.findActiveCreditsOnAddress(selectedPerson.getAddresses().get(0));
            creditsOnStreet = creditService.findActiveCreditsOnStreet(selectedPerson.getAddresses().get(0).getStreet());
            creditsOnCity = creditService.findActiveCreditsOnCity(selectedPerson.getAddresses().get(0).getCity());
            gurantOnCredits = creditService.findActiveByGuarantor(selectedPerson);
            closedCredits = creditService.findClosedCreditsByPerson(selectedPerson);
            dueSumUntilNow = creditService.findDueSumByPerson(selectedPerson, LocalDateTime.now());
            activeBasisByPerson = creditService.findActiveBasisByPerson(selectedPerson);
            activeReturnedByPerson = creditService.findActiveReturnedByPerson(selectedPerson);
            activeDueSumByPerson = creditService.findDueSumByPerson(selectedPerson);

            // Address summary
            addressActiveBasisSum = creditService.findAddressActiveBasisSum(selectedPerson.getAddresses().get(0));
            addressActiveReturnedSum = creditService.findAddressActiveReturnedSum(selectedPerson.getAddresses().get(0));
            addressDueSum = creditService.findAddressActiveFullSumByAddress(selectedPerson.getAddresses().get(0));

            // Street summary
            streetActiveBasisSum = creditService.findStreetActiveBasisSum(selectedPerson.getAddresses().get(0));
            streetActiveReturnedSum = creditService.findStreetActiveReturnedSum(selectedPerson.getAddresses().get(0));
            streetDueSum = creditService.findStreetDueSum(selectedPerson.getAddresses().get(0));

            // Guarantor summary
            guarantorActiveBasisSum = creditService.findGuarantorActiveBasisSum(selectedPerson);
            guarantorActiveReturnedSum = creditService.findGuarantorActiveReturnedSum(selectedPerson);
            guarantorDueSum = creditService.findGuarantorDueSum(selectedPerson);
        }
    }

    public Credit getSelectedCredit() {
        return selectedCredit;
    }

    public void setSelectedCredit(Credit selectedCredit) {
        if (selectedCredit != null) {
            this.selectedCredit = selectedCredit;
        }
    }

    public Payment getSelectedPayment() {
        return selectedPayment;
    }

    public void setSelectedPayment(Payment selectedPayment) {
        this.selectedPayment = selectedPayment;
    }

    public void recalculate() {
        /*if (incomeSum.compareTo(BigDecimal.valueOf(0d)) > 0) {

            if (selectedCredit != null) {

                // Generate income
                income.setSum(incomeSum);
                if (income.getId() == null) {
                    getTransactionService().save(income);
                }

                changedPayments = selectedCredit.addIncome(income);

                paidBillPayments = new ArrayList<>();
                paidBillPayments.addAll(changedPayments.keySet());

                selectedBillPayments = new ArrayList<>();
                for (Payment payment : paidBillPayments) {
                    if ((payment.getIsBilled() == null || !payment.getIsBilled()) && payment.isClosed()) {
                        selectedBillPayments.add(payment);
                    }
                }

                // Generate Income order
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("credit", getSelectedCredit());
                parameters.put("payments", paidBillPayments);
                parameters.put("changedPayments", changedPayments);
                parameters.put("person", getSelectedPerson());
                parameters.put("today", LocalDateTime.now());
                parameters.put("transaction", income);

                // Populate spelled sum
                Map<Payment, String> spelledSums = new HashMap<>();
                for (Map.Entry<Payment, BigDecimal> entry : changedPayments.entrySet()) {
                    spelledSums.put(entry.getKey(), SpellerBG.speelCurrency(entry.getValue().floatValue()));
                }
                parameters.put("spelledSums", spelledSums);
                incomeOrder = DocumentCompiler.compile(parameters, incomeOrderTemplate);
            }
        }*/
    }


    public BigDecimal getIncomeSum() {
        return incomeSum;
    }

    public void setIncomeSum(BigDecimal incomeValue) {
        this.incomeSum = incomeValue;
    }

    public List<Payment> getSelectedBillPayments() {
        return selectedBillPayments;
    }

    public void setSelectedBillPayments(List<Payment> selectedBillPayments) {
        this.selectedBillPayments = selectedBillPayments;
    }

    public BigDecimal getOutstandingSumToPay() {
        return this.outstandingSumToPay;
    }

    public void setOutstandingSumToPay(BigDecimal outstandingSumToPay) {
        this.outstandingSumToPay = outstandingSumToPay;
    }

    public boolean getAddDisabled() {
        return (selectedCredit == null);
    }

    public PersonAdvancedInfo getPersonAdvancedInfo() {
        return personAdvancedInfo;
    }

    public void setPersonAdvancedInfo(PersonAdvancedInfo personAdvancedInfo) {
        this.personAdvancedInfo = personAdvancedInfo;
    }

    public List<Payment> getPaidBillPayments() {
        return paidBillPayments;
    }

    public void setPaidBillPayments(List<Payment> paidBillPayments) {
        this.paidBillPayments = paidBillPayments;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void onCreditsRowSelect(SelectEvent event) {
        selectedCredit = (Credit) event.getObject();

        if (selectedCredit != null) {
            // Sync
            calculatorUtil.sync(selectedCredit);
            outstandingSumToPay = selectedCredit.getOustandingSum();

            if (selectedCredit.getActivePayment() != null) {
                incomeSum = selectedCredit.getActivePayment().getOutstandingSum();
            }
        }
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getDueSumUntilNow() {
        return dueSumUntilNow;
    }


    public BigDecimal getDueSumForAddress() {
        return dueSumForAddress;
    }

    public BigDecimal getDueSumForStreet() {
        return dueSumForStreet;
    }

    public String getIncomeOrder() {
        return incomeOrder;
    }

    public void setIncomeOrder(String incomeOrder) {
        this.incomeOrder = incomeOrder;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public Payment getEarlyPaidPayment() {
        return earlyPaidPayment;
    }

    public void setEarlyPaidPayment(Payment earlyPaidPayment) {
        this.earlyPaidPayment = earlyPaidPayment;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addSum() {
        if (incomeSum.compareTo(BigDecimal.valueOf(0d)) > 0 && selectedCredit != null) {

            if (incomeSum.compareTo(outstandingSumToPay) > 0) {
                showMessage(FacesMessage.SEVERITY_ERROR,
                        LocaleUtils.getLocaliziedMessage("error.input"),
                        LocaleUtils.getLocaliziedMessage("validation.income.toHigh"));
                return;
            }

            Transaction income = new Transaction();
            income.setReason(TransactionReason.CREDIT);
            income.setPaymentMethod(paymentMethod);
            income.setState(TransactionState.INITIAL);
            income.setSum(incomeSum);
            income.setCredit(selectedCredit);

            transactions.add(income);
            selectedCredit.getTransactions().add(income);

            generateRecipts(income);
            incomeSum = BigDecimal.valueOf(0d);
        }
    }


    public void addPaymentSum(Payment payment) {
        if (!payment.getClosed()) {
            Transaction income = new Transaction();
            income.setReason(TransactionReason.CREDIT);
            income.setPaymentMethod(paymentMethod);
            income.setState(TransactionState.INITIAL);
            income.setSum(payment.getOutstandingSum());
            income.setCredit(payment.getCredit());

            transactions.add(income);
            selectedCredit.getTransactions().add(income);

            generateRecipts(income);
            incomeSum = BigDecimal.valueOf(0d);
        }
    }

    private void generateRecipts(Transaction transaction) {
        // Calculate active payment index
        Payment originalActivePayment = selectedCredit.getActivePayment();
        BigDecimal originalActivePaymentReturnedSum = selectedCredit.getActivePayment().getReturnedSum();
        calculatorUtil.sync(selectedCredit);
        Payment nextActivePayment = selectedCredit.getActivePayment();

        Map<String, Object> documentAttributes = new HashMap<>();
        documentAttributes.put("person", transaction.getCredit().getPerson());
        documentAttributes.put("transaction", transaction);
        documentAttributes.put("today", LocalDateTime.now());
        documentAttributes.put("credit", transaction.getCredit());

        Map<Payment, BigDecimal> changedSums = new HashMap<>();
        List<Payment> payments = new ArrayList<>();
        // Generate the receipt proxy
        ReceiptProxy receiptProxy = new ReceiptProxy();

        // Decide what to print
        if (nextActivePayment != null && nextActivePayment.equals(originalActivePayment)) {
            // Partially paid payment. Add the difference of the returned sum before and after payment
            changedSums.put(originalActivePayment, nextActivePayment.getReturnedSum().subtract(originalActivePaymentReturnedSum));
            payments.add(originalActivePayment);
            documentAttributes.put("changedSums", changedSums);
        } else {
            // Fully paid payment

            // Last Payment
            if (nextActivePayment == null) {
                nextActivePayment = selectedCredit.getLastPayment();
            }

            // Not a last Payment
            int startIndex = selectedCredit.getPayments().indexOf(originalActivePayment);
            int endIndex = selectedCredit.getPayments().indexOf(nextActivePayment);
            for (int i = startIndex; i < endIndex; i++) {
                Payment payment = selectedCredit.getPayments().get(i);
                payments.add(payment);
                changedSums.put(payment, payment.getBasis());
                documentAttributes.put("changedSums", changedSums);
                // Check whether the payment should be early paid
                if (!payment.getEarlyPaid()) {
                    // Not Early paid so print receipt with interest value
                    receiptProxy.receiptValue = payment.getInterest();
                }
            }
        }
        documentAttributes.put("payments", payments);
        receiptProxy.setDocumentAttributes(documentAttributes);

        receipts.put(transaction, receiptProxy);
    }

    public void removeTransaction(final Transaction transaction) {
        logger.info("Adding transaction");
        Credit credit = Iterables.find(credits, new Predicate<Credit>() {
            @Override
            public boolean apply(Credit credit) {
                return credit.getId().equals(transaction.getCredit().getId());
            }
        });
        credit.getTransactions().remove(transaction);
        calculatorUtil.sync(credit);
        transactions.remove(transaction);
        receipts.remove(transaction);
    }

    public Boolean shouldRenderPayment(Payment payment) {
        return payment.getOutstandingSum().longValue() > 0;
    }

    public BigDecimal getTransactionSum() {
        BigDecimal transactionSum = BigDecimal.valueOf(0d);
        for (Transaction transaction : transactions) {
            transactionSum = transactionSum.add(transaction.getSum());
        }
        return transactionSum;
    }

    public void setActiveStartDate(LocalDateTime activeStartDate) {
        this.activeStartDate = activeStartDate;
    }

    public LocalDateTime getActiveStartDate() {
        return activeStartDate;
    }

    public void setActiveEndDate(LocalDateTime activeEndDate) {
        this.activeEndDate = activeEndDate;
    }

    public LocalDateTime getActiveEndDate() {
        return activeEndDate;
    }

    public void setSelectedActiveCreditTypes(List<String> selectedActiveCreditTypes) {
        this.selectedActiveCreditTypes = selectedActiveCreditTypes;
    }

    public List<String> getSelectedActiveCreditTypes() {
        return selectedActiveCreditTypes;
    }

    public void setClosedCredits(List<Credit> closedCredits) {
        this.closedCredits = closedCredits;
    }

    public List<Credit> getClosedCredits() {
        return closedCredits;
    }

    public void setGurantOnCredits(List<Credit> gurantOnCredits) {
        this.gurantOnCredits = gurantOnCredits;
    }

    public List<Credit> getGurantOnCredits() {
        return gurantOnCredits;
    }

    public void setCreditsOnStreetStartDate(LocalDateTime creditsOnStreetStartDate) {
        this.creditsOnStreetStartDate = creditsOnStreetStartDate;
    }

    public LocalDateTime getCreditsOnStreetStartDate() {
        return creditsOnStreetStartDate;
    }

    public void setCreditsOnStreetEndDate(LocalDateTime creditsOnStreetEndDate) {
        this.creditsOnStreetEndDate = creditsOnStreetEndDate;
    }

    public LocalDateTime getCreditsOnStreetEndDate() {
        return creditsOnStreetEndDate;
    }

    public void setSelectedOnStreetCreditTypes(List<String> selectedOnStreetCreditTypes) {
        this.selectedOnStreetCreditTypes = selectedOnStreetCreditTypes;
    }

    public List<String> getSelectedOnStreetCreditTypes() {
        return selectedOnStreetCreditTypes;
    }

    public void setCreditsOnStreet(List<Credit> creditsOnStreet) {
        this.creditsOnStreet = creditsOnStreet;
    }

    public List<Credit> getCreditsOnStreet() {
        return creditsOnStreet;
    }

    public List<Credit> getCreditsOnAddress() {
        return creditsOnAddress;
    }

    public void setCreditsOnAddress(List<Credit> creditsOnAddress) {
        this.creditsOnAddress = creditsOnAddress;
    }

    public List<Credit> getCreditsOnCity() {
        return creditsOnCity;
    }

    public void setCreditsOnCity(List<Credit> creditsOnCity) {
        this.creditsOnCity = creditsOnCity;
    }

    public void setCreditsOnAddressStartDate(LocalDateTime creditsOnAddressStartDate) {
        this.creditsOnAddressStartDate = creditsOnAddressStartDate;
    }

    public LocalDateTime getCreditsOnAddressStartDate() {
        return creditsOnAddressStartDate;
    }

    public void setCreditsOnAddressEndDate(LocalDateTime creditsOnAddressEndDate) {
        this.creditsOnAddressEndDate = creditsOnAddressEndDate;
    }

    public LocalDateTime getCreditsOnAddressEndDate() {
        return creditsOnAddressEndDate;
    }

    public void setSelectedOnAddressCreditTypes(List<String> selectedOnAddressCreditTypes) {
        this.selectedOnAddressCreditTypes = selectedOnAddressCreditTypes;
    }

    public List<String> getSelectedOnAddressCreditTypes() {
        return selectedOnAddressCreditTypes;
    }

    public BigDecimal getActiveBasisByPerson() {
        return activeBasisByPerson;
    }

    public BigDecimal getActiveReturnedByPerson() {
        return activeReturnedByPerson;
    }

    public BigDecimal getActiveDueSumByPerson() {
        return activeDueSumByPerson;
    }

    public BigDecimal getAddressActiveBasisSum() {
        return addressActiveBasisSum;
    }

    public BigDecimal getAddressActiveReturnedSum() {
        return addressActiveReturnedSum;
    }

    public BigDecimal getAddressDueSum() {
        return addressDueSum;
    }

    public BigDecimal getStreetBasisSum() {
        return streetActiveBasisSum;
    }

    public BigDecimal getStreetReturnedSum() {
        return streetActiveReturnedSum;
    }

    public BigDecimal getStreetDueSum() {
        return streetDueSum;
    }

    public void printCurrentDocumentHTML() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute(String.format("printComponentContentById('incomeOrderContainer_0');"));
    }

    public BigDecimal getGuarantorActiveBasisSum() {
        return guarantorActiveBasisSum;
    }

    public void setGuarantorActiveBasisSum(BigDecimal guarantorActiveBasisSum) {
        this.guarantorActiveBasisSum = guarantorActiveBasisSum;
    }

    public BigDecimal getGuarantorActiveReturnedSum() {
        return guarantorActiveReturnedSum;
    }

    public void setGuarantorActiveReturnedSum(BigDecimal guarantorActiveReturnedSum) {
        this.guarantorActiveReturnedSum = guarantorActiveReturnedSum;
    }

    public BigDecimal getGuarantorDueSum() {
        return guarantorDueSum;
    }

    public void setGuarantorDueSum(BigDecimal guarantorDueSum) {
        this.guarantorDueSum = guarantorDueSum;
    }

    public void setCreditsOnGuarantorStartDate(LocalDateTime creditsOnGuarantorStartDate) {
        this.creditsOnGuarantorStartDate = creditsOnGuarantorStartDate;
    }

    public LocalDateTime getCreditsOnGuarantorStartDate() {
        return creditsOnGuarantorStartDate;
    }

    public void setCreditsOnGuarantorEndDate(LocalDateTime creditsOnGuarantorEndDate) {
        this.creditsOnGuarantorEndDate = creditsOnGuarantorEndDate;
    }

    public LocalDateTime getCreditsOnGuarantorEndDate() {
        return creditsOnGuarantorEndDate;
    }

    public void setSelectedOnGuarantorCreditTypes(List<String> selectedOnGuarantorCreditTypes) {
        this.selectedOnGuarantorCreditTypes = selectedOnGuarantorCreditTypes;
    }

    public List<String> getSelectedOnGuarantorCreditTypes() {
        return selectedOnGuarantorCreditTypes;
    }

    /*public void printCurrentDocumentHTML(String uniqueIndex) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute(String.format("printComponentContentById('incomeOrderContainer_" + uniqueIndex + "');"));
    }*/

    private class ReceiptProxy {
        private Map<String, Object> documentAttributes;
        private BigDecimal receiptValue;
        private Document incomeOrder;

        private ReceiptProxy(Map<String, Object> documentAttributes, BigDecimal receiptValue) {
            this.documentAttributes = documentAttributes;
            this.receiptValue = receiptValue;
        }

        private ReceiptProxy() {
        }

        public Document getIncomeOrder() {
            return incomeOrder;
        }

        public BigDecimal getReceiptValue() {
            return receiptValue;
        }

        public Map<String, Object> getDocumentAttributes() {
            return documentAttributes;
        }

        public void setDocumentAttributes(Map<String, Object> documentAttributes) {
            this.documentAttributes = documentAttributes;
        }


        public void setIncomeOrder(Document incomeOrder) {
            this.incomeOrder = incomeOrder;
        }

        public void setReceiptValue(BigDecimal receiptValue) {
            this.receiptValue = receiptValue;
        }

    }
}
