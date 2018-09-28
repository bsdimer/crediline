package com.crediline.service;

import com.crediline.dao.DocumentTemplateDao;
import com.crediline.model.DocumentTemplate;
import com.crediline.model.DocumentTemplatePurpose;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimer on 2/21/14.
 */

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
@Service("documentTemplateService")
public class DocumentTemplateService extends AbstractService<DocumentTemplate> {
    @Inject
    private DocumentTemplateDao repository;

    public DocumentTemplateDao getRepository() {
        return repository;
    }

    public DocumentTemplate findByName(String name) {
        return repository.findByName(name);
    }

    public DocumentTemplate findByDocumentTemplatePurpose(DocumentTemplatePurpose documentTemplatePurpose) {
        return repository.findByDocumentTemplatePurpose(documentTemplatePurpose);
    }


}
