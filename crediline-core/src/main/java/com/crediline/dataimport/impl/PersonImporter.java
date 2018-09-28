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

@Service("personImporter")
public class PersonImporter extends AbstractDataImporter implements DataImporter {

    private EntityManager em;

    private List<RowMapper> collection = new ArrayList<RowMapper>();

    public static Logger logger = Logger.getLogger(PersonImporter.class.getName());

    public PersonImporter() {
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
                        values.put(stripCommonChars(propName), stripCommonChars(attributes[j]));
                        j++;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        values.put(stripCommonChars(propName), "");
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
        Long pid = 0L;
        for (RowMapper rowMapper : getCollection()) {

            incrementId++;
            pid++;
            HashMap<String, String> parameters = (HashMap<String, String>) rowMapper.getValues();

            String personId = "";
            String fullName = "";
            String[] nameParts = null;
            String personEgn = "";
            String addressCity = "";
            String addressOblast = "";
            String addressObshtina = "";
            String addressStreet = "";
            String addressNumber = "";
            String addressEntrance = "";
            String addressFloor = "";
            String addressApartment = "";
            String comment1 = "";
            String comment2 = "";
            String phone = "";
            String creationDate = "";
            String personWorkspace = "";
            String personName = "";
            String personMidname = "";
            String personSurname = "";


            try {
                personId = pid.toString();
                fullName = parameters.get("fullname").trim();
                nameParts = fullName.split("\\s+");

                personName = nameParts[0];
                personMidname = nameParts[1];
                personSurname = "";
                if (nameParts.length == 3) {
                    personSurname = nameParts[2];
                } else if (nameParts.length > 3) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 2; i <= nameParts.length; i++) {
                        sb.append(nameParts[i]);
                        sb.append(" ");
                    }
                    personSurname = sb.toString();
                } else if (nameParts.length < 3) {
                    personSurname = nameParts[nameParts.length - 1];
                }

                personEgn = parameters.get("egn");
                addressCity = parameters.get("city");
                addressOblast = parameters.get("oblast");
                addressObshtina = parameters.get("obshtina");
                addressStreet = parameters.get("street");
                addressNumber = parameters.get("number");
                addressEntrance = parameters.get("entrance");
                addressFloor = parameters.get("floor");
                addressApartment = parameters.get("apartment");
                comment1 = parameters.get("comment1");
                comment2 = parameters.get("comment2");
                phone = parameters.get("phone");
                creationDate = transformDate(parameters.get("creationDate"));
                personWorkspace = parameters.get("workplace").trim().replace("'", "");
            } catch (Exception e) {
                getUnsuccessfulls().put(incrementId, rowMapper);
                logger.warning("ERROR IN ID: " + personId);
            }
            if (!isValidEGN(personEgn)) {
                continue;
            }

            int personRating = 10;
            if (parameters.get("comment1").toLowerCase().contains("забран")) {
                personRating = 0;
            }

            String getCityReferenceSelect = String.format("select id from cities where name='%s' and oblast='%s' and obshtina='%s' limit 1",
                    addressCity, addressOblast, addressObshtina);

            String getStreetReferenceSelect = String.format("select id from streets where name='%s' limit 1",
                    addressStreet);

            String personRow = String.format("insert into persons set" +
                    " id='%s'," +
                    " creation_date='%s', " +
                    " createdBy_id='1'," +
                    " workplace='%s', " +
                    " rating='%s', " +
                    " name='%s'," +
                    " midname='%s'," +
                    " surname='%s'," +
                    " egn='%s'," +
                    " lkNo='%s';",
                    personId,
                    creationDate,
                    personWorkspace,
                    personRating,
                    personName,
                    personMidname,
                    personSurname,
                    personEgn,
                    personEgn);


            String addressRow = String.format("insert into addresses set " +
                    "id='%s'," +
                    " addressType='HOME', " +
                    " creation_date='%s', " +
                    " createdBy_id='1', " +
                    "country='България', " +
                    "city_id=(" + getCityReferenceSelect + "), " +
                    "street_id=(" + getStreetReferenceSelect + "), " +
                    "number='%s', " +
                    "entrance='%s', " +
                    "floor='%s', " +
                    "apartment='%s';", incrementId, creationDate, addressNumber, addressEntrance, addressFloor, addressApartment);

            String personAddressesRow = String.format("insert into persons_addresses set " +
                    "persons_id='%s', " +
                    "addresses_id='%s';", personId, incrementId);

            String commentsRow = String.format("insert into comments (id,value) values ('%s','%s'),('%s','%s');", incrementId, comment1, ++incrementId, comment2);

            Query phoneRowQuery = null;
            Query personPhoneRowQuery = null;

            if (parameters.get("phone").length() > 0) {
                String phoneRow = String.format("insert into phones set" +
                        " id='%s'," +
                        " creation_date='%s', " +
                        " createdBy_id='1', " +
                        " number='%s'," +
                        " phoneType='%s';", incrementId, creationDate, phone, "MOBILE");

                String personPhoneRow = String.format("insert into persons_phones set" +
                        " persons_id='%s'," +
                        " phones_id='%s';", personId, incrementId);

                phoneRowQuery = em.createNativeQuery(phoneRow);
                personPhoneRowQuery = em.createNativeQuery(personPhoneRow);
            }

            String personCommentsRow = String.format("insert into person_comments set person_id='%s', comment_id='%s'", personId, (incrementId - 1));

            String personCommentsRow2 = String.format("insert into person_comments set person_id='%s', comment_id='%s'", personId, incrementId);

            Query personRowQuery = em.createNativeQuery(personRow);
            Query addressRowQuery = em.createNativeQuery(addressRow);
            Query personAddressesRowQuery = em.createNativeQuery(personAddressesRow);
            Query commentsRowQuery = em.createNativeQuery(commentsRow);
            Query personCommentsRowQuery = em.createNativeQuery(personCommentsRow);
            Query personCommentsRow2Query = em.createNativeQuery(personCommentsRow2);

            try {
                em.getTransaction().begin();
                personRowQuery.executeUpdate();
                addressRowQuery.executeUpdate();
                personAddressesRowQuery.executeUpdate();
                commentsRowQuery.executeUpdate();
                personCommentsRowQuery.executeUpdate();
                personCommentsRow2Query.executeUpdate();
                if (parameters.get("phone").length() > 0) {
                    phoneRowQuery.executeUpdate();
                    personPhoneRowQuery.executeUpdate();
                }
                em.getTransaction().commit();
            } catch (Exception e) {
                getUnsuccessfulls().put(incrementId, rowMapper);
                em.getTransaction().rollback();
            }

        }
        return this;
    }

    private String stripCommonChars(String input) {
        return input.trim().replaceAll("\"", "").replaceAll("'", "");
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
