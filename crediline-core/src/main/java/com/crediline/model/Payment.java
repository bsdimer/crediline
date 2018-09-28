package com.crediline.model;

import com.crediline.utils.PrintUtils;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dimer on 1/4/14.
 */
@Entity
@Table(name = "payments")
public class Payment extends PersistedVersional implements Identifiable, Serializable {


    private static final long serialVersionUID = -1921737511901263387L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id", nullable = false)
    private Credit credit;

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime dueDate;

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime closedDate;

    @Column
    private BigDecimal basis;

    @Column
    private BigDecimal interest;

    @Column
    private BigDecimal insurance = BigDecimal.valueOf(0d);

    @Column
    private BigDecimal returnedSum = BigDecimal.valueOf(0d);

    @Column
    private Boolean itWasInOverdue = false;

    @Column
    private Boolean isBilled = false;

    @Column
    private Boolean earlyPaid = false;

    @Column
    private BigDecimal balance;

    @Column
    private Boolean closed = false;

    @Column
    private BigDecimal sum;

    @Column
    private BigDecimal outstandingSum;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }


    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime issueDate) {
        this.dueDate = issueDate;
    }


    public BigDecimal getBasis() {
        return basis;
    }

    public void setBasis(BigDecimal basis) {
        this.basis = basis;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }


    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }

    public BigDecimal getReturnedSum() {
        return returnedSum;
    }

    public void setReturnedSum(BigDecimal returnedSum) {
        this.returnedSum = returnedSum;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean value) {
        closed = value;
    }

    public Boolean getItWasInOverdue() {
        return itWasInOverdue;
    }

    public void setItWasInOverdue(Boolean itWasInOverdue) {
        this.itWasInOverdue = itWasInOverdue;
    }

    public Boolean getIsBilled() {
        return isBilled;
    }

    public void setIsBilled(Boolean isBilled) {
        this.isBilled = isBilled;
    }

    public BigDecimal getInsurance() {
        return insurance;
    }

    public void setInsurance(BigDecimal insurance) {
        this.insurance = insurance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    public Boolean getEarlyPaid() {
        return earlyPaid;
    }

    public void setEarlyPaid(Boolean earlyPaid) {
        this.earlyPaid = earlyPaid;
    }

    public BigDecimal getOutstandingSum() {
        return outstandingSum;
    }

    public void setOutstandingSum(BigDecimal outstandingSum) {
        this.outstandingSum = outstandingSum;
    }

    @Transient
    public BigDecimal getBilledAmount() {
        if (getOutstandingSum().floatValue() == 0f) {
            return getBasis();
        }
        return getReturnedSum();
    }

    @Override
    public String toString() {
        return PrintUtils.print(this);
    }

    @Transient
    public boolean isInOverdue() {
        if ((getClosedDate() == null && getDueDate().isBefore(LocalDateTime.now()))
                || (getClosedDate() != null && getClosedDate().isAfter(getDueDate()))) {
            return true;
        } else {
            return false;
        }
    }

}
