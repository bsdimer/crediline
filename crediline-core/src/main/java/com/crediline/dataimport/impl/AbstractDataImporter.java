package com.crediline.dataimport.impl;

import com.crediline.dataimport.RowMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dimer on 4/29/14.
 */
public class AbstractDataImporter {

    private String symbol = ";";
    private boolean isQuoted = false;
    private String quotingSymbol = "\"";
    private Map<Long, RowMapper> unsuccessfulls = new HashMap<Long, RowMapper>();

    public void setDelimiter(String symbol) {
        this.symbol = symbol;
    }

    public String getDelimiter() {
        return symbol;
    }

    public boolean isQuoted() {
        return this.isQuoted;
    }

    public void setQuoted(boolean value) {
        this.isQuoted = value;
    }

    public void setQutingSymbol(String symbol) {
        this.quotingSymbol = symbol;
    }

    public String getQutingSymbol() {
        return this.quotingSymbol;
    }


    public Map<Long, RowMapper> getUnsuccessfulls() {
        return unsuccessfulls;
    }

    public void setUnsuccessfulls(Map<Long, RowMapper> unsuccessfulls) {
        this.unsuccessfulls = unsuccessfulls;
    }
}
