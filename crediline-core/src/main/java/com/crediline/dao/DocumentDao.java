package com.crediline.dao;

import com.crediline.model.Document;
import org.springframework.stereotype.Repository;

/**
 * Created by dimer on 1/14/14.
 */

@Repository("documentDao")
public interface DocumentDao extends CredilineRepository<Document> {
    Document findByName(String name);

}