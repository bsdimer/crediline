package com.crediline.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dimer on 1/4/14.
 */
@Entity
@Table(name = "streets")
public class Street extends PersistedVersional implements Identifiable, Serializable {

    private static final long serialVersionUID = 5064022331112654811L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 100)
    private String name;

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

    @Override
    public String toString() {
        return name;
    }

}
