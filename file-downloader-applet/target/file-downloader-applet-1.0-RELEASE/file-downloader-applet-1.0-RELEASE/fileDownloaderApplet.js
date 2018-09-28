var attributes = {
	id : 'downloader',
	code : 'com.crediline.applet.FileDownloaderApplet.class',
	width : 1,
	height : 1
};

var parameters = {
	jnlp_href : './resources/file-downloader-applet/main.jnlp',
};

var appletIsRunning = false;

function downloadFile(sourceFilename, targetFilename, appletContainer, targetDir) {
	if(!appletIsRunning) {
		runApplet(appletContainer);
	}
	//downloader is applet's instance
	downloader.downloadFile(sourceFilename, targetFilename, targetDir);
}

function runApplet(appletContainer) {
	docWriteWrapper($('#' + appletContainer), function () {
		deployJava.runApplet(attributes, parameters, '1.6');
	});
	
	appletIsRunning = true;
}

function docWriteWrapper(jq, func) {
    var oldwrite = document.write, content = '';
    document.write = function(text) {
        content += text;
    };
    func();
    document.write = oldwrite;
    jq.html(content);
}

