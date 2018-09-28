package com.crediline.mb;

import com.crediline.model.Document;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by dimer on 7/17/14.
 */
@Component("documentDetailsMB")
@Scope("session")
public class DocumentDetailMB extends CommonManagedBean {
    private Document selectedDocument = new Document();

    public Document getSelectedDocument() {
        return selectedDocument;
    }

    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }

    public void printDocument(Document document) {
        this.selectedDocument = document;
    }
}
