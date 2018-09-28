package com.crediline.mb;

import com.crediline.exceptions.ApplicationSpecifficExeption;
import com.crediline.model.FlowType;
import com.crediline.model.PaymentMethod;
import com.crediline.model.Transaction;
import com.crediline.model.TransactionReason;
import com.crediline.service.TransactionService;
import com.crediline.utils.LocaleUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dimer on 5/16/14.
 */
@Component("transactionBean")
@Scope("prototype")
public class TransactionMB extends CommonManagedBean implements Serializable, ITabBean {
    @Inject
    private TransactionService transactionService;

    private TransactionReason transactionReason;
    private String comment;
    private BigDecimal sum;
    private PaymentMethod paymentMethod;
    private Transaction transaction;

    @PostConstruct
    public void init() {
        sum = new BigDecimal(0d);
        transactionReason = TransactionReason.UNKNOWN;
        paymentMethod = PaymentMethod.IN_CACHE;
        transaction = new Transaction();
    }


    public void save() throws ApplicationSpecifficExeption {
        transaction.setReason(transactionReason);
        transaction.setPaymentMethod(paymentMethod);
        transaction.setSum(sum);
        try {
            transactionService.save(transaction);
        } catch (Exception e) {
            showGrowl(FacesMessage.SEVERITY_ERROR, LocaleUtils.getLocaliziedMessage("error.database"), e.getMessage());
        }
    }

    public void saveAndClose() throws ApplicationSpecifficExeption {
        transaction.setReason(transactionReason);
        transaction.setPaymentMethod(paymentMethod);
        transaction.setSum(sum);
        try {
            transactionService.save(transaction);
            getTabViewMB().closeByBean(this);
        } catch (Exception e) {
            showGrowl(FacesMessage.SEVERITY_ERROR, LocaleUtils.getLocaliziedMessage("error.database"), e.getMessage());
        }
    }

    @Override

    public Boolean getClosable() {
        return true;
    }

    public TransactionReason getTransactionReason() {
        return transactionReason;
    }

    public void setTransactionReason(TransactionReason transactionReason) {
        this.transactionReason = transactionReason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public FlowType getFlowType() {
        return transaction.getFlowType();
    }

    public void setFlowType(FlowType flowType) {
        if (flowType.equals(FlowType.OUTCOME) && transaction.getSum().floatValue() > 0) {
            transaction.reverse();
        }
    }
}
