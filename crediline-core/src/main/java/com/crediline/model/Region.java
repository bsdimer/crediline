package com.crediline.model;

import com.crediline.utils.PrintUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dimer on 1/4/14.
 */
@Entity
@Table(name = "regions")
public class Region extends PersistedVersional implements Identifiable, Serializable {

    private static final long serialVersionUID = 3298001811452654742L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    private Person manager;


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


    public Person getManager() {
        return manager;
    }

    public void setManager(Person manager) {
        this.manager = manager;
    }


    @Override
    public String toString() {
        return PrintUtils.print(this);
    }

}
