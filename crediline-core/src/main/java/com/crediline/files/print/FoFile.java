package com.crediline.files.print;

import com.crediline.files.FileWriter;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.xmlgraphics.util.MimeConstants;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * Created by dimer on 8/15/14.
 */
public class FoFile extends FileTransformer implements IFileTransform {

    private final String uniqueIndex;
    private final File file;

    public FoFile(String uniqueIndex) {
        this.uniqueIndex = uniqueIndex;
        com.crediline.files.FileWriter fileWriter = new FileWriter();
        file = fileWriter.write(" ", uniqueIndex + ".fo");
    }

    public FoFile(String uniqueIndex, ByteArrayOutputStream content) {
        this.uniqueIndex = uniqueIndex;
        com.crediline.files.FileWriter fileWriter = new FileWriter();
        file = fileWriter.write(content, uniqueIndex + ".fo");
    }

    public FoFile(String uniqueIndex, String content) {
        this.uniqueIndex = uniqueIndex;
        com.crediline.files.FileWriter fileWriter = new FileWriter();
        file = fileWriter.write(" ", uniqueIndex + ".fo");
    }

    public PDFFile convert2PDF() {

        PDFFile pdfFile = new PDFFile(uniqueIndex);
        pdfFile.getFile().getParentFile().mkdir();
        try {
            OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile.getFile()));
            /*FopFactory fopFactory = getFopFactory();
            FOUserAgent userAgent = new FOUserAgent(fopFactory);
            userAgent.getRendererOptions().put(PropertiesUtils.factory().getProperties().get("pdf.print-format.key"),
                    PropertiesUtils.factory().getProperties().get("pdf.print-format.value"));
            Fop fop = getFopFactory().newFop(MimeConstants.MIME_PDF, userAgent, out);*/
            Fop fop = getFopFactory().newFop(MimeConstants.MIME_PDF, out);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(); // identity transformer

            Source src = new StreamSource(file);

            Result res = new SAXResult(fop.getDefaultHandler());

            transformer.transform(src, res);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (FOPException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdfFile;
    }

    @Override
    public File getFile() {
        return file;
    }
}
