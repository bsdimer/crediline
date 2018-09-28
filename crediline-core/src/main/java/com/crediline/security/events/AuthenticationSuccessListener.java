package com.crediline.security.events;

import com.crediline.event.EventGenerator;
import com.crediline.model.Event;
import com.crediline.model.EventType;
import com.crediline.security.CustomUserDetails;
import com.crediline.service.EventService;
import org.joda.time.LocalDateTime;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.inject.Inject;

/**
 * Created by dimer on 9/10/14.
 */
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent>, EventGenerator<AuthenticationSuccessEvent> {

    public static String EVENT_NAME_SUCCESS = "Login successful";

    @Inject
    EventService eventService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent abstractAuthenticationEvent) {
        Event event = generate(abstractAuthenticationEvent);
        if (event != null) {
            eventService.save(event);
        }
    }

    public Event generate(AuthenticationSuccessEvent event) {
        Event entityEvent = new Event();
        entityEvent.setName(EVENT_NAME_SUCCESS);
        entityEvent.setEventType(EventType.LOGIN_EVENT);
        entityEvent.setCreatedBy(((CustomUserDetails) event.getAuthentication().getPrincipal()).getDomainUser());
        entityEvent.setCreationDate(LocalDateTime.now());

        StringBuilder builder = new StringBuilder();
        builder.append("user: ").append(((CustomUserDetails) event.getAuthentication().getPrincipal()).getUsername());
        builder.append(" from host: ").append(((WebAuthenticationDetails) event.getAuthentication().getDetails()).getRemoteAddress());
        entityEvent.setValue(builder.toString());
        return entityEvent;
    }
}
