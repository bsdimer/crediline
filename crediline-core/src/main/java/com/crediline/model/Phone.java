package com.crediline.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dimer on 1/4/14.
 */
@Entity
@Table(name = "phones")
public class Phone extends PersistedVersional implements Identifiable, Serializable {

    private static final long serialVersionUID = -5166786656782645948L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number;

    @Column
    @Enumerated(EnumType.STRING)
    private PhoneType phoneType = PhoneType.MOBILE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    @Override
    public String toString() {
        return getNumber();
    }

}
