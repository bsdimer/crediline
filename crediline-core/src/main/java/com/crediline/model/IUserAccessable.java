package com.crediline.model;

/**
 * Created by dimer on 3/9/14.
 */
public interface IUserAccessable {
    User getCreatedBy();

    void setCreatedBy(User createdBy);


    User getModifiedBy();

    void setModifiedBy(User modifiedBy);
}
