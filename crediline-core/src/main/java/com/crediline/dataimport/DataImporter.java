package com.crediline.dataimport;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by dimer on 4/29/14.
 */
public interface DataImporter {

    public void setDelimiter(String symbol);

    public String getDelimiter();

    public boolean isQuoted();

    public void setQuoted(boolean value);

    public void setQutingSymbol(String symbol);

    public String getQutingSymbol();

    public DataImporter parse(InputStream stream) throws IllegalAccessException, InvocationTargetException, InstantiationException;

    public List<RowMapper> getCollection();

    public DataImporter persist();

    public Map<Long, RowMapper> getUnsuccessfulls();
}
