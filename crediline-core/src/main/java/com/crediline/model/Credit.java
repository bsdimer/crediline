package com.crediline.model;

import com.crediline.utils.PrintUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.joda.time.Days;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by dimer on 1/4/14.
 */
@Entity
@Table(name = "credits")
@EntityListeners(value = {CreditEntityListener.class})
public class Credit extends PersistedVersional implements Serializable {

    private static final long serialVersionUID = -7571077756157086335L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guarantor1_id")
    private Person guarantor1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guarantor2_id")
    private Person guarantor2;

    @Min(0)
    @Column
    private BigDecimal basis;

    @Min(0)
    @Column
    private BigDecimal originalBasis;

    @Min(1)
    @Column
    private Integer period;

    @Min(0)
    @Column(precision = 10, scale = 7)
    private BigDecimal interest;

    @NotNull
    @Size(min = 1)
    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<Payment>();

    @NotNull
    @Size(min = 1)
    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<Transaction>();

    @Column
    @Enumerated(EnumType.STRING)
    private CreditState creditState = CreditState.INITIAL;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private List<Comment> comments;

    @Column(name = "close_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime closedDate;

    @Column(name = "due_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime dueDate;

    @Column(name = "pickUp_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime pickUpDate;


    @OneToMany(cascade = CascadeType.ALL)
    private Map<DocumentTemplatePurpose, Document> documents = new HashMap<>();

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Transaction outcome;

    @Column(name = "is_registered")
    private Boolean isRegistered = false;

    @Column
    private Boolean itWasInOverdue = false;

    @Column
    private BigDecimal applicationFee = BigDecimal.valueOf(0d);

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Insurance insurance;

    @Column(precision = 10, scale = 7)
    private BigDecimal originalRate;

    @Column
    private BigDecimal oustandingSum;

    @Column
    private BigDecimal returnedSum;

    @Column
    private BigDecimal interestSum;

    @Column
    private BigDecimal overdueInterest;

    @Column(name = "invalidation_reason")
    private String invalidationReason;

    @Column
    private Currency currency = Currency.getInstance("BGN");

    @Column
    private BigDecimal fullSum;

    @Column
    private Boolean needInsurance;

    @Column(nullable = false)
    private BigDecimal irr;


    @Column(nullable = false)
    private BigDecimal gpr;

    @Column(nullable = false)
    private BigDecimal anuitetValue;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public BigDecimal getBasis() {
        return basis;
    }

    public void setBasis(BigDecimal sum) {
        this.basis = sum;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }


    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> incomes) {
        this.transactions = incomes;
    }


    public CreditState getCreditState() {
        return creditState;
    }

    public void setCreditState(CreditState creditState) {
        this.creditState = creditState;
    }


    public Person getGuarantor1() {
        return guarantor1;
    }

    public void setGuarantor1(Person guarantor1) {
        this.guarantor1 = guarantor1;
    }

    public Person getGuarantor2() {
        return guarantor2;
    }

    public void setGuarantor2(Person guarantor2) {
        this.guarantor2 = guarantor2;
    }


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public BigDecimal getOustandingSum() {
        return oustandingSum;
    }

    public void setOustandingSum(BigDecimal oustandingSum) {
        this.oustandingSum = oustandingSum;
    }

    public BigDecimal getFullSum() {
        return fullSum;
    }

    public void setFullSum(BigDecimal fullSum) {
        this.fullSum = fullSum;
    }


    public BigDecimal getInterestSum() {
        return interestSum;
    }

    public void setInterestSum(BigDecimal interestSum) {
        this.interestSum = interestSum;
    }


    public BigDecimal getReturnedSum() {
        return returnedSum;
    }

    public void setReturnedSum(BigDecimal value) {
        returnedSum = value;
    }


    public BigDecimal getOverdueInterest() {
        return overdueInterest;
    }

    public void setOverdueInterest(BigDecimal overdueInterest) {
        this.overdueInterest = overdueInterest;
    }


    public LocalDateTime getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Transaction getOutcome() {
        return outcome;
    }

    public void setOutcome(Transaction outcome) {
        this.outcome = outcome;
    }

    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }


    public Boolean getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(Boolean isRegistered) {
        this.isRegistered = isRegistered;
    }


    public Boolean getItWasInOverdue() {
        return this.itWasInOverdue;
    }

    public void setItWasInOverdue(Boolean itWasInOverdue) {
        this.itWasInOverdue = itWasInOverdue;
    }


    public Boolean getNeedInsurance() {
        return needInsurance;
    }

    public void setNeedInsurance(Boolean needInsurance) {
        this.needInsurance = needInsurance;
    }


    public BigDecimal getApplicationFee() {
        return applicationFee;
    }

    public void setApplicationFee(BigDecimal applicationFee) {
        this.applicationFee = applicationFee;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }


    public BigDecimal getOriginalRate() {
        return originalRate;
    }

    public void setOriginalRate(BigDecimal realRate) {
        this.originalRate = realRate;
    }

    public void setIrr(BigDecimal irr) {
        this.irr = irr;
    }

    public void setGpr(BigDecimal gpr) {
        this.gpr = gpr;
    }

    public void setAnuitetValue(BigDecimal anuitetValue) {
        this.anuitetValue = anuitetValue;
    }

    public BigDecimal getIrr() {
        return irr;
    }

    public BigDecimal getGpr() {
        return gpr;
    }

    public BigDecimal getAnuitetValue() {
        return anuitetValue;
    }

    public String getInvalidationReason() {
        return invalidationReason;
    }

    public void setInvalidationReason(String invalidationReason) {
        this.invalidationReason = invalidationReason;
    }

    public BigDecimal getOriginalBasis() {
        return originalBasis;
    }

    public void setOriginalBasis(BigDecimal originalBasis) {
        this.originalBasis = originalBasis;
    }


    public LocalDateTime getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(LocalDateTime pickUpDate) {
        this.pickUpDate = pickUpDate;
    }


    public Map<DocumentTemplatePurpose, Document> getDocuments() {
        return documents;
    }

    public void setDocuments(Map<DocumentTemplatePurpose, Document> documents) {
        this.documents = documents;
    }


    @Override
    public String toString() {
        return PrintUtils.print(this);
    }



    @Transient
    public Payment getActivePayment() {
        for (Payment payment : getPayments()) {
            if (payment.getOutstandingSum().compareTo(BigDecimal.valueOf(0d)) > 0) {
                return payment;
            }
        }
        return null;
    }

    @Transient
    public List<Payment> getActivePayments() {
        List<Payment> activePayments = new ArrayList<Payment>();
        for (Payment payment : getPayments()) {
            if (!payment.getClosed()) {
                activePayments.add(payment);
            }
        }
        return activePayments;
    }

    @Transient
    public List<Payment> getClosedPayments() {
        List<Payment> resultPayments = new ArrayList<Payment>();
        for (Payment payment : getPayments()) {
            if (payment.getClosed()) {
                resultPayments.add(payment);
            }
        }
        return resultPayments;
    }

    /**
     * @return the annual interest - this is calculated interest multiplied by 12
     */
    @Transient
    public BigDecimal getAnnualInterest() {
        return getInterest().multiply(new BigDecimal(12));
    }

    @Transient
    public int getOverdueDays() {
        Days difference = null;
        if (getClosedDate() == null) {
            difference = Days.daysBetween(getDueDate(), new LocalDateTime());
        } else {
            difference = Days.daysBetween(getDueDate(), getClosedDate());
        }

        return difference.getDays();
    }

    @Transient
    public boolean isItRepaid() {
        return getClosedDate() != null;
    }

    @Transient
    public boolean isInOverdue() {
        if (getOverdueDays() > 0) {
            return true;
        } else {
            return false;
        }
    }


    @Transient
    public List<Transaction> getIncomes() {
        List<Transaction> incomes = new ArrayList<>();
        for (Transaction transaction : getTransactions()) {
            if (transaction.getFlowType().equals(FlowType.INCOME)) {
                incomes.add(transaction);
            }
        }
        return incomes;
    }

    @Transient
    public BigDecimal getDueSumForPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal dueSum = BigDecimal.valueOf(0d);
        for (Payment payment : getActivePayments()) {
            if (payment.getClosed().equals(false)
                    && payment.getDueDate().isAfter(startDate)
                    && payment.getDueDate().isBefore(endDate)) {
                dueSum = dueSum.add(payment.getOutstandingSum());
            }
        }
        return dueSum;
    }

