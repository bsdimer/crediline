package com.crediline.mb;

import java.util.Map;

/**
 * Created by dimer on 3/25/14.
 * <p/>
 * Used with template engine tools. It provides the interface which can be used for building Document from template
 */

public interface ITemplatable {
    /**
     * Return the attribute names which can be used when compiling the template
     *
     * @return This map consist of itemName and description
     */
    /*Map<String, String> getTemplateItemNames();*/

    /**
     * @return complete map of all items
     */
    Map<String, Object> getTemplateItems();
}
