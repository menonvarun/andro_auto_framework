/*
 * Copyright (C) 2010-2011 Zutubi Pty Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zutubi.android.junitreport;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestListener;

import org.imaginea.botbot.common.DataDrivenTestCase;
import org.xmlpull.v1.XmlSerializer;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Custom test listener that outputs test results to XML files. The files
 * use a similar format to the Ant JUnit task XML formatter, with a few of
 * caveats:
 * <ul>
 *   <li>
 *     By default, multiple suites are all placed in a single file under a root
 *     &lt;testsuites&gt; element.  In multiFile mode a separate file is
 *     created for each suite, which may be more compatible with existing
 *     tools.
 *   </li>
 *   <li>
 *     Redundant information about the number of nested cases within a suite is
 *     omitted.
 *   </li>
 *   <li>
 *     Durations are omitted from suites.
 *   </li>
 *   <li>
 *     Neither standard output nor system properties are included.
 *   </li>
 * </ul>
 * The differences mainly revolve around making this reporting as lightweight as
 * possible. The report is streamed as the tests run, making it impossible to,
 * e.g. include the case count in a &lt;testsuite&gt; element.
 */
public class TestngReportListener implements TestListener {
    private static final String LOG_TAG = "TestNGReportListener";

    private static final String ENCODING_UTF_8 = "utf-8";

    private static final String TAG_SUITES = "testng-results";
    private static final String TAG_SUITE = "suite";
    private static final String TAG_TEST = "test";
    private static final String TAG_GROUPS = "groups";
    private static final String TAG_CLASS = "class";
    private static final String TAG_CASE = "test-method";
    private static final String TAG_ERROR = "error";
    private static final String TAG_FAILURE = "failure";
    private static final String TAG_EXCEPTION = "exception";
    private static final String TAG_MESSAGE = "message";

    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_CLASS = "classname";
    private static final String ATTRIBUTE_TYPE = "type";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String ATTRIBUTE_TIME = "duration-ms";
    private static final String ATTRIBUTE_STATUS = "status";
    private static final String ATTRIBUTE_START_TIME = "started-at";
    private static final String ATTRIBUTE_SIGNATURE = "signature";
    private static final String ATTRIBUTE_FINISHED_AT = "finished-at";
    private static final String ATTRIBUTE_CONFIG = "is-config";
    private static final String ATTRIBUTE_SKIPPED = "skipped";
    private static final String ATTRIBUTE_FAILED = "failed";
    private static final String ATTRIBUTE_TOTAL = "total";
    private static final String ATTRIBUTE_PASSED = "passed";

    // With thanks to org.apache.tools.ant.taskdefs.optional.junit.JUnitTestRunner.
    // Trimmed some entries, added others for Android.
    private static final String[] DEFAULT_TRACE_FILTERS = new String[] {
            "java.lang.reflect.Method.invoke(", "sun.reflect.",
            // JUnit 4 support:
            "org.testng.",
            // Added for Android
            "android.test.", "android.app.Instrumentation",
            "java.lang.reflect.Method.invokeNative",
    };

    private Context mContext;
    private Context mTargetContext;
    private String mReportFile;
    private String mReportDir;
    private boolean mFilterTraces;
    private boolean mMultiFile;
    private FileOutputStream mOutputStream;
    private XmlSerializer mSerializer;
    private String mCurrentSuite;

    // simple time tracking
    private boolean mTimeAlreadyWritten = false;
    private long mTestStartTime;
    private ArrayList<String> tests= new ArrayList<String>();
    private HashMap<String, TestKeeper> testMap= new HashMap<String, TestKeeper>();
    private int errorCount=0;
    private int failureCount=0;