    @Transient
    public BigDecimal getDueSumUntilNow() {
        BigDecimal dueSum = BigDecimal.valueOf(0d);
        for (Payment payment : getActivePayments()) {
            if (payment.getClosed().equals(false)
                    && payment.getDueDate().isBefore(LocalDateTime.now())) {
                dueSum = dueSum.add(payment.getOutstandingSum());
            }
        }
        return dueSum;
    }


    @Transient
    public BigDecimal getMonthInterest() {
        return BigDecimal.valueOf(getInterest().floatValue() * 100f).setScale(2, RoundingMode.HALF_UP);
    }

    @Transient
    public BigDecimal getYearInterest() {
        return BigDecimal.valueOf(getMonthInterest().floatValue() * 12f).setScale(2, RoundingMode.HALF_UP);
    }

    @Transient
    public BigDecimal getDayInterest() {
        return BigDecimal.valueOf(getMonthInterest().floatValue() / 30f).setScale(2, RoundingMode.HALF_UP);
    }


    @Transient
    public List<Payment> getOverduePayments() {
        List<Payment> result = new ArrayList<>();
        for (Payment payment : getPayments()) {
            if (payment.isInOverdue()) {
                result.add(payment);
            }
        }
        return result;
    }

    @Transient
    public List<Payment> getOverduePayments(LocalDateTime from, LocalDateTime to) {
        List<Payment> result = new ArrayList<>();
        if (from != null && to != null) {
            for (Payment payment : getPayments()) {
                if (payment.getDueDate().isAfter(from) && payment.getDueDate().isBefore(to)) {
                    result.add(payment);
                }
            }
        }
        return result;
    }


