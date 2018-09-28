package com.crediline.mb;

import com.crediline.dao.DocumentTemplateDao;
import com.crediline.files.print.PDFFileCreator;
import com.crediline.model.DocumentTemplate;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.*;
import java.util.List;

/**
 * Created by dimer on 1/17/14.
 */

/**
 * Must be crated in custom scope which is alive along with wizard creation and destruction
 */
@Component("documentTemplateMB")
@Scope("session")
public class DocumentManagementMB implements Serializable, ITabBean {
    private static final long serialVersionUID = 9207140712207075569L;

    @Inject
    private DocumentTemplateDao documentTemplateService;

    @Inject
    private AutoCompleteBean autoCompleteBean;

    private DocumentTemplate selectedTemplate;
    private StreamedContent pdf;


    public Boolean getClosable() {
        return true;
    }

    @Override
    public TabViewMB getTabViewMB() {
        return null;
    }

    @Override
    public void setTabViewMB(TabViewMB tabViewMB) {

    }

    public void setSelectedTemplate(DocumentTemplate value) {
        if (value != null) {
            this.selectedTemplate = value;
        }
    }

    public DocumentTemplate getSelectedTemplate() {
        return this.selectedTemplate;
    }


    public DocumentTemplateDao getDocumentTemplateService() {
        return documentTemplateService;
    }

    public void createTemplate() {
        try {
            DocumentTemplate dt = new DocumentTemplate();
            dt.setName("New Template");
            getDocumentTemplateService().save(dt);
            refresh();
            selectedTemplate = dt;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Cannot save:" + e.getMessage()));
        }
    }

    public void deleteSelectedTemplate() {
        if (selectedTemplate != null) {
            getDocumentTemplateService().delete(selectedTemplate);
            refresh();
        }
        refresh();
        selectedTemplate = null;
    }

    public void saveSelectedTemplate() {
        try {
            if (selectedTemplate != null) {
                getDocumentTemplateService().save(selectedTemplate);
                refresh();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Cannot save:" + e.getMessage()));
        }

    }

    /*public String getTemplateSource() {
        if (getSelectedTemplate() != null) {
            return selectedTemplate.getSource();
        }
        return tts;
    }

    public void setTemplateSource(String value) {
        if (getSelectedTemplate() != null) {
            selectedTemplate.setSource(value);
        }
        tts = value;
    }*/

    public void onEdit(RowEditEvent event) {
        try {
            getDocumentTemplateService().save((DocumentTemplate) event.getObject());
            refresh();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Cannot save:" + e.getMessage()));
        }
    }

    public void onCancel(RowEditEvent event) {
    }

    public void onSelect(SelectEvent event) {
        selectedTemplate = (DocumentTemplate) event.getObject();
    }


    public List<DocumentTemplate> getAllDocumentTemplates() {
        return autoCompleteBean.getAllDocumentTemplates();
    }

    public void refresh() {
        autoCompleteBean.refreshDocumentTemplates();
    }

    public void onTemplateSourceChange(ValueChangeEvent event) {

    }

    public void setPdf(StreamedContent pdf) {
        this.pdf = pdf;
    }

    public StreamedContent getPdf() throws FileNotFoundException {
        PDFFileCreator pdfFileCreator = new PDFFileCreator(selectedTemplate.getSource());
        File file = pdfFileCreator.createPDFFile();
        FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
        StreamedContent sc = new DefaultStreamedContent(fileInputStream, "application/pdf", file.getName());
        return sc;
    }
}

