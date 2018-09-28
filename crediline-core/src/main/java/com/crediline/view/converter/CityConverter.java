package com.crediline.view.converter;

import com.crediline.dao.CityDao;
import com.crediline.model.City;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by dimer on 4/6/14.
 */
/*@FacesConverter(value = "cityConverter")*/
@Component("cityConverter")
@Scope("request")
public class CityConverter extends EntityConverter<City> {
    @Inject
    private CityDao cityService;

    public CityDao getRepository() {
        return cityService;
    }
}
