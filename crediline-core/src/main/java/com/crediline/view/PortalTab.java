package com.crediline.view;

import com.crediline.mb.ITabBean;
import com.crediline.mb.TabViewMB;

/**
 * Created by dimer on 2/25/14.
 */
public class PortalTab extends CustomTab implements ITabBean {
    private TabViewMB tabViewMB;



    public PortalTab(String view, String label, TabViewMB tabView) {
        super(view, label, tabView);
    }

    @Override
    public Boolean getClosable() {
        return false;
    }

    @Override
    public TabViewMB getTabViewMB() {
        return this.tabViewMB;
    }

    @Override
    public void setTabViewMB(TabViewMB tabViewMB) {

        this.tabViewMB = tabViewMB;
    }
}
