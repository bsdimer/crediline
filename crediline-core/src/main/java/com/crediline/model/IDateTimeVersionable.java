package com.crediline.model;

import org.joda.time.LocalDateTime;

/**
 * Created by dimer on 3/9/14.
 */
public interface IDateTimeVersionable {
    LocalDateTime getCreationDate();

    void setCreationDate(LocalDateTime creationDate);

    LocalDateTime getModificationDate();

    void setModificationDate(LocalDateTime modificationDate);
}
