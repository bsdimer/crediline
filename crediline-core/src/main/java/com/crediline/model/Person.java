package com.crediline.model;

import com.crediline.utils.PrintUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by dimer on 1/4/14.
 */
@Entity
@Table(name = "persons")
public class Person extends PersistedVersional implements Identifiable, Serializable {


    private static final long serialVersionUID = 5064001813612654811L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String midname;

    @Column(nullable = false, length = 100)
    private String surname;

    @Column(unique = true, nullable = false, length = 100)
    private String egn;

    @Column(unique = true, nullable = false, length = 20)
    private String lkNo;

    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime lkIssueDate;

    @Column(length = 100)
    private String lkIssueLocation;

    @OneToOne(fetch = FetchType.EAGER)
    private City lkBirthPlace;

    @Column
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column
    private String email;

    @Column
    private String picture;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private List<Phone> phones = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Credit> credits = new ArrayList<>();

    @Column
    private Integer rating;

    @Column(name = "hasRealEstate")
    private Boolean hasRealEstate;

    @Column
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column
    @Enumerated(EnumType.STRING)
    private Education education;

    @Column(length = 50)
    private String profession;

    @Column(name = "hasJob")
    private Boolean hasJob;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<Comment> comments = new ArrayList<Comment>();

    @Column(name = "lkvalid_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime validBefore;

    @Column(length = 100)
    private String workplace;

    @Column(length = 100)
    private String citizenship;

    @Column(length = 30)
    private String debitCard;


    public Long getId() {
        return this.id;
    }

    public void setId(Long userId) {
        this.id = userId;
    }


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getMidname() {
        return midname;
    }

    public void setMidname(String midname) {
        this.midname = midname;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }


    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public City getLkBirthPlace() {
        return lkBirthPlace;
    }

    public void setLkBirthPlace(City lkBirthPlace) {
        this.lkBirthPlace = lkBirthPlace;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }


    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }


    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }


    public Boolean hasRealEstate() {
        return hasRealEstate;
    }

    public void setHasRealEstate(Boolean hasRealEstate) {
        this.hasRealEstate = hasRealEstate;
    }


    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }


    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }


    public Boolean hasJob() {
        return hasJob;
    }

    public void setHasJob(Boolean hasJob) {
        this.hasJob = hasJob;
    }


    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }


    public String getLkNo() {
        return lkNo;
    }

    public void setLkNo(String lkNo) {
        this.lkNo = lkNo;
    }


    public LocalDateTime getLkIssueDate() {
        return lkIssueDate;
    }

    public void setLkIssueDate(LocalDateTime lkIssueDate) {
        this.lkIssueDate = lkIssueDate;
    }


    public String getLkIssueLocation() {
        return lkIssueLocation;
    }

    public void setLkIssueLocation(String lkIssueLocation) {
        this.lkIssueLocation = lkIssueLocation;
    }


    public LocalDateTime getValidBefore() {
        return validBefore;
    }

    public void setValidBefore(LocalDateTime validBefore) {
        this.validBefore = validBefore;
    }


    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }


    public String getDebitCard() {
        return debitCard;
    }

    public void setDebitCard(String debitCard) {
        this.debitCard = debitCard;
    }

    @Transient
    public String getFullName() {
        return getName() + " " + getMidname() + " " + getSurname();
    }

    @Transient
    public Address getHomeAddress() {
        for (Address address : addresses) {
            if (AddressType.HOME.equals(address.getAddressType())) {
                return address;
            }
        }
        return null;
    }

    @Transient
    public Address getLkAddress() {
        for (Address address : addresses) {
            if (AddressType.LK.equals(address.getAddressType())) {
                return address;
            }
        }
        return null;
    }

    @Transient
    public boolean hasCreditBeforeDate(LocalDateTime date) {
        for (Credit credit : credits) {
            if (credit.getCreationDate().isBefore(date)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return PrintUtils.print(this);
    }


    @Transient
    public List<Credit> getActiveCredits() {
        List<Credit> result = new ArrayList<>();
        for (Credit credit : getCredits()) {
            if (credit.getCreditState().equals(CreditState.IN_PROGRESS)
                    || credit.getCreditState().equals(CreditState.OVERDUE)) {
                result.add(credit);
            }
        }
        return result;
    }


    @Transient
    public LocalDateTime getBirthDate() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyMMdd");
        LocalDateTime birthdate = LocalDateTime.parse(getEgn().substring(0, 6), formatter);
        return birthdate;
    }

    @Transient
    public Address getMainAddress() {
        return getAddresses().get(0);
    }


    @Transient
    public Phone getMainPhone() {
        if (getPhones().size() > 0) {
            return getPhones().get(0);
        }
        return new Phone();
    }

    /*@Transient
    public BigDecimal getDueSumForPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal result = BigDecimal.valueOf(0d);
        for (Credit credit : getActiveCredits()) {
            result = result.add(credit.getDueSumForPeriod(startDate, endDate));
        }
        return result;
    }

    @Transient
    public BigDecimal getDueSum() {
        BigDecimal result = BigDecimal.valueOf(0d);
        for (Credit credit : getActiveCredits()) {
            result = result.add(credit.getOustandingSum());
        }
        return result;
    }

    @Transient
    public BigDecimal getDueSumUntilNow() {
        BigDecimal result = BigDecimal.valueOf(0d);
        for (Credit credit : getActiveCredits()) {
            result = result.add(credit.getDueSumUntilNow());
        }
        return result;
    }*/


    @Transient
    public List<Payment> getOverduePayments() {
        List<Payment> result = new ArrayList<>();
        for (Credit credit : getActiveCredits()) {
            result.addAll(credit.getOverduePayments());
        }
        return result;
    }

    @Transient
    public List<Credit> getOverdueCredits() {
        List<Credit> result = new ArrayList<>();
        for (Credit credit : getCredits()) {
            if (credit.getCreditState().equals(CreditState.OVERDUE)) {
                result.add(credit);
            }
        }
        return result;
    }

    @Transient
    public List<Credit> getOverdueCredits(LocalDateTime from, LocalDateTime to) {
        List<Credit> result = new ArrayList<>();
        for (Credit credit : getCredits()) {
            if (credit.getCreditState().equals(CreditState.OVERDUE)) {
                result.add(credit);
            }
        }
        return result;
    }

    @Transient
    public BigDecimal getActiveBasisSum() {
        BigDecimal result = BigDecimal.valueOf(0d);
        for (Credit credit : getActiveCredits()) {
            result = result.add(credit.getBasis());
        }
        return result;
    }

    @Transient
    public BigDecimal getActiveReturnedSum() {
        BigDecimal result = BigDecimal.valueOf(0d);
        for (Credit credit : getActiveCredits()) {
            result = result.add(credit.getReturnedSum());
        }
        return result;
    }

    @Transient
    public BigDecimal getDueSumForPeriod(LocalDateTime from, LocalDateTime to) {
        BigDecimal result = BigDecimal.valueOf(0d);
        for (Credit credit : getActiveCredits()) {
            for (Payment payment : credit.getPayments()) {
                if (!payment.getClosed() && payment.getDueDate().isAfter(from) && payment.getDueDate().isBefore(to)) {
                    result = result.add(credit.getOustandingSum());
                }
            }
        }
        return result;
    }

}



