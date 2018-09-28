package com.crediline.files.print;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by dimer on 8/15/14.
 */
public class XHTMLFile extends FileTransformer implements IFileTransform {

    private final String uniqueIndex;
    private final ByteArrayInputStream sourceBuffer;
    private File file;
    private File xslFile;
    private static final String XSL_RELATIVE_WEBPATH = "/resources/xslt/xhtml2fo.xsl";

    public XHTMLFile(String source, String uniqueIndex) {
        this.uniqueIndex = uniqueIndex;
        sourceBuffer = new ByteArrayInputStream(source.getBytes());

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        xslFile = new File(servletContext.getRealPath(XSL_RELATIVE_WEBPATH));
    }

    public FoFile convert2Fo() {
        Source s = new StreamSource(sourceBuffer);
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        Result r = new StreamResult(resultStream);
        try {
            TransformerFactory f = TransformerFactory.newInstance();
            Transformer t = f.newTransformer(new StreamSource(xslFile));
            t.transform(s, r);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return new FoFile(uniqueIndex, resultStream);
    }

    @Override
    public File getFile() {
        return file;
    }
}
