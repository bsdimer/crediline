package com.crediline.mb;

import com.crediline.dao.common.Specifications;
import com.crediline.model.*;
import com.crediline.model.specifications.CreationDateSpecification;
import com.crediline.service.TransactionService;
import com.crediline.utils.LocaleUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component("inquiryTransactions")
@Scope("prototype")
public class InquiryTransactionsMB extends CommonManagedBean implements Serializable, ITabBean {
    private static final long serialVersionUID = 3560428443350426415L;

    @Inject
    private TransactionService transactionService;

    private LocalDateTime issuedBefore;
    private LocalDateTime issuedAfter;
    private Transaction selectedTransaction;
    private User issuer;
    private Region selectedRegion;
    private Office selectedOffice;
    private List<Transaction> filteredTransactions;
    private List<Transaction> result = new ArrayList<Transaction>();
    private String summary;
    private String incomesSum;
    private String outcomesSum;
    private Long incomesNumber;
    private Long outcomesNumber;

    @PostConstruct
    public void init() {
        summary = "0.00 ".concat(LocaleUtils.getLocaliziedMessage("common.currency.bg"));
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        issuedBefore = LocalDateTime.now().plusDays(1);
        issuedAfter = LocalDateTime.parse(LocalDateTime.now().toString("dd/MM/yyyy 00:00:00"), formatter);
        selectedOffice = getCurrentOffice();
    }

    @Override
    public Boolean getClosable() {
        return true;
    }

    public class TransactionSearchSpecification implements Specification<Transaction> {
        @Override
        public Predicate toPredicate(Root<Transaction> transactionRoot, CriteriaQuery<?> query, CriteriaBuilder builder) {
            List<Predicate> predicates = new ArrayList<Predicate>();
            if (selectedOffice != null && selectedOffice.getId() != null) {
                predicates.add(builder.equal(transactionRoot.get(Transaction_.createdIn), selectedOffice));
            }

            if (selectedRegion != null && selectedRegion.getId() != null) {
                predicates.add(builder.equal(transactionRoot.get(Transaction_.createdIn).get(Office_.region), selectedRegion));
            }

            if (issuer != null) {
                predicates.add(builder.equal(transactionRoot.get(Transaction_.createdBy), issuer));  // TODO: ?
            }
            return Specifications.and(predicates, builder);
        }
    }

    public void search() {
        result = transactionService.findAll(Specifications.and(
                new CreationDateSpecification<Transaction>(issuedAfter, issuedBefore),
                new TransactionSearchSpecification()
        ), new Sort("id"));

        /*CriteriaBuilder builder = getTransactionService().getCriteriaBuilder();
        CriteriaQuery<Transaction> criteriaQuery = builder.createQuery(Transaction.class);
        Root transactionRoot = criteriaQuery.from(Transaction.class);
        Expression expression = getTransactionService().getCriteriaBuilder().sum(transactionRoot.get("sum"));
        criteriaQuery.select(expression);
        List sum = getTransactionService().findByCriteria(criteriaQuery);
        setIncomesSum(sum.get(0).toString());*/
    }

    public Transaction getSelectedTransaction() {
        return selectedTransaction;
    }

    public void setSelectedTransaction(Transaction selectedTransaction) {
        this.selectedTransaction = selectedTransaction;
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

    public Region getSelectedRegion() {
        return selectedRegion;
    }

    public void setSelectedRegion(Region selectedRegion) {
        this.selectedRegion = selectedRegion;
    }

    public Office getSelectedOffice() {
        return selectedOffice;
    }

    public void setSelectedOffice(Office selectedOffice) {
        this.selectedOffice = selectedOffice;
    }

    public User getIssuer() {
        return issuer;
    }

    public void setIssuer(User issuer) {
        this.issuer = issuer;
    }

    public List<Transaction> getResult() {
        return result;
    }

    public void setResult(List<Transaction> result) {
        this.result = result;
    }

    public List<Transaction> getFilteredTransactions() {
        return filteredTransactions;
    }

    public void setFilteredTransactions(List<Transaction> filteredTransactions) {
        this.filteredTransactions = filteredTransactions;
    }

    public String getSummary() {
        BigDecimal sum = new BigDecimal(0l);
        if (result != null && result.size() > 0) {
            for (Transaction transaction : result) {
                sum = sum.add(transaction.getSum());
            }
        }
        return sum.toString();
    }


    public String getIncomesSum() {
        BigDecimal sum = new BigDecimal(0l);
        if (result != null && result.size() > 0) {
            for (Transaction transaction : result) {
                if (transaction.getFlowType().equals(FlowType.INCOME)) {
                    sum = sum.add(transaction.getSum());
                }
            }
        }
        return sum.toString();
    }

    public String getOutcomesSum() {
        BigDecimal sum = new BigDecimal(0l);
        if (result != null && result.size() > 0) {
            for (Transaction transaction : result) {
                if (transaction.getFlowType().equals(FlowType.OUTCOME)) {
                    sum = sum.add(transaction.getSum());
                }
            }
        }
        return sum.abs().toString();
    }

    public Long getIncomesNumber() {
        Long sum = 0L;
        if (result != null && result.size() > 0) {
            for (Transaction transaction : result) {
                if (transaction.getFlowType().equals(FlowType.INCOME)) {
                    sum++;
                }
            }
        }
        return sum;
    }

    public Long getOutcomesNumber() {
        Long sum = 0L;
        if (result != null && result.size() > 0) {
            for (Transaction transaction : result) {
                if (transaction.getFlowType().equals(FlowType.OUTCOME)) {
                    sum++;
                }
            }
        }
        return sum;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setIncomesSum(String incomesSum) {
        this.incomesSum = incomesSum;
    }

    public void setOutcomesSum(String outcomesSum) {
        this.outcomesSum = outcomesSum;
    }

    public void setIncomesNumber(Long incomesNumber) {
        this.incomesNumber = incomesNumber;
    }

    public void setOutcomesNumber(Long outcomesNumber) {
        this.outcomesNumber = outcomesNumber;
    }


}
