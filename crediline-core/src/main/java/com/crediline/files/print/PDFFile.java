package com.crediline.files.print;

import java.io.File;

/**
 * Created by dimer on 8/15/14.
 */
public class PDFFile extends FileTransformer implements IFileTransform {
    private final String uniqueIndex;
    private final File file;

    public PDFFile(String uniqueIndex) {
        this.uniqueIndex = uniqueIndex;
        file = new File(generateResourceFilePath(uniqueIndex + ".pdf"));
    }

    @Override
    public File getFile() {
        return file;
    }
}
