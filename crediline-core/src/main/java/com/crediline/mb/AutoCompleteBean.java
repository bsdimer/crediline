package com.crediline.mb;

import com.crediline.model.*;
import com.crediline.model.specifications.PersonNameSpecification;
import com.crediline.service.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimer on 1/20/14.
 */

@Component("autoComplete")
public class AutoCompleteBean implements Serializable {

    @Inject
    private PersonService personService;

    @Inject
    private UserService userService;

    @Inject
    private StreetService streetService;

    @Inject
    private CityService cityService;

    @Inject
    private RegionService regionService;

    @Inject
    private OfficeService officeService;

    @Inject
    private ProvinceService provinceService;

    @Inject
    private DocumentTemplateService documentTemplateService;

    private static final long serialVersionUID = 729813845266293645L;
    // ToDo: countries should be Entity and not only String array.
    private List<String> countries = new ArrayList<>();
    private List<String> citizenships = new ArrayList<>();
    private List<City> cities = new ArrayList<City>();
    private List<Street> streets = new ArrayList<Street>();
    private List<Region> regions = new ArrayList<Region>();
    private List<Office> offices = new ArrayList<Office>();
    private List<Person> persons = new ArrayList<Person>();
    private List<User> users = new ArrayList<User>();
    private List<DocumentTemplate> documentTemplates = new ArrayList<DocumentTemplate>();
    private List<Province> provinces = new ArrayList<Province>();

    public AutoCompleteBean() {
        countries = new ArrayList<>();
        countries.add("България");
        citizenships = new ArrayList<>();
        citizenships.add("българско");
        citizenships.add("чужденец");
    }

    public synchronized List<City> getAllCities() {
        if (cities.size() == 0) {
            cities = cityService.findAll();
        }
        return cities;
    }

    public synchronized List<Region> getAllRegions() {
        return regionService.findAll();
    }

    public synchronized List<User> getAllUsers() {
        return userService.findAll();
    }

    public synchronized List<Office> getAllOffices() {
        return officeService.findAll();
    }

    public synchronized List<Street> getAllStreets() {
        if (streets.size() == 0) {
            streets = streetService.findAll();
        }
        return streets;
    }

    public synchronized List<Province> getAllProvinces() {
        if (provinces.size() == 0) {
            provinces = provinceService.findAll();
        }
        return provinces;
    }

    public synchronized List<Person> getAllPersons() {
        if (persons.size() == 0) {
            persons = personService.findAll();
        }
        return persons;
    }

    public synchronized List<DocumentTemplate> getAllDocumentTemplates() {
        if (documentTemplates.size() == 0) {
            documentTemplates = documentTemplateService.findAll();
        }
        return documentTemplates;
    }

    public synchronized void refreshCities() {
        cities = cityService.findAll();
    }

    public synchronized void refreshRegions() {
        regions = regionService.findAll();
    }

    public synchronized void refreshStreets() {
        streets = streetService.findAll();
    }

    public synchronized void refreshProvinces() {
        provinces = provinceService.findAll();
    }

    public synchronized void refreshPersons() {
        persons = personService.findAll();
    }

    public synchronized void refreshOffices() {
        offices = officeService.findAll();
    }

    public synchronized void refreshUsers() {
        users = userService.findAll();
    }

    public synchronized void refreshDocumentTemplates() {
        documentTemplates = documentTemplateService.findAll();
    }

    public synchronized List<Person> autoCompleteEGN(String egn) {
        return personService.findByEgnLike(egn, new PageRequest(0, 10));
    }

    public synchronized List<City> autoCompleteCities(String query) {
        query = query.trim();
        if (query.length() > 0) {
            return cityService.findByNameLike(query + "%", new PageRequest(0, 10));
        }
        return new ArrayList<City>();
    }

    public synchronized List<Office> autoCompleteOffices(String query) {
        query = query.trim();
        if (query.length() > 0) {
            return officeService.findByNameLike(query + "%", new PageRequest(0, 10));
        }
        return new ArrayList<Office>();
    }

    public synchronized List<Street> autoCompleteStreets(String query) {
        query = query.trim();
        if (query.length() > 0) {
            return streetService.findByNameLike(query + "%", new PageRequest(0, 10));
        }
        return new ArrayList<Street>();
    }

    public synchronized List<Province> autoCompleteProvinces(String query) {
        query = query.trim();
        if (query.length() > 0) {
            return provinceService.findByNameLike(query + "%", new PageRequest(0, 10));
        }
        return new ArrayList<Province>();
    }

    public synchronized List<String> autoCompleteCountries(String query) {
        List<String> results = new ArrayList<String>();

        for (String country : countries) {
            if (country.startsWith(query)) {
                results.add(country);
            }
        }

        return results;
    }

    public synchronized List<String> autoCompleteCitizenships(String query) {
        List<String> results = new ArrayList<String>();

        for (String citizenship : citizenships) {
            if (citizenship.startsWith(query)) {
                results.add(citizenship);
            }
        }

        return results;
    }

    public synchronized List<Person> autoCompletePersonNames(String query) {
        return personService.findByFullName(query, 10);
    }

    public synchronized List<Person> autoCompletePerson(String query) {
        query = query.trim();
        if (query.length() > 0) {
            if (Character.isDigit(query.charAt(0))) {
                return personService.findByEgnLikeEager(query, new PageRequest(0, 10));
            }
            return personService.findAll(new PersonNameSpecification(query));
        }
        return new ArrayList<Person>();
    }
}
