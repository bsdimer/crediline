package com.crediline.documents;

import com.crediline.mb.ITemplatable;
import com.crediline.model.Document;
import com.crediline.model.DocumentTemplate;
import com.crediline.utils.SessionUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by dimer on 3/12/14.
 */
public class DocumentCompiler implements Serializable {

    private static final String LOG_TAG = "velocity compile";
    VelocityContext vc = new VelocityContext();
    DocumentTemplate documentTemplate;
    private String result;
    private Map<String, Object> entries;
    private ITemplatable templatable;

    public DocumentCompiler(ITemplatable templatable, DocumentTemplate documentTemplate) {
        this.templatable = templatable;
        this.documentTemplate = documentTemplate;
    }

    public DocumentCompiler() {
    }

    public synchronized DocumentCompiler compile(Map<String, Object> entries, DocumentTemplate documentTemplate) {
        this.documentTemplate = documentTemplate;
        this.entries = entries;
        StringWriter vw = new StringWriter();
        for (Map.Entry<String, Object> entry : entries.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            vc.put(key, value);
        }
        Velocity.evaluate(vc, vw, LOG_TAG, documentTemplate.getSource());
        result = vw.toString();
        return this;
    }

    public DocumentCompiler compile() {
        return compile(templatable.getTemplateItems(), documentTemplate);
    }

    public String toString() {
        return result;
    }

    public static DocumentCompiler getCompiler() {
        return new DocumentCompiler();
    }

    public Map<String, Object> getEntries() {
        return entries;
    }

    public DocumentTemplate getDocumentTemplate() {
        return documentTemplate;
    }

    public Document getDocument() {
        Document document = new Document();
        document.setLocation(SessionUtils.getCurrentUser().getOffice());
        document.setName(documentTemplate.getName());
        document.setDocumentTemplatePurpose(documentTemplate.getDocumentTemplatePurpose());
        document.setSource(result);
        return document;
    }

    public ITemplatable getTemplatable() {
        return templatable;
    }
}
