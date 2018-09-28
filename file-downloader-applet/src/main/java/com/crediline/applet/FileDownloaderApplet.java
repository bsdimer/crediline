package com.crediline.applet;

import java.applet.Applet;
import java.net.MalformedURLException;
import java.net.URL;

public class FileDownloaderApplet extends Applet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -92900006211121844L;

	public void downloadFile(String sourceFilename, String targetFilename, String targetDir) {
		URL url = null;
		try {
			url = new URL(getCodeBase(), sourceFilename);
		} catch (MalformedURLException e) {
		}

		FileDownloader.downloadFile(url, targetFilename, targetDir);
	}
}
