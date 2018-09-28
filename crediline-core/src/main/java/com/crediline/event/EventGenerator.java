package com.crediline.event;

import com.crediline.model.Event;

/**
 * Created by dimer on 9/10/14.
 */
public interface EventGenerator<T> {
    Event generate(T event);
}
