package com.crediline.files.print;

import com.crediline.files.FileWriter;
import com.crediline.utils.SessionUtils;

import java.io.File;
import java.util.UUID;

public class PDFFileCreator {

    private final String sessionId;
    private final String uuid;
    private FileWriter fileWriter;
    private String source;
    private PDFFile pdfFile;
    private FoFile foFile;
    private XHTMLFile xhtmlFile;

    public PDFFileCreator(String source) {
        this.source = source;
        this.sessionId = SessionUtils.getSessionId();
        fileWriter = new FileWriter();
        uuid = UUID.randomUUID().toString();
    }

    public File createPDFFile() {
        // Extract XHTML
        xhtmlFile = new XHTMLFile(source, uuid + "_" + sessionId);

        // Generate Fo
        foFile = xhtmlFile.convert2Fo();

        // Generate PDF
        pdfFile = foFile.convert2PDF();
        return pdfFile.getFile();
    }

}