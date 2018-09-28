package com.crediline.documents;

import com.crediline.dao.DocumentDao;
import com.crediline.model.Document;

import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by dimer on 3/4/14.
 */
@FacesConverter(value = "document")
public class DocumentTypeConverter implements Converter {

    @ManagedProperty("#{documentService}")
    private DocumentDao documentService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return documentService.findOne(Long.valueOf(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o == null || o.equals("")) {
            return "";
        } else {
            return String.valueOf(((Document) o).getId());
        }
    }

    public DocumentDao getDocumentService() {
        return documentService;
    }

    public void setDocumentService(DocumentDao documentService) {
        this.documentService = documentService;
    }
}
