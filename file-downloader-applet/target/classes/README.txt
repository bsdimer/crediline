------------------------
Ver. 1.0-RELEASE
------------------------

File Downloader Applet is self signed applet which download file in directory created by applet which is located in
user home directory.

To use it we need to add rootCA.cer as trusted certificate in java
For Windows:
- go to Control Panel and open Java(Java Control Panel).
- after opening Java Control Panel, go to Security tab->Manage Certificates
- import rootCA.cer as Signer CA certificate(chose Signer CA from combo box Certificate type

For Linux:
TODO

Prepare applet for using:
- Create folder 'file-downloader-applet' in webapp->resources
- Put file-downloader-applet-*.jar and main.jnlp in file-downloader-applet folder
- Create folder 'js' in  webapp->resources
- Put fileDownloaderApplet.js in js folder
- Import deployJava.js which applet's script using(import it in view or root view when we are going to use applet)
	<script src = "https://www.java.com/js/deployJava.js"></script>
- Import applet's script (fileDownloaderApplet.js)

How to use applet:
- We need to create container for applet in view where we are going to use it.
	For example we can create <div id="applet_container">&#160;</div>
	conteiner's id is important because when we invoke applet we have to specify container
- To download file via applet invoke javascript function - downloadFile('filename.txt', 'applet_container' 'targetDir');
	filename.txt - file for downloading
	applet_container - id of html tag which will used for applet container
	targetDir - This is target directory where to download file. This have to be in user home directory(if target dir is not exist applet will create it)
	File we are going to download have to be in the same directory where are located main.jnlp and jar file.
	In our case file-downloader-applet
	
How to debug applet with Eclipse:
- Go to Java Control Panel(Control Panel->Java)
- Go to Java tab -> View
- Change Runtime parameters
	Change 'suspend=n' to 'suspend=y'
	If there aren't runtime parameters set this:
	-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=y
	When we try to start applet Java will wait until we connect to applet
- To connect to applet after it started go to Debug Configurations in Eclipse and create new Remote Java Application
	Project - file-downloader-applet-0.0.1-SNAPSHOT(chose file downloader applet project)
	host - localhost
	port - 8000
