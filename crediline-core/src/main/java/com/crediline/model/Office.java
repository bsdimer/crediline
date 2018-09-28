package com.crediline.model;

import com.crediline.utils.PrintUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dimer on 1/4/14.
 */
@Entity
@Table(name = "offices",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name")
        })
public class Office extends PersistedVersional implements Identifiable, Serializable {

    private static final long serialVersionUID = -4531486161692370175L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Phone> phones = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Region region;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Person manager;

    /**
     * Money order items
     */

    @Column(length = 50)
    private String mopc;

    @Column(length = 50)
    private String mocity;

    @Column(length = 50)
    private String moregion;

    @Column(length = 50)
    private String mooblast;

    @Column(length = 50)
    private String mobank;

    @Column(length = 50)
    private String moap;

    @Column(length = 50)
    private String mophone;

    @Column(length = 50)
    private String motitular;

    @Column(length = 50)
    private String moOfficeManagerNameEn;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void addPhone(Phone phone) {
        if (phones == null) {
            phones = new ArrayList<Phone>();
        }
        if (!phones.contains(phone)) {
            phones.add(phone);
        }
    }

    public Person getManager() {
        return manager;
    }

    public void setManager(Person manager) {
        this.manager = manager;
    }

    public String getMopc() {
        return mopc;
    }

    public void setMopc(String mopc) {
        this.mopc = mopc;
    }

    public String getMocity() {
        return mocity;
    }

    public void setMocity(String mocity) {
        this.mocity = mocity;
    }

    public String getMoregion() {
        return moregion;
    }

    public void setMoregion(String moregion) {
        this.moregion = moregion;
    }

    public String getMooblast() {
        return mooblast;
    }

    public void setMooblast(String mooblast) {
        this.mooblast = mooblast;
    }

    public String getMobank() {
        return mobank;
    }

    public void setMobank(String mobank) {
        this.mobank = mobank;
    }

    public String getMoap() {
        return moap;
    }

    public void setMoap(String moap) {
        this.moap = moap;
    }

    public String getMophone() {
        return mophone;
    }

    public void setMophone(String mophone) {
        this.mophone = mophone;
    }


    public String getMotitular() {
        return motitular;
    }

    public void setMotitular(String mofullname) {
        this.motitular = mofullname;
    }


    @Override
    public String toString() {
        return PrintUtils.print(this);
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }


    public String getMoOfficeManagerNameEn() {
        return moOfficeManagerNameEn;
    }

    public void setMoOfficeManagerNameEn(String moOfficeManagerNameEn) {
        this.moOfficeManagerNameEn = moOfficeManagerNameEn;
    }

}
