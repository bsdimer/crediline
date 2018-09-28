package com.crediline.model;

import com.crediline.event.BusinessEventTypes;
import com.crediline.event.IBusinessLogicEvent;
import org.joda.time.LocalDateTime;

import java.io.Serializable;

/**
 * Created by dimer on 3/31/14.
 */
public class LoggerEvent implements IBusinessLogicEvent, Identifiable, Serializable {

    private static final long serialVersionUID = 9080573067517262522L;

    private Long id;
    private LocalDateTime creationDate;
    private User createdBy;
    private String value;
    private BusinessEventTypes eventType;

    public LoggerEvent() {
        creationDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (getId() != null) {
            sb.append(getId().toString().concat(", "));
        }

        if (getCreationDate() != null) {
            sb.append(getCreationDate().toString().concat(", "));
        }

        if (getCreatedBy() != null) {
            sb.append(getCreatedBy().toString().concat(", "));
        }

        if (getValue() != null) {
            sb.append(getValue().concat(", "));
        }

        return sb.toString();
    }

    public BusinessEventTypes getEventType() {
        return eventType;
    }

    public void setEventType(BusinessEventTypes eventType) {
        this.eventType = eventType;
    }

    public static synchronized LoggerEvent fromEventEntity(Event event) {
        LoggerEvent loggerEvent = new LoggerEvent();
        loggerEvent.setId(event.getId());
        loggerEvent.setValue(event.getValue());
        return loggerEvent;
    }
}
