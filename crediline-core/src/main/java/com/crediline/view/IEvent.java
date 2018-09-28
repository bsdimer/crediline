package com.crediline.view;

import java.util.Map;

/**
 * Created by dimer on 2/23/14.
 */
public interface IEvent {
    String getType();

    Map<String, Object> getParameters();
}
