package com.crediline.service;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service("resourceService")
public class ResourceService implements ResourceLoaderAware {
    private ResourceLoader resourceLoader;

    public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
    }

    public Resource getResource(String location) {
            return resourceLoader.getResource(location);
    }
}
