package com.crediline.mb;

import com.crediline.event.EventManager;
import com.crediline.model.Event;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * Created by dimer on 3/31/14.
 */
@Component("eventMB")
@Scope("session")
public class EventMB extends CommonManagedBean implements Serializable {
    private static final long serialVersionUID = -2194517194781335633L;

    private Event selectedEvent;

    @Inject
    private EventManager eventManager;


    public List<Event> getEvents() {
        return eventManager.getEvents();
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }
}
