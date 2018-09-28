package com.crediline.files.print;

import org.apache.fop.apps.FopFactory;
import org.xml.sax.SAXException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by dimer on 8/15/14.
 */
public abstract class FileTransformer {

    protected String generateResourceFilePath(String suffix) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        String relativeWebPath = "/temp/" + suffix;
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        return servletContext.getRealPath(relativeWebPath);
    }

    protected String generateResourcesPath() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        String relativeWebPath = "/resources/";
        ServletContext servletContext = (ServletContext) externalContext.getContext();
        return servletContext.getRealPath(relativeWebPath);
    }

    protected FopFactory getFopFactory() {
        FopFactory fopFactory = FopFactory.newInstance();
        try {
            String resourcesUrl = generateResourcesPath();
            fopFactory.setUserConfig(new File(resourcesUrl + "/fop/fopConfig.xml"));
            fopFactory.getFontManager().setFontBaseURL(resourcesUrl + "/fop/fonts");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fopFactory;
    }

}