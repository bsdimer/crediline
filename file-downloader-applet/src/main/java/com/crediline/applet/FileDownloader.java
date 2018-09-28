package com.crediline.applet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

public class FileDownloader {
	private static final String USER_HOME_PROPERTY_KEY = "user.home";
	private static Logger LOGGER = Logger.getLogger(FileDownloader.class
			.getName());

	public static void downloadFile(final URL url, final String filename, final String targetDir) {
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			public Void run() {
				FileOutputStream fileOutputStream = null;
				try {
					InputStream inputStream = url.openStream();
					String fileToDownload = getPathToTargetDir(targetDir) + "\\"
							+ filename;
					File targetFile = new File(fileToDownload);
					fileOutputStream = new FileOutputStream(targetFile);
					LOGGER.info("Start downloading file:" + url.getFile());
					byte[] array = IOUtils.toByteArray(inputStream);
					fileOutputStream.write(array);
					LOGGER.info("File for print is successfully downloaded in:");
					LOGGER.info(fileToDownload);
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	public static String getPathToTargetDir(String targetDir) throws IOException {
		String homeDirPath = System.getProperty(USER_HOME_PROPERTY_KEY);
		File printerDir = new File(homeDirPath + "\\" + targetDir);
		if (!printerDir.exists()) {
			boolean created = printerDir.mkdir();

			if (!created) {
				throw new IOException("Cannot create printer directory");
			}
		}

		return printerDir.getAbsolutePath();
	}
}
