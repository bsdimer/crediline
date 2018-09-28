package com.crediline.security.events;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;

/**
 * Created by dimer on 9/10/14.
 */
public class ApplicationContextListener implements ApplicationListener<AbstractAuthenticationEvent> {

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {

    }
}
