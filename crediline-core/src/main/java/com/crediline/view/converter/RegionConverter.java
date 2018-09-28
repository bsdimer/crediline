package com.crediline.view.converter;

import com.crediline.dao.RegionDao;
import com.crediline.model.Region;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by dimer on 4/6/14.
 */
@Component("regionConverter")
@Scope("request")
public class RegionConverter extends EntityConverter<Region> {
    @Inject
    private RegionDao regionService;

    public RegionDao getRepository() {
        return regionService;
    }
}
