package com.crediline.model;

import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimer on 1/4/14.
 */
@Entity
@Table(name = "insurances")
public class Insurance extends PersistedVersional implements Identifiable, Serializable {

    private static final long serialVersionUID = 654403112261267098L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigDecimal sum;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<Document>();

    @OneToOne
    private Credit credit;

    private InsuranceStatus status;
    private LocalDateTime paidDate;
    private LocalDateTime closeDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal value) {
        this.sum = value;
    }


    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public InsuranceStatus getStatus() {
        return status;
    }

    public void setStatus(InsuranceStatus status) {
        this.status = status;
    }

    public LocalDateTime getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDateTime paidDate) {
        this.paidDate = paidDate;
    }

    @Transient
    public LocalDateTime getCloseDate() {
        return credit.getLastPayment().getDueDate();
    }

}
