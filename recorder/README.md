# *bot-bot* Recorder

----
##Introduction:
As said earlier **bot-bot** is an native android automation tool. One of the components of the **bot-bot** is the **recorder**.

**Recorder** is used to record user actions on the android app under test and send these actions to the **bot-bot server**.
Recorder uses AspectJ for recording user actions.

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
4. Once your android project is converted to AspectJ, copy the recorder forlder from the bot-bot source code to the root of the android project.
5. Select *src* & *aspects* folder under the recorder folder , Right click -> Build-path -> Use as source folder.
6. After this compile your android app project and run it as an Android application.

**Note: ** Currently *Recorder* will only record user events if the server is running in the same machine where the android simulator is running.

---------