    /**
     * Creates a new listener.
     *
     * @param context context of the test application
     * @param targetContext context of the application under test
     * @param reportFile name of the report file(s) to create
     * @param reportDir  path of the directory under which to write files
     *                  (may be null in which case files are written under
     *                  the context using {@link Context#openFileOutput(String, int)}).
     * @param filterTraces if true, stack traces will have common noise (e.g.
     *            framework methods) omitted for clarity
     * @param multiFile if true, use a separate file for each test suite
     */
    public TestngReportListener(Context context, Context targetContext, String reportFile, String reportDir, boolean filterTraces, boolean multiFile) {
        this.mContext = context;
        this.mTargetContext = targetContext;
        this.mReportFile = reportFile;
        this.mReportDir = reportDir;
        this.mFilterTraces = filterTraces;
        this.mMultiFile = multiFile;
    }
    private String getTestName(Test test){
		String testName;
		if (test instanceof TestCase) {
			TestCase testCase = (TestCase) test;
			if (DataDrivenTestCase.class.isAssignableFrom(test.getClass())) {
				testName = ((DataDrivenTestCase) testCase).getCustomTestName();
				return testName;
			} else {

				return testCase.getName();
			}
		}
		return "";
    }
    @Override
    public void startTest(Test test) {
            if (test instanceof TestCase) {
                TestCase testCase=(TestCase)test;
            	TestKeeper testKeeper= new TestKeeper();
				String testName=getTestName(test);
				testKeeper.setTestname(testName);
				tests.add(testName);
                mTimeAlreadyWritten = false;
                mTestStartTime = System.currentTimeMillis();
                testKeeper.setStartTime(mTestStartTime);
                testKeeper.setTest(testCase);
                testMap.put(testKeeper.getTestname(), testKeeper);
                
            }
    }

    private void checkForNewSuite(TestCase testCase) throws IOException {
        String suiteName = testCase.getClass().getName();
        if (mCurrentSuite == null || !mCurrentSuite.equals(suiteName)) {
            if (mCurrentSuite != null) {
                if (mMultiFile) {
                    closeSuite();
                } else {
                	mSerializer.endTag("", TAG_CLASS);
        			mSerializer.endTag("", TAG_TEST);
        			 mSerializer.endTag("", TAG_SUITE);
                }
            }

            openIfRequired(suiteName);
            mSerializer.startTag("", TAG_SUITE);
            mSerializer.attribute("", ATTRIBUTE_NAME, "Bot-bot Suite");
            mSerializer.attribute("", ATTRIBUTE_TIME, "");
            mSerializer.attribute("", ATTRIBUTE_START_TIME, "");
            mSerializer.attribute("", ATTRIBUTE_FINISHED_AT, "");
            mSerializer.startTag("", TAG_GROUPS);
            mSerializer.endTag("", TAG_GROUPS);
            mSerializer.startTag("", TAG_TEST);
            mSerializer.attribute("", ATTRIBUTE_NAME, "Bot-bot test");
            mSerializer.attribute("", ATTRIBUTE_TIME, "");
            mSerializer.attribute("", ATTRIBUTE_START_TIME, "");
            mSerializer.attribute("", ATTRIBUTE_FINISHED_AT, "");
            mSerializer.startTag("", TAG_CLASS);
            mSerializer.attribute("", ATTRIBUTE_CLASS, suiteName);
            mCurrentSuite = suiteName;
        }
    }

    private void openIfRequired(String suiteName) throws IOException {
        if (mSerializer == null) {
            String fileName = mReportFile;
            if (mMultiFile) {
                fileName = fileName.replace("$(suite)", suiteName);
            }

            if (mReportDir == null) {
                try {
                    mOutputStream = mContext.openFileOutput(fileName, 0);
                } catch(Exception e) {
                    mOutputStream = mTargetContext.openFileOutput(fileName, 0);
                }
            } else {
                mOutputStream = new FileOutputStream(new File(mReportDir, fileName));
            }

            mSerializer = Xml.newSerializer();
            mSerializer.setOutput(mOutputStream, ENCODING_UTF_8);
            mSerializer.startDocument(ENCODING_UTF_8, true);
            if(!mMultiFile){
                mSerializer.startTag("", TAG_SUITES);
                mSerializer.attribute("", "skipped", "0");
                mSerializer.attribute("", ATTRIBUTE_FAILED, String.valueOf(failureCount));
                mSerializer.attribute("", ATTRIBUTE_TOTAL, String.valueOf(tests.size()));
                mSerializer.attribute("", ATTRIBUTE_PASSED, String.valueOf(tests.size()-failureCount));
                mSerializer.startTag("", "reporter-output");
                mSerializer.endTag("", "reporter-output");
            }
        }
    }

    @Override
    public void addError(Test test, Throwable error) {
        addProblem(test,TAG_ERROR, error);
        errorCount++;
    }

    @Override
    public void addFailure(Test test, AssertionFailedError error) {
        addProblem(test,TAG_FAILURE, error);
        failureCount++;
    }

    private void addProblem(Test test,String tag, Throwable error) {
        try {
            recordTestTime(test);
            TestKeeper testKeeper= testMap.get(getTestName(test));
            testKeeper.setError(error);
            testKeeper.setTag(tag);
        } catch (IOException e) {
            Log.e(LOG_TAG, safeMessage(e));
        }
    }

