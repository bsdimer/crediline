package com.crediline.mb;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * Created by dimer on 3/27/14.
 */

@Component("managedBeanTemplate")
@Scope("prototype")
public class ManagedBeanTemplate extends CommonManagedBean implements ITabBean {
    private static final long serialVersionUID = 100000000000000L;

    @PostConstruct
    public void init() {

    }

    @Override
    public Boolean getClosable() {
        return true;
    }

}
