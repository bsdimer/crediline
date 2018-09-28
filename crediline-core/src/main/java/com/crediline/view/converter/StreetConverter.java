package com.crediline.view.converter;

import com.crediline.dao.CredilineRepository;
import com.crediline.dao.StreetDao;
import com.crediline.model.Street;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by dimer on 4/6/14.
 */
@Component("streetConverter")
@Scope("request")
public class StreetConverter extends EntityConverter<Street> {
    @Inject
    private StreetDao streetService;

    @Override
    protected CredilineRepository<Street> getRepository() {
        return streetService;
    }
}
