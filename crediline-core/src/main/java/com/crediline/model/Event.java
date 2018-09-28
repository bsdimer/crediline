package com.crediline.model;

import com.crediline.utils.PrintUtils;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dimer on 2/7/14.
 */
@Entity
@Table(name = "events")
public class Event implements Identifiable, Serializable, IDateTimeVersionable, IUserAccessable {

    private static final long serialVersionUID = 3032509639520111502L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String value;

    @Column
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(length = 100)
    private String name;

    @Column(name = "creation_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "modification_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime modificationDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private Office createdIn;

    @ManyToOne(fetch = FetchType.LAZY)
    private User modifiedBy;


    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }


    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }


    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }


    public User getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Office getCreatedIn() {
        return createdIn;
    }

    public void setCreatedIn(Office createdIn) {
        this.createdIn = createdIn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getName() + ": " + getValue();
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
