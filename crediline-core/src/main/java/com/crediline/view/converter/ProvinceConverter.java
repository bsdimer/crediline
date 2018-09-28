package com.crediline.view.converter;

import com.crediline.dao.ProvinceDao;
import com.crediline.model.Province;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by dimer on 4/6/14.
 */
@Component("provinceConverter")
@Scope("request")
public class ProvinceConverter extends EntityConverter<Province> {
    @Inject
    private ProvinceDao provinceService;

    public ProvinceDao getRepository() {
        return provinceService;
    }
}

