package com.crediline.mb;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Created by dimer on 3/27/14.
 */

@Component("inquiryPredefinedMB")
@Scope("prototype")
public class InquiryPredefinedMB extends CommonManagedBean implements ITabBean {
    private static final long serialVersionUID = 4564711094798387066L;

    @Override
    public Boolean getClosable() {
        return true;
    }
}
