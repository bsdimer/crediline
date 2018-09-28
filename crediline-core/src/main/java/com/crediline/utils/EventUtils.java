package com.crediline.utils;

import com.crediline.model.Event;

/**
 * Created by dimer on 3/31/14.
 */
public class EventUtils {

    public static synchronized Event generateEvent(String value) {
        Event event = new Event();
        event.setValue(value);
        return event;
    }
}
