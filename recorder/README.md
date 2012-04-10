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

1. Android SDK has been installed. More info is available at [link](http://developer.android.com/sdk/installing.html)
2. Apache ant needs to be installed.[link](http://ant.apache.org/)
3. "apk" file of an app that you want to test.

-----------
##Installation:

1. Set the "ANDROID_HOME" enviornment variable to the Android SDK installation directory on your system ex. "/opt/softwares/android-sdk-linux"
2. Set the tools and platform-tools folder path to your enviornment PATH. ex. "/opt/softwares/android-sdk-linux/tools" & "/opt/softwares/android-sdk-linux/platform-tools"
3. Open *default.properties* file under the recoder folder and replace the value of *TEST_APK_FILENAME* with your test apk file path.
4. Open command prompt and type the command **ant**, this will automatically take your apk file compile it with recorder code, resign it and install it to your emulator or device whichever is running.

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

