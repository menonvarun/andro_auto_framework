# *bot-bot* Recorder

----
##Introduction:
As said earlier **bot-bot** is an native android automation tool. One of the components of the **bot-bot** is the **recorder**.

**Recorder** is used to record user actions on the android app under test and send these actions to the **bot-bot server**.
Recorder uses AspectJ for recording user actions.

-----------
##Download

You can download the latest release recorder from the download section mentioned below:

https://github.com/Imaginea/bot-bot/downloads

----------
##Pre-conditions:

1. Eclipse is installed on the system.Eclipse can be downloaded from the [link](http://www.eclipse.org/downloads/)
2. AspectJ plugin for eclipse has been installed in eclipse. One of the plugin can found [here](http://www.eclipse.org/ajdt/)
3. Application that needs to be tested source code is available.

-----------
##Installation:

1. Export the bot-bot from git to your local system.
2. Import the source code of the android app that needs to be tested into your eclipse IDE.
3. Right click on the imported android app project -> Configure -> Click on "Convert to AspectJ project".
4. Once your android project is converted to AspectJ, copy the recorder folder from the bot-bot source code to the root of the android project.
5. Select *src* & *aspects* folder under the recorder folder , Right click -> Build-path -> Use as source folder.
6. Copy the "recorder.properties" file from the recorder folder to the "assets" folder of your android source code.
6. After this compile your android app project and run it as an Android application.

**Note: ** Currently *Recorder* will only record user events if the server is running in the same machine where the android simulator is running.

---------
##Configuration:

You can configure the recording server details for recorder to record its event in the "recorder.properties" file.
Following are the available configuration:

- SERVER_IP -> Recording server IP. Default is 10.0.2.2(localhost). In case the server is not on localhost change it to the server IP. Make sure that the android device is able to connect to the said IP.
- SERVER_PORT -> Port on which the server is running. Default is 8080.
- SESSION_NAME -> Session name that the recroder uses to create the session on the server.
- SERVER_NAME -> Server name . Used in case the server is deployed on a servelet container server like Apache,Jboss. Default its not set and assumes that the user is using the standalone mode of the server.

-----------------

