package com.crediline.utils;

import java.io.Serializable;

/**
 * Created by dimer on 3/30/14.
 */
public class Currency implements Serializable {

    private static final long serialVersionUID = -805535436988343952L;
    private String code;
    private String name;
    private String pluralName;

    public Currency(String code, String name, String pluralName) {
        this.code = code;
        this.name = name;
        this.pluralName = pluralName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPluralName() {
        return pluralName;
    }

    public void setPluralName(String pluralName) {
        this.pluralName = pluralName;
    }


    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((code == null) ? 0 : code.hashCode());
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
        final Currency other = (Currency) obj;
        if (other.getCode().equals(this.getCode())
                && other.getName().equals(this.getName())
                && other.getPluralName().equals(this.getPluralName())) {
            return true;
        }
        return false;
    }
}
