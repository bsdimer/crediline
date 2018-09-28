package com.crediline.files;

import org.primefaces.context.RequestContext;

public class FileDownloader {
	private static final String JS_DOWNLOAD_FUNC_TEMPLATE = "downloadFile('%s', '%s', '%s', '%s')";

	/**
	 * Utility class for using downloader applet to download files from server to local machine.
	 * @param sourceFilename name of the file for download
	 * @param targetFilename name of the target file
	 * @param container in which applet to be located on UI
	 * @param directory location on locale machine where file to be downloaded.
	 * Directory should be in User Home directory if specified directory not exist it will be created.
	 */
	public static void downloadFile(String sourceFilename, String targetFilename, String container, String directory) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute(String.format(JS_DOWNLOAD_FUNC_TEMPLATE, sourceFilename, targetFilename, container, directory));
	}
}
