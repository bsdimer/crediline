package com.crediline.mb;

import com.crediline.model.Document;
import org.primefaces.component.editor.Editor;

/**
 * Created by dimer on 8/27/14.
 */
public class DocumentWrapper {
    private Document document;

    private Editor uicomponent;

    public DocumentWrapper(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Editor getUicomponent() {
        return uicomponent;
    }

    public void setUicomponent(Editor uicomponent) {
        this.uicomponent = uicomponent;
    }

}