    private void recordTestTime(Test test) throws IOException {
    	if (!mTimeAlreadyWritten) {
            mTimeAlreadyWritten = true;
            TestKeeper testKeeper= testMap.get(getTestName(test));
            testKeeper.setEndTime(System.currentTimeMillis());
        }
    }

    @Override
    public void endTest(Test test) {
        try {
            if (test instanceof TestCase) {
                recordTestTime(test);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, safeMessage(e));
        }
    }
    
    public void close(){
		try {
			for (String testName : tests) {

				String status = "";
				TestKeeper testKeeper = testMap.get(testName);
				TestCase testCase = testKeeper.getTest();
				Log.e(LOG_TAG, "checking for new suite");
				checkForNewSuite(testCase);
				mSerializer.startTag("", TAG_CASE);
				if (testKeeper.isFailed()) {
					status = "FAIL";
				} else {
					status = "PASS";
				}
				mSerializer.attribute("", ATTRIBUTE_STATUS, status);
				mSerializer.attribute("", ATTRIBUTE_SIGNATURE, testName + "["
						+ mCurrentSuite + "]");
				mSerializer.attribute("", ATTRIBUTE_NAME, testName);
				mSerializer.attribute("", ATTRIBUTE_CONFIG, "true");
				mSerializer.attribute(
						"",
						ATTRIBUTE_TIME,
						String.valueOf(testKeeper.getEndTime()
								- testKeeper.getStartTime()));
				mSerializer.attribute("", ATTRIBUTE_START_TIME,
						String.valueOf(testKeeper.getStartTime()));
				mSerializer.attribute("", ATTRIBUTE_FINISHED_AT,
						String.valueOf(testKeeper.getEndTime()));
				if (testKeeper.isFailed()) {
					Throwable error = testKeeper.getError();
					mSerializer.startTag("", TAG_EXCEPTION);
					mSerializer.attribute("", ATTRIBUTE_CLASS, error.getClass()
							.getName());
					mSerializer.startTag("", TAG_MESSAGE);
					mSerializer.text(safeMessage(error));
					mSerializer.endTag("", TAG_MESSAGE);
					mSerializer.startTag("", "full-stacktrace");
					StringWriter w = new StringWriter();
					error.printStackTrace(mFilterTraces ? new FilteringWriter(w)
							: new PrintWriter(w));
					mSerializer.text(w.toString());
					mSerializer.endTag("", "full-stacktrace");
					mSerializer.endTag("", TAG_EXCEPTION);
				}
				mSerializer.endTag("", TAG_CASE);
			}
			mSerializer.endTag("", TAG_CLASS);
			mSerializer.endTag("", TAG_TEST);
		} catch (IOException e) {
			Log.e(LOG_TAG, safeMessage(e));
		}

		closeSuite();
    }
    
    /**
     * Releases all resources associated with this listener.  Must be called
     * when the listener is finished with.
     */
    public void closeSuite() {
        
    	
    	if (mSerializer != null) {
            Log.i(LOG_TAG, mSerializer.toString());
    		try {
                if (mCurrentSuite != null) {
                	Log.i(LOG_TAG, mCurrentSuite);
                    mSerializer.endTag("", TAG_SUITE);
                }

                if (!mMultiFile) {
                    mSerializer.endTag("", TAG_SUITES);
                }
                mSerializer.endDocument();
                mSerializer = null;
            } catch (IOException e) {
                Log.e(LOG_TAG, safeMessage(e));
            }
        }

        if (mOutputStream != null) {
            try {
                mOutputStream.close();
                mOutputStream = null;
            } catch (IOException e) {
                Log.e(LOG_TAG, safeMessage(e));
            }
        }
    }

    private String safeMessage(Throwable error) {
        String message = error.getMessage();
        return error.getClass().getName() + ": " + (message == null ? "<null>" : message);
    }

    /**
     * Wrapper around a print writer that filters out common noise from stack
     * traces, making it easier to see the actual failure.
     */
    private static class FilteringWriter extends PrintWriter {
        public FilteringWriter(Writer out) {
            super(out);
        }

        @Override
        public void println(String s) {
            for (String filtered : DEFAULT_TRACE_FILTERS) {
                if (s.contains(filtered)) {
                    return;
                }
            }

            super.println(s);
        }
    }
}
