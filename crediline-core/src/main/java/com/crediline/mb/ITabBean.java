package com.crediline.mb;

/**
 * Created by dimer on 2/25/14.
 */
public interface ITabBean {
    // TODO: This shoulnd't be dicatated by the bean. Move this to TabConfig, probably in applicationContext.xml as configuration
    Boolean getClosable();

    TabViewMB getTabViewMB();

    void setTabViewMB(TabViewMB tabViewMB);
}
