package com.crediline.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dimer on 2/7/14.
 */
@Entity
@Table(name = "comments")
public class Comment extends PersistedVersional implements Identifiable, Serializable {

    private static final long serialVersionUID = -4713715896680743909L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String value;

    public Comment(String value) {
        this.value = value;
    }

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
