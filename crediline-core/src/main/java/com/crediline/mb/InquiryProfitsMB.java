package com.crediline.mb;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Created by dimer on 3/27/14.
 */

@Component("inquiryProfitsMB")
@Scope("prototype")
public class InquiryProfitsMB extends CommonManagedBean implements ITabBean {
    private static final long serialVersionUID = -8874184532604352392L;

    @Override
    public Boolean getClosable() {
        return true;
    }
}
