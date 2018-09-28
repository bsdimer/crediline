package com.crediline.files;

import com.crediline.files.register.model.IRegisterData;
import com.crediline.service.ResourceService;
import org.springframework.core.io.Resource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import javax.faces.context.FacesContext;
import java.io.*;
import java.util.List;
import java.util.logging.Logger;

public class FileWriter {
    private static final String PATH_TO_TARGET_FILES_DIRECTORY = "resources/file-downloader-applet/";
    private static Logger LOGGER = Logger.getLogger(FileWriter.class.getName());
    private Resource targetFileDirectoryResource;
    private File file;

    public FileWriter() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        ResourceService resourceService = ctx.getBean(ResourceService.class);
        targetFileDirectoryResource = resourceService.getResource(PATH_TO_TARGET_FILES_DIRECTORY);
    }

    public FileWriter(String prefix) {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        ResourceService resourceService = ctx.getBean(ResourceService.class);
        targetFileDirectoryResource = resourceService.getResource(prefix);
    }

    public File write(List<? extends IRegisterData> allRegisterData, String filename) {

        java.io.FileWriter out = null;
        try {
            file = new File(getFilePath(filename));
            out = new java.io.FileWriter(file);

            for (IRegisterData data : allRegisterData) {
                String registerData = data.getData();
                out.write(registerData);
            }
            LOGGER.info("Report created in:");
            LOGGER.info(file.getAbsolutePath());
        } catch (IOException ioe) {
            // STODO
            ioe.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                // STODO
                e.printStackTrace();
            }
        }
        return file;
    }

    public File write(String data, String filename) {

        Writer out = null;
        try {
            file = new File(getFilePath(filename));
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "cp1251"));
            out.write(data);

            LOGGER.info("File created in:");
            LOGGER.info(file.getAbsolutePath());
        } catch (IOException ioe) {
            // STODO
            ioe.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                // STODO
                e.printStackTrace();
            }
        }
        return file;
    }

    public File write(ByteArrayOutputStream data, String filename) {

        Writer out = null;
        try {
            file = new File(getFilePath(filename));
            FileOutputStream fos = new FileOutputStream(file);
            data.writeTo(fos);

            LOGGER.info("File created in:");
            LOGGER.info(file.getAbsolutePath());
        } catch (IOException ioe) {
            // STODO
            ioe.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                // STODO
                e.printStackTrace();
            }
        }
        return file;
    }

    public File getFile() {
        return file;
    }

    private String getFilePath(String filename) throws IOException {
        return targetFileDirectoryResource.getFile().getAbsoluteFile() + "/" + filename;
    }

}
