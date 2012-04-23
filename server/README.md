# *bot-bot* Server

-----------------
##Introduction:
**Bot-bot server** is a component of bot-bot automation framework. It is used to record, store and modify the user actions taken on the android app at the user end/system.

Available functionalities:

- View recorded sessions
- View recorded entries for a particular session.

------------

##Download

You can download the latest release Server from the download section mentioned below:

https://github.com/Imaginea/bot-bot/downloads

The server zip contains two files one is a "bot-bot-server.war" and another is "bot-bot-server-standalone.jar". You can read read the "Deploy the war" section below on how to deploy the server using these files.


------------
##Build the war:

To build the bot-bot-server war you need to have maven isntalled in your system. Maven can be downloaded from the ths [link](http://maven.apache.org/download.html).

Once done run the following command at the root of the bot-bot/server folder

<pre style="font-family: Helvetica, Arial, FreeSans, san-serif; color: #000000; background-color: #eee;font-size: 15px;border: 1px dashed #999999;line-height: 15px;padding: 5px; overflow: auto; width: 100%"><code>
$mvn clean install
</code></pre>



------------
## Deploy the war
The war is self contained including the database. 

The war can be deployed in two ways:

1. As a standalone server.

	To deploy the bot-bot server as a standalone server, go to the target folder under the root after running "mvn clean install".
	You will find a file with name "bot-bot-server-standalone.jar". Run the following command in the command prompt after going to the said folder.

	<pre style="font-family: Helvetica, Arial, FreeSans, san-serif; color: #000000; background-color: #eee;font-size: 15px;border: 1px dashed #999999;line-height: 15px;padding: 5px; overflow: auto; width: 100%"><code>
java -jar bot-bot-server-standalone.jar
</code></pre>

	This will start the standalone bot-bot server. The server can be accessed using the url "http://localhost:8080/index.html".

2. On an servlet supported webserver:

	Bot-bot server can be deployed to any of the servlet container like *Apache Tomcat*, *Jboss*, etc.To deploy the war do the following:

	- Create the war as mentioned in the *Build the war* section.
	- Copy the war from the target folder under the bot-bot/server to the webapps folder of your serrvlet container.
	- Start your server.
	- Access the server using the following URL
	
	http://&lt;localhost or systemip&gt;:&lt;port&gt;/bot-bot-server/index.html


	Note: In case you are deploying the war on a servlet container server. You need update the "SERVER_NAME" under the recorder.properties file in the "recorder" section. The recorder is currently configured to record on the standalone bot-bot server deployment.

------------
##Usage

Once recording starts the user can access the sessions on the server:
Following features are supported:

- Viewing the recorded seesions. The server shows all the recorded session on the server. Once user clicks on particular session, the recorded data that particular session will be shown.
- Viewing the recorded records for a session.
- Refresh the session page for a new recorded entries.
- Export as CSV -> Once clicked on "Export as CSV" link, server will give you download option for downloading your test-case as a csv file.
- Edit of an existing record. You can edit any existing recorde to change the command or update any other values.
- Add a new command row. You can add new command row before or after any existing rows to modify your test-case.

----------------
