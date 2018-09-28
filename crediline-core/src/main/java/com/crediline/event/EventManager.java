package com.crediline.event;

import com.crediline.model.Event;
import com.crediline.service.EventService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by dimer on 3/31/14.
 */
@Component
public class EventManager {

    public static Logger logger = Logger.getLogger(EventManager.class.getName());

    @Inject
    private EventService eventService;

    public EventManager() {
    }

    public List<Event> getEvents() {
        return eventService.findAll();
    }

}
