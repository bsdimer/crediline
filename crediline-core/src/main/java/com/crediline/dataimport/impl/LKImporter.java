package com.crediline.dataimport.impl;

import com.crediline.dataimport.DataImporter;
import com.crediline.dataimport.RowMapper;
import com.crediline.model.Person;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
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

@Service("lkImporter")
public class LKImporter extends AbstractDataImporter implements DataImporter {

    private EntityManager em;

    private List<RowMapper> collection = new ArrayList<RowMapper>();

    public static Logger logger = Logger.getLogger(LKImporter.class.getName());

    public LKImporter() {
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

            String personEgn = "";
            String lkNo = "";
            String lkIssue = "";
            String lkDue = "";
            String workplace = "";
            String lkIssueLocation = "";
            LocalDateTime lkissueDate = new LocalDateTime();
            LocalDateTime lkDueDate = new LocalDateTime();

            try {
                lkNo = parameters.get("lkNo").trim();
                personEgn = parameters.get("egn").trim();
                lkIssue = parameters.get("lkIssueDate");
                lkDue = parameters.get("lkDueDate");
                lkIssueLocation = parameters.get("lkIssueLocation");
                workplace = parameters.get("workplace");

                lkissueDate = LocalDateTime.parse(lkIssue, DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss"));
                lkDueDate = LocalDateTime.parse(lkDue, DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss"));


            } catch (Exception e) {
                getUnsuccessfulls().put(incrementId, rowMapper);
                logger.warning("ERROR IN ID: " + incrementId);
            }
            if (!isValidEGN(personEgn)) {
                continue;
            }

            /*String personIdSelect = String.format("select id from persons where egn='%s' limit 1", personEgn);*/
            // String updateQueryString = String.format("update person (lkNo, lkIssueDate, validBefore, lkIssueLocation, workplace) values ('%s', '%s', '%s', '%s', '%s') where id = (%s);", lkNo, lkissueDate.toString(), lkDueDate.toString(), lkIssueLocation, workplace, personIdSelect);
            String updateQueryString = String.format("update persons set lkNo='%s', lkIssueDate='%s', lkvalid_date='%s', lkIssueLocation='%s', workplace='%s' where egn = '%s';", lkNo, lkissueDate.toString(), lkDueDate.toString(), lkIssueLocation, workplace, personEgn);

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
