package com.crediline.documents;

import com.crediline.model.Document;
import com.crediline.model.DocumentTemplate;
import com.crediline.model.DocumentTemplatePurpose;
import com.crediline.service.DocumentTemplateService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by dimer on 8/21/14.
 */
@Service("documentFactory")
public class DocumentFactory implements Serializable {

    @Inject
    private DocumentTemplateService documentTemplateService;

    public Document generateDocument(DocumentTemplatePurpose documentTemplatePurpose, Map<String, Object> templateItems) {
        try {
            DocumentTemplate documentTemplate = documentTemplateService.findByDocumentTemplatePurpose(documentTemplatePurpose);
            DocumentCompiler documentCompiler = new DocumentCompiler();

            // Generate document
            return documentCompiler.compile(templateItems, documentTemplate).getDocument();
        } catch (Exception e) {
            Document document = new Document();
            document.setDocumentTemplatePurpose(documentTemplatePurpose);
            document.setSource(e.getMessage());
            return document;
        }
    }

    public Document generateDocument(DocumentTemplate documentTemplate, Map<String, Object> templateItems) {
        try {
            DocumentCompiler documentCompiler = new DocumentCompiler();

            // Generate document
            return documentCompiler.compile(templateItems, documentTemplate).getDocument();
        } catch (Exception e) {
            Document document = new Document();
            document.setDocumentTemplatePurpose(documentTemplate.getDocumentTemplatePurpose());
            document.setSource(e.getMessage());
            return document;
        }
    }
}
