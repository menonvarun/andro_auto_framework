# *bot-bot* Runner

---------
##Introduction :

As said earlier **bot-bot** is an native android automation tool. One of the componenets of the **bot-bot** is the **runner**.

Runner is a test exectuion frameowrk based on keyword driven approach. The test cases needs to be defined in “csv” format. These csv files will be converted to java files and then executed using the TestNG framework.

It uses following open-source tools:

1. [Selenium Nativedriver](http://code.google.com/p/nativedriver/)
2. [TestNG](http://testng.org/doc/index.html)
3. [Sikuli](http://sikuli.org/)

**Note:** This tool requires source code of the app that is being automated to be available.
Apps that internally call WebView or load webpages may be able to use this tool.(Not tested)

---------
##Pre-conditions:

1. Android SDK has been installed. More info is available at [link](http://developer.android.com/sdk/installing.html)
2. If you want to use the verifyscreen option in the tool. Please install sikuli on your system. Sikuli can be obtained at: [link](http://sikuli.org/download.shtml)
3. Apache ant needs to be installed.[link](http://ant.apache.org/)

---------
##Installation:

1. Export the bot-bot from git to your local system.
2. Import the source code of the android app that needs to be installed into your eclipse IDE.
3. Copy the "server-standalone.jar" from the bot-bot/runner/lib folder to the root of the Android project inside Eclipse IDE.
4. Open the AndroidManifest.xml file of your Android app and paste the following to it:

	<pre style="font-family: Helvetica, Arial, FreeSans, san-serif; color: #000000; background-color: #eee;font-size: 15px;border: 1px dashed #999999;line-height: 15px;padding: 5px; overflow: auto; width: 100%"><code>&lt;instrumentation android:targetPackage=&quot;{app package name}&quot;
        android:name=&quot;com.google.android.testing.nativedriver.server.ServerInstrumentation&quot; /&gt;
    &lt;uses-permission android:name=&quot;android.permission.INTERNET&quot; /&gt;
    &lt;uses-permission android:name=&quot;android.permission.WAKE_LOCK&quot; /&gt;
    &lt;uses-permission android:name=&quot;android.permission.DISABLE_KEYGUARD&quot; /&gt;
</code></pre>
Here the {app package name} needs to be replaced with the name of the package as specified in the mainfest element's package attribute.

5. Build and install the android app on to the simulator.

6. Go to the android sdk installation folder and inside it to platform-tools. Then run the following commands:
	<pre style="font-family: Helvetica, Arial, FreeSans, san-serif; color: #000000; background-color: #eee;font-size: 15px;border: 1px dashed #999999;line-height: 15px;padding: 5px; overflow: auto; width: 100%"><code> adb shell am instrument {app_package_name}/com.google.android.testing.nativedriver.server.ServerInstrumentation</code></pre>
	
	Here {app_package_name} needs to be replaced with the name of the package as specified in the mainfest element's package attribute.
	
	<pre style="font-family: Helvetica, Arial, FreeSans, san-serif; color: #000000; background-color: #eee;font-size: 15px;border: 1px dashed #999999;line-height: 15px;padding: 5px; overflow: auto; width: 100%"><code>adb forward tcp:54129 tcp:54129</code></pre>

	You can confirm that the instrumentation is started by running adb logcat and looking for a line like this:

	<pre style="font-family: Helvetica, Arial, FreeSans, san-serif; color: #000000; background-color: #eee;font-size: 15px;border: 1px dashed #999999;line-height: 15px;padding: 5px; overflow: auto; width: 100%"><code>I/com.google.android.testing.nativedriver.server.ServerInstrumentation(  273): Jetty started on port 54129</code></pre>

7. Package name and initial activity name has to be defined in the BaseClass.java file. (This will be converted to properties file going forward)
8. Run the ant command at the root of the andro_auto_framework directory:

	<pre style="font-family: Helvetica, Arial, FreeSans, san-serif; color: #000000; background-color: #eee;font-size: 15px;border: 1px dashed #999999;line-height: 15px;padding: 5px; overflow: auto; width: 100%"><code>ant andro-test</code></pre>

	This will compile your code and execute your test cases. Currently TestNG , Junit & TestNG-xslt report are generated for the test execution.

---------