    @Transient
    public Payment getLastPayment() {
        return getPayments().get(getPayments().size() - 1);
    }

    @Transient
    public LocalDateTime getEndDate() {
        if (getPayments().size() > 0) {
            return getLastPayment().getDueDate();
        }
        return getCreationDate();
    }

    @Transient
    public boolean getActive() {
        return (this.getCreditState().equals(CreditState.IN_PROGRESS) || this.getCreditState().equals(CreditState.OVERDUE));
    }

    @Transient
    public void setDocument(DocumentTemplatePurpose purpose, Document document) {
        if (document != null) {
            if (documents.containsKey(purpose)) {
                documents.remove(purpose);
            }
            documents.put(purpose, document);
        }
    }



   /* private Map<Payment, BigDecimal> sync() {
        BigDecimal returnedSum = calculateReturnedSum();
        Map<Payment, BigDecimal> changedPayments = new HashMap<>();

        // Fix payments according to the recieved incomes
        int incrementId = -1;
        for (Payment payment : getPayments()) {

            incrementId++;

            // Check whether the returned value is bigger that 0;
            if (returnedSum.compareTo(BigDecimal.valueOf(0d)) > 0) {

                // Check whether the returned value will fill the full payment;
                if (returnedSum.compareTo(payment.getSum()) >= 0) {
                    // True
                    changedPayments.put(payment, payment.getBasis());
                    returnedSum = returnedSum.subtract(payment.getSum());
                    payment.setReturnedSum(payment.getSum());
                    if (incrementId >= getIncomes().size()) {
                        payment.setClosedDate(getLastIncome().getCreationDate());
                        payment.setClosed(true);
                    } else {
                        payment.setClosedDate(getIncomes().get(incrementId).getCreationDate());
                        payment.setClosed(true);
                    }

                } else {
                    // False
                    changedPayments.put(payment, returnedSum.subtract(getActivePayment().getReturnedSum()));
                    Double delta = returnedSum.doubleValue() - payment.getSum().doubleValue();
                    payment.setReturnedSum(returnedSum);
                    if (delta < 1.0 && delta > -1.0) {
                        // Fix the rounding
                        payment.setBasis(payment.getBasis().
                                add(BigDecimal.valueOf(returnedSum.doubleValue() - payment.getSum().doubleValue()).setScale(2, RoundingMode.HALF_UP)));
                        payment.setClosed(true);
                        payment.setClosedDate(getLastIncome().getCreationDate());
                    }
                    returnedSum = BigDecimal.valueOf(0d);
                }

            } else {
                break;
            }
        }


        // Fix credit state according to the recieved incomes
        if (!creditState.equals(CreditState.INITIAL) && !creditState.equals(CreditState.DECLINED) && !creditState.equals(CreditState.INVALID)) {

            // Change credit state
            if (getPayments().size() > 0 && getLastPayment().getClosed()) {
                closedDate = getLastPayment().getClosedDate();
                dueDate = getLastPayment().getDueDate();
                creditState = CreditState.CLOSED;
                return changedPayments;
            } else {
                if (getActivePayment() != null) {
                    if (LocalDateTime.now().isAfter(getActivePayment().getDueDate())) {
                        creditState = CreditState.OVERDUE;
                        getActivePayment().setItWasInOverdue(true);
                        this.setItWasInOverdue(true);
                        this.setDueDate(getActivePayment().getDueDate());
                    } else {
                        creditState = CreditState.IN_PROGRESS;
                        this.setDueDate(getActivePayment().getDueDate());
                    }
                }
            }
        }
        return changedPayments;
    }*/


}