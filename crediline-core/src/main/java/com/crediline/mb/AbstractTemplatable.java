package com.crediline.mb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dimer on 4/23/14.
 */
public abstract class AbstractTemplatable extends CommonManagedBean implements Serializable, ITemplatable {
    private static final long serialVersionUID = 4099009209077652321L;



    @Override
    public Map<String, Object> getTemplateItems() {
        Map<String, Object> items = new HashMap<String, Object>();
        return items;
    }

}
