package com.crediline.event;

import com.crediline.model.User;
import org.joda.time.LocalDateTime;

/**
 * Created by dimer on 3/31/14.
 */
public interface IBusinessLogicEvent {
    LocalDateTime getCreationDate();

    User getCreatedBy();

    String getValue();

    BusinessEventTypes getEventType();

}
