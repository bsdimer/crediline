package com.crediline.dao;

import com.crediline.model.DocumentTemplate;
import com.crediline.model.DocumentTemplatePurpose;
import org.springframework.stereotype.Repository;

/**
 * Created by dimer on 1/14/14.
 */

@Repository("documentTemplateDao")
public interface DocumentTemplateDao extends CredilineRepository<DocumentTemplate> {
    DocumentTemplate findByName(String name);

    DocumentTemplate findByDocumentTemplatePurpose(DocumentTemplatePurpose documentTemplatePurpose);
}