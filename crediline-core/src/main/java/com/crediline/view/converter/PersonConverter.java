package com.crediline.view.converter;

import com.crediline.dao.PersonDao;
import com.crediline.model.Person;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by dimer on 4/6/14.
 */
@Component("personConverter")
@Scope("request")
public class PersonConverter extends EntityConverter<Person> {
    @Inject
    private PersonDao personService;

    public PersonDao getRepository() {
        return personService;
    }
}
