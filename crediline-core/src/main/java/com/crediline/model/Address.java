package com.crediline.model;

import com.crediline.utils.LocaleUtils;
import com.crediline.utils.PrintFlag;
import com.crediline.utils.PrintUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by dimer on 1/4/14.
 */
@Entity
@Table(name = "addresses")
public class Address extends PersistedVersional implements Identifiable, Serializable {

    private static final long serialVersionUID = 885795669149286396L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String country;

    @OneToOne
    private City city;

    @Column(length = 30)
    private String quarter;

    @OneToOne
    private Street street;

    @Column(length = 5)
    private String number;

    @Column(length = 5)
    private String floor;

    @Column(length = 5)
    private String apartment;

    @Column
    @Enumerated(EnumType.STRING)
    private AddressType addressType = AddressType.HOME;

    @Column(length = 5)
    private String entrance;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<Comment> comments;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }


    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return PrintUtils.print(this, PrintFlag.PRINT_PRETY);
    }

    public String toString(String flag) {
        return PrintUtils.print(this, PrintFlag.PRINT_PRETY, flag);
    }

    public String printShort() {
        StringBuilder sb = new StringBuilder();
        if (getCity() != null) {
            sb.append(getCity().getName().concat(" "));

            if (getCity().getOblast() != null) {
                sb.append(LocaleUtils.getLocaliziedMessage("common.region.short") + getCity().getOblast().concat(" "));
            }
        }
        if (getStreet() != null) {
            sb.append(LocaleUtils.getLocaliziedMessage("common.street.short").concat(getStreet().toString()).concat(" "));
        }
        if (getNumber() != null) {
            sb.append(LocaleUtils.getLocaliziedMessage("common.No.symbol").concat(getNumber()));
        }

        return sb.toString();
    }

}
