package com.crediline.service;

import com.crediline.dao.DocumentDao;
import com.crediline.model.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by dimer on 2/21/14.
 */

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
@Service("documentService")
public class DocumentService extends AbstractService<Document> {
    @Inject
    private DocumentDao repository;

    public DocumentDao getRepository() {
        return repository;
    }

}
