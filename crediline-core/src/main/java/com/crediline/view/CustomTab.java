package com.crediline.view;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class CustomTab implements Serializable {
    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(CustomTab.class.getName());

    private String label;
    private String view;
    private String url;
    private String id;
    private Map<String, Object> parameters;
    private Object bean;

    public CustomTab(String view, String label, Object bean) {
        this.id = UUID.randomUUID().toString();
        this.view = view;
        this.url = "./" + view + ".xhtml";
        this.label = label;
        this.bean = bean;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getView() {
        return view;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
