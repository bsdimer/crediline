package com.crediline.model;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

/**
 * Created by dimer on 3/9/14.
 */
@MappedSuperclass
@EntityListeners(value = {AuditEntityListener.class})
public abstract class PersistedVersional implements Identifiable, IDateTimeVersionable, IUserAccessable {

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
}
