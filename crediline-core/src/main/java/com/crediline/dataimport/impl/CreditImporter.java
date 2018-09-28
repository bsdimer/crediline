package com.crediline.dataimport.impl;

import com.crediline.dataimport.DataImporter;
import com.crediline.dataimport.RowMapper;
import com.crediline.model.Credit;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dimer on 4/29/14.
 */

@Service("creditImporter")
public class CreditImporter extends AbstractDataImporter implements DataImporter {

    private EntityManager em;

    private List<RowMapper> collection;

    public CreditImporter() {
       // em = GenericDao.emf.createEntityManager();
    }

    @Override
    public DataImporter parse(InputStream stream) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        BufferedReader br = null;
        String line = "";
        collection = new ArrayList<RowMapper>();
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
                collection.add(new RowMapper(Credit.class, values));
            }
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
        ItemStatus itemStatus = ItemStatus.UNKNOWN;
        Long creditIncrements = 100L;
        Long paymentIncrements = 100L;
        Long transactionIncrements = 100L;

        for (RowMapper rowMapper : getCollection()) {

            paymentIncrements++;
            transactionIncrements++;

            HashMap<String, String> parameters = (HashMap<String, String>) rowMapper.getValues();
            String credit_id = "";
            String credit_sum = "";
            String credit_periods = "";
            String credit_interest = "";
            String payment_no = "";
            String ostatuk_glavnica_before_payment = "";
            String payment_base = "";
            String payment_interest = "";
            String payment_sum = "";
            String ostatuk_glavnica_after_payment = "";
            String payment_due_date = "";
            String pickup_date = "";
            String person_id = "";
            String income_sum = "";
            String income_date = "";
            String guarantor1 = "";
            String guarantor2 = "";
            String income_hour = "";
            String office_id = "";
            String office_name = "";
            String person_egn = "";

            try {
                credit_id = parameters.get("credit_id").trim();
                credit_sum = parameters.get("credit_sum").replace(" лв.", "").replace(",", ".").replaceAll("\\s+", "");
                credit_periods = parameters.get("credit_periods").trim();
                credit_interest = convertIntrest((parameters.get("credit_interest").trim()));
                payment_no = parameters.get("payment_no").trim();
                ostatuk_glavnica_before_payment = parameters.get("ostatuk_glavnica_before_payment").replace(" лв.", "").replace(",", ".").replaceAll("\\s+", "");
                payment_base = parameters.get("payment_base").replace(" лв.", "").replace(",", ".").replaceAll("\\s+", "");
                payment_interest = parameters.get("payment_interest").replace(" лв.", "").replace(",", ".").replaceAll("\\s+", "");
                payment_sum = parameters.get("payment_sum").replace(" лв.", "").replace(",", ".").replaceAll("\\s+", "");
                ostatuk_glavnica_after_payment = parameters.get("ostatuk_glavnica_after_payment").replace(" лв.", "").replace(",", ".").replaceAll("\\s+", "");
                payment_due_date = transformDateTime(parameters.get("payment_due_date").trim());
                pickup_date = transformDateTime(parameters.get("pickup_date").trim());
                person_id = parameters.get("person_id").trim();
                income_sum = correctIncomeSum(rowMapper);
                income_date = transformDateTime(parameters.get("income_date").trim());
                // guarantor1 = parameters.get("guarantor1").trim();
                // guarantor2 = parameters.get("guarantor2").trim();
                income_hour = transformDateTime(parameters.get("income_hour").trim());
                // office_id = parameters.get("office_id").trim();
                office_name = parameters.get("office_name").trim();
                person_egn = parameters.get("person_egn").trim();

            } catch (Exception e) {
                getUnsuccessfulls().put(paymentIncrements, rowMapper);
                continue;
            }
            // Check whether it is a start of a new credit

            boolean hasIncome = (!income_sum.equals("0.00"));

            String insertOutcome = "";
            String insertCredit = "";
            String insertPayment = "";
            String insertIncome = "";
            String updateIncome = "";

            String getOfficceReferenceSelect = String.format("select id from offices where name='%s' limit 1",
                    office_name);

            if (payment_no.equals("1")) {
                // Catch new credit
                itemStatus = ItemStatus.NEW_CREDIT;

                creditIncrements++;



                insertOutcome = String.format("insert into transactions set " +
                        "flowType='OUTCOME', " +
                        "reason='CREDIT', " +
                        "paymentMethod='IN_CACHE'," +
                        "id = '%s', " +
                        "sum='%s', " +
                        "createdBy_id ='%s', " +
                        "creation_date = '%s', " +
                        "issuedIn_id =(%s);",
                        transactionIncrements,
                        credit_sum,
                        "1",
                        pickup_date,
                        getOfficceReferenceSelect);


                String personIdSelect = String.format("select id from persons where egn='%s' limit 1", person_egn);

                insertCredit = String.format("insert into credits set " +
                        " id = '%s'," +
                        " creation_date='%s', " +
                        " createdBy_id='1', " +
                        " creditState = '%s'," +
                        " interest = '%s'," +
                        " issuedIn_id = (%s)," +
                        " outcome_id = '%s'," +
                        " period = '%s'," +
                        " person_id = (" + personIdSelect + ")," +
                        " pickup_date = '%s'," +
                        " sum = '%s';",
                        credit_id,
                        pickup_date,
                        "IN_PROGRESS",
                        credit_interest,
                        getOfficceReferenceSelect,
                        transactionIncrements,
                        credit_periods,
                        pickup_date,
                        credit_sum);

                updateIncome = String.format("update transactions set" +
                        " credit_id='%s'" +
                        " where id='%s';", credit_id, transactionIncrements);

                transactionIncrements++;

                insertPayment = String.format("insert into payments set " +
                        " id = '%s'," +
                        " creation_date='%s', " +
                        " createdBy_id='1', " +
                        " basis = '%s'," +
                        " dueDate = '%s'," +
                        " interest = '%s'," +
                        " returnedSum = '%s'," +
                        " credit_id = '%s';",

                        paymentIncrements,
                        pickup_date,
                        payment_base,
                        payment_due_date,
                        payment_interest,
                        income_sum,
                        credit_id
                );


            } else {
                // Catch new payment

                itemStatus = ItemStatus.PAYMENT_IMPORT;

                insertPayment = String.format("insert into payments set " +
                        " id = '%s'," +
                        " creation_date='%s', " +
                        " createdBy_id='1', " +
                        " basis = '%s'," +
                        " dueDate = '%s'," +
                        " interest = '%s'," +
                        " returnedSum = '%s'," +
                        " credit_id = '%s';",

                        paymentIncrements,
                        pickup_date,
                        payment_base,
                        payment_due_date,
                        payment_interest,
                        income_sum,
                        credit_id
                );

            }

            if (hasIncome) {
                insertIncome = String.format("insert into transactions set " +
                        " id = '%s'," +
                        " flowType = 'INCOME', " +
                        " paymentMethod = 'IN_CACHE', " +
                        " reason = 'CREDIT', " +
                        " creation_date='%s', " +
                        " createdBy_id='1', " +
                        " sum = '%s'," +
                        " issuedIn_id = (%s)," +
                        " credit_id = '%s';",

                        transactionIncrements,
                        income_date,
                        income_sum,
                        getOfficceReferenceSelect,
                        credit_id
                );
            }


            try {
                em.getTransaction().begin();

                if (itemStatus.equals(ItemStatus.NEW_CREDIT)) {
                    em.createNativeQuery(insertOutcome).executeUpdate();
                    em.createNativeQuery(insertCredit).executeUpdate();
                    em.createNativeQuery(updateIncome).executeUpdate();
                    em.createNativeQuery(insertPayment).executeUpdate();
                } else if (itemStatus.equals(ItemStatus.PAYMENT_IMPORT)) {
                    em.createNativeQuery(insertPayment).executeUpdate();
                }

                if (hasIncome) {
                    em.createNativeQuery(insertIncome).executeUpdate();
                }

                em.getTransaction().commit();
            } catch (Exception e) {
                getUnsuccessfulls().put(paymentIncrements, rowMapper);
                em.getTransaction().rollback();
            }

        }
        return this;
    }

    private String stripCommonChars(String input) {
        return input.trim().replaceAll("\"", "").replaceAll("'", "");
    }

    private String convertIntrest(String credit_interest) {
        BigDecimal bd = new BigDecimal(credit_interest);
        bd = bd.divide(BigDecimal.valueOf(100d));
        return bd.toString();
    }

    private String correctIncomeSum(RowMapper rowMapper) {
        return rowMapper.getValues().get("income_sum").replace(" лв.", "").replace(",", ".").replaceAll("\\s+", "");
        /*String paymentString = rowMapper.getValues().get("payment_sum").replace(" лв.", "").replace(",", ".").replaceAll("\\s+", "");
        Float income = Float.parseFloat(incomeString);
        Float payment = Float.parseFloat(paymentString);
        Float razlika = payment - income;
        if (razlika != 0.0) {
            return paymentString;
        }
        return incomeString;*/
    }

    private String transformDateTime(String inputDateTime) {
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

enum ItemStatus {
    UNKNOWN, NEW_CREDIT, PAYMENT_IMPORT;
}
