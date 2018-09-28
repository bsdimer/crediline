package com.crediline.mb;

import com.crediline.files.Printer;
import com.crediline.model.Credit;
import com.crediline.model.Document;
import com.crediline.model.DocumentTemplate;
import com.crediline.service.CreditService;
import com.crediline.service.DocumentService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by dimer on 3/27/14.
 */

@Component("testMB")
@Scope("session")
public class TestMB extends CommonManagedBean implements ITabBean {
    private static final long serialVersionUID = 654323323699875392L;

    @Inject
    private CreditService creditService;

    @Inject
    private Printer printer;

    @Inject
    private DocumentService documentService;

    @PostConstruct
    public void init() {

    }

    public void commit() {
        Credit credit = creditService.findOne(258452L);
        creditService.delete(credit);
    }

    @Override
    public Boolean getClosable() {
        return true;
    }

    public void printHTMLTest() {
        DocumentTemplate documentTemplate = getDocumentTemplateService().findOne(1l);
        Document document = new Document();
        document.setSource(documentTemplate.getSource());
        printer.printDocumentHTML(document);
    }
}
