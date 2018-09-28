package com.crediline.model;

import com.crediline.dataimport.CSVEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by dimer on 1/4/14.
 */
/*@Entity
@Table(name = "values")*/
public class RawValue extends PersistedVersional implements Identifiable, Serializable {

    private static final long serialVersionUID = 926403181561267481L;
    private Long id;
    private String name;
    private String value;
    private String description;

    public RawValue(Long id) {
        super();
        this.id = id;
    }

    public RawValue() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @CSVEntity(rowName = "id")
    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final RawValue other = (RawValue) obj;
        if (other.id.equals(this.id)) {
            return true;
        }
        return false;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }
}
