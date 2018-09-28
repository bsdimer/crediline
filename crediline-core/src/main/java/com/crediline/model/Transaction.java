package com.crediline.model;

import com.crediline.utils.PrintUtils;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

/**
 * Created by dimer on 1/4/14.
 */
@Entity
@Table(name = "transactions")
@EntityListeners(value = {TransactionEntityListener.class})
public class Transaction extends PersistedVersional implements Identifiable, Serializable {

    private static final long serialVersionUID = 863637950814194394L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal sum = BigDecimal.valueOf(0d);

    @OneToMany(fetch = FetchType.LAZY)
    @Fetch(org.hibernate.annotations.FetchMode.JOIN)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod = PaymentMethod.IN_CACHE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionReason reason;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private TransactionState state;

    @Column
    private Currency currency = Currency.getInstance("BGN");


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


    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Transient
    public FlowType getFlowType() {
        if (getSum().compareTo(BigDecimal.valueOf(0d)) > 0) {
            return FlowType.INCOME;
        }
        return FlowType.OUTCOME;
    }

   /* @Transient
    public void setFlowType(FlowType flowType) {
        if (flowType.equals(FlowType.INCOME)) {
            if (this.getSum().compareTo(BigDecimal.valueOf(0d)) < 0) {
                this.setSum(getSum().multiply(BigDecimal.valueOf(-1)));
            }
        } else {
            if (this.getSum().compareTo(BigDecimal.valueOf(0d)) > 0) {
                this.setSum(getSum().multiply(BigDecimal.valueOf(-1)));
            }
        }
    }*/

    /*
    public void set
    (FlowType flowType) {
        this.flowType = flowType;
    }*/


    public TransactionReason getReason() {
        return reason;
    }


/*    @Transient
    public int getFlowSign() {
        return getFlowType().equals(FlowType.OUTCOME) ? -1 : 1;
    }*/

/*    @Transient
    public BigDecimal getFlowSum() {
        BigDecimal flowSign = new BigDecimal(getFlowSign());
        return getSum().multiply(flowSign);
    }*/


    public void setReason(TransactionReason reason) {
        this.reason = reason;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


    public Currency getCurrency() {
        return currency;
    }


    public TransactionState getState() {
        return state;
    }

    public void setState(TransactionState state) {
        this.state = state;
    }

    public void reverse() {
        setSum(getSum().multiply(BigDecimal.valueOf(-1d)));
    }

    @Override
    public String toString() {
        return PrintUtils.print(this);
    }

}
