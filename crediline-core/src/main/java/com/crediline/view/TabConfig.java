package com.crediline.view;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.util.Map;

/**
 * Created by dimer on 2/23/14.
 */

public class TabConfig implements BeanFactoryAware {
    private Map<String, String> tabViewMap;
    private BeanFactory beanFactory;


    public void setTabViewMap(Map<String, String> tabViewMap) {
        this.tabViewMap = tabViewMap;
    }

    public Map<String, String> getTabViewMap() {
        return tabViewMap;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public Object getBeanForTabView(String viewName) {
        String beanName = tabViewMap.get(viewName);
        if (beanName == null) {
            beanName = viewName;
        }

        return beanFactory.getBean(beanName);
    }
}
