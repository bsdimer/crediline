package com.crediline.mb;

import com.crediline.view.CustomTab;
import com.crediline.view.TabConfig;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dimer on 1/16/14.
 */
@Component("tabView")
@Scope("session")
public class TabViewMB implements Serializable {
    private static final long serialVersionUID = -7970019847661081454L;

    private static Logger logger = Logger.getLogger(TabViewMB.class.getName());
    private List<CustomTab> tabs = new ArrayList<CustomTab>();
    private Integer activeIndex;
    private Map<String, CustomTab> tabsMap = new HashMap<String, CustomTab>();

    @Inject
    private TabConfig tabConfig;

    public TabViewMB() {
        logger.log(Level.INFO, "TabView initialized");
        /*PortalTab portalTab = new PortalTab("portal", "Портал таб", this);
        tabsMap.put(portalTab.getId(), portalTab);*/
    }

    public void addTab(String id, String label) {
        CustomTab newTab = new CustomTab(id, label, tabConfig.getBeanForTabView(id));

        tabs.add(newTab);
        activeIndex = tabs.size();
        refresh();
    }


    public List<CustomTab> getTabs() {
        return tabs;
    }

    public void onTabClose(TabCloseEvent event) {
        String titleTip = event.getTab().getTitletip();
        for (CustomTab tab : tabs) {
            if (tab.getId().equals(titleTip)) {
                tabs.remove(tab);
                return;
            }
        }
    }


    public void onTabChange(TabChangeEvent event) {
    }

    public void onTabShow(Object event) {
    }

    public Integer getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(Integer activeIndex) {
        this.activeIndex = activeIndex;
    }


    public CustomTab getActiveTab() {
        return tabs.get(activeIndex);
    }

    /**
     * Close tab by Bean instance
     *
     * @param bean
     * @return
     */
    public void closeByBean(Object bean) {
        for (CustomTab tab : tabs) {
            if (tab.getBean() == bean) {
                tabs.remove(tab);
                break;
            }
        }
        refresh();
    }

    public void refresh() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("tabPanel");
    }

    public void close(CustomTab customTab) {

    }
}

