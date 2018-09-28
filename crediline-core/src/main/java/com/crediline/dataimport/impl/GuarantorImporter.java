package com.crediline.dataimport.impl;

import com.crediline.dataimport.DataImporter;
import com.crediline.dataimport.RowMapper;
import com.crediline.model.Person;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by dimer on 4/29/14.
 */

@Service("guarantorImporter")
public class GuarantorImporter extends AbstractDataImporter implements DataImporter {

    private EntityManager em;

    private List<RowMapper> collection = new ArrayList<RowMapper>();

    public static Logger logger = Logger.getLogger(GuarantorImporter.class.getName());

    public GuarantorImporter() {
       // em = GenericDao.emf.createEntityManager();
    }

    @Override
    public DataImporter parse(InputStream stream) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        BufferedReader br = null;
        String line = "";

        br = new BufferedReader(new InputStreamReader(stream));
        try {
            int i = 0;
            HashMap<String, String> values = new HashMap<String, String>();
            List<String> descriptionRow = new ArrayList<String>();
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] attributes = line.split(getDelimiter());

                if (i == 0) {
                    descriptionRow = Arrays.asList(attributes);
                    i++;
                    continue;
                }
                int j = 0;
                values = new HashMap<String, String>();
                for (String propName : descriptionRow) {
                    try {
                        values.put(propName, attributes[j]);
                        j++;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        values.put(propName, "");
                    }
                }
                collection.add(new RowMapper(Person.class, values));
            }
            collection.remove(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    @Override
    public List<RowMapper> getCollection() {
        return collection;
    }

    @Override
    public DataImporter persist() {
        Long incrementId = 0L;
        for (RowMapper rowMapper : getCollection()) {

            incrementId++;
            HashMap<String, String> parameters = (HashMap<String, String>) rowMapper.getValues();

            String creditId = "";
            String guarantorId = "";
            String personEgn = "";

            try {
                creditId = parameters.get("creditId").trim().replaceAll("'", "");
                guarantorId = parameters.get("gid").trim();
                personEgn = parameters.get("egn").trim().replaceAll("'", "");
            } catch (Exception e) {
                getUnsuccessfulls().put(incrementId, rowMapper);
                logger.warning("ERROR IN ID: " + incrementId);
            }
            if (!isValidEGN(personEgn)) {
                continue;
            }

            String personIdSelect = String.format("select id from persons where egn='%s' limit 1", personEgn);
            String updateQueryString = String.format("update credits" +
                    " set guarantor%s_id = (%s) where id = '%s';", guarantorId, personIdSelect, creditId);

            Query updateQuery = em.createNativeQuery(updateQueryString);

            try {
                em.getTransaction().begin();
                updateQuery.executeUpdate();
                em.getTransaction().commit();
            } catch (Exception e) {
                getUnsuccessfulls().put(incrementId, rowMapper);
                em.getTransaction().rollback();
            }

        }
        return this;
    }


    private boolean isValidEGN(String egn) {
        if (egn.length() != 10) return false;
        return true;
    }

    private String transformDate(String inputDateTime) {
        DateTimeFormatter outputDateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.US);
        try {
            DateTimeFormatter inputDateTimeFormat = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm").withLocale(Locale.US);
            DateTime inputDate = DateTime.parse(inputDateTime, inputDateTimeFormat);
            return inputDate.toString(outputDateTimeFormat);
        } catch (IllegalArgumentException e) {
            DateTimeFormatter inputDateTimeFormat = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss").withLocale(Locale.US);
            DateTime inputDate = DateTime.parse(inputDateTime, inputDateTimeFormat);
            return inputDate.toString(outputDateTimeFormat);
        }
    }
}
