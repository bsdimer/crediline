package com.crediline.security.events;

import com.crediline.event.EventGenerator;
import com.crediline.model.Event;
import com.crediline.model.EventType;
import com.crediline.service.EventService;
import com.crediline.service.UserService;
import org.joda.time.LocalDateTime;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureServiceExceptionEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.inject.Inject;

/**
 * Created by dimer on 9/10/14.
 */
public class AuthenticationFailListener implements ApplicationListener<AuthenticationFailureServiceExceptionEvent>, EventGenerator<AuthenticationFailureServiceExceptionEvent> {

    public static String EVENT_NAME_FAIL = "Login failed";

    @Inject
    EventService eventService;

    @Inject
    UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationFailureServiceExceptionEvent abstractAuthenticationEvent) {
        Event event = generate(abstractAuthenticationEvent);
        if (event != null) {
            eventService.save(event);
        }
    }

    public Event generate(AuthenticationFailureServiceExceptionEvent event) {
        Event entityEvent = new Event();
        entityEvent.setName(EVENT_NAME_FAIL);
        entityEvent.setEventType(EventType.LOGIN_EVENT);
        entityEvent.setCreatedBy(userService.findOne(1l));
        entityEvent.setCreationDate(LocalDateTime.now());

        StringBuilder builder = new StringBuilder();
        builder.append("user: ").append(event.getAuthentication().getPrincipal());
        builder.append(" password: ").append(event.getAuthentication().getCredentials());
        builder.append(" from host: ").append(((WebAuthenticationDetails) event.getAuthentication().getDetails()).getRemoteAddress());
        entityEvent.setValue(builder.toString());
        return entityEvent;
    }
}
