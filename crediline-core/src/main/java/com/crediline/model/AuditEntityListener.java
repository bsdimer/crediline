package com.crediline.model;

import com.crediline.utils.SessionUtils;
import org.joda.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Created by dimer on 7/31/14.
 */
public class AuditEntityListener {

    @PrePersist
    public void prePersist(PersistedVersional e) {
        e.setCreatedBy(SessionUtils.getCurrentUser());
        e.setCreatedIn(SessionUtils.getCurrentUser().getOffice());
        e.setCreationDate(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(PersistedVersional e) {
        e.setModifiedBy(SessionUtils.getCurrentUser());
        e.setModificationDate(LocalDateTime.now());
    }
}
