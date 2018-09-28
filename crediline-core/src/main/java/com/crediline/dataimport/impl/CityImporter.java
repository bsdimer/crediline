package com.crediline.dataimport.impl;

import com.crediline.dataimport.DataImporter;
import com.crediline.dataimport.RowMapper;
import com.crediline.model.City;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by dimer on 4/29/14.
 */

@Service("cityImporter")
public class CityImporter extends AbstractDataImporter implements DataImporter {

    private EntityManager em;

    /*@Inject
    private CityService cityService;*/

    private List<RowMapper> collection = new ArrayList<RowMapper>();

    public CityImporter() {
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
                collection.add(new RowMapper(City.class, values));
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
        for (RowMapper rowMapper : getCollection()) {
            String query = "insert into cities set ";
            Iterator iterator = rowMapper.getValues().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                if (entry.getKey().equals("#")) {
                    continue;
                }

                String part1 = entry.getKey() + "='" + entry.getValue() + "'";

                if (iterator.hasNext()) {
                    part1 = part1.concat(",");
                } else {
                    part1 = part1.concat(";");
                }
                query = query.concat(part1);
            }

            Query hibernateQuery = em.createNativeQuery(query);
            try {
                em.getTransaction().begin();
                hibernateQuery.executeUpdate();
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
            }

        }
        return this;
    }


}
