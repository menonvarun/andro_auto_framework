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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.imaginea.botbot.common.BotBotTestRunner;
import org.imaginea.botbot.common.DataDrivenTestCase;
import org.imaginea.botbot.utility.DataDrivenDataGenerator;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

import android.os.Bundle;
import android.test.AndroidTestRunner;
import android.test.InstrumentationTestRunner;
import android.util.Log;

/**
 * Custom test runner that adds a {@link JUnitReportListener} to the underlying
 * test runner in order to capture test results in an XML report. You may use
 * this class in place of {@link InstrumentationTestRunner} in your test
 * project's manifest, and/or specify it to your Ant build using the test.runner
 * property.
 * <p/>
 * This runner behaves identically to the default, with the added side-effect of
 * producing JUnit XML reports. The report format is similar to that produced
 * by the Ant JUnit task's XML formatter, making it compatible with existing
 * tools that can process that format. See {@link JUnitReportListener} for
 * further details.
 * <p/>
 * This runner accepts the following arguments:
 * <ul>
 *   <li>
 *     reportFile: name of the file(s) to write the XML report to (default:
 *     junit-report.xml or junit-report-$(suite).xml depending on the value of
 *     multiFile).  May contain $(suite), which will be replaced with the test
 *     suite name when using multiFile mode.  See the reportDir argument for
 *     discussion of the file location.
 *   </li>
 *   <li>
 *     reportDir: if specified, absolute path to a directory in which to write
 *     the report file(s) (default: unset, in which case files are written to
 *     the test application's data area if possible, or the application under
 *     test's data area if that fails).
 *   </li>
 *   <li>
 *     multiFile: if true, write a separate XML file for each test suite;
 *     otherwise include all suites in a single XML file (default: false).
 *   </li>
 *   <li>
 *     filterTraces: if true, stack traces in test failure reports will be
 *     filtered to remove noise such as framework methods (default: true)
 *   </li>
 * </ul>
 * These arguments may be specified as follows:
 *
 * <pre>
 * {@code adb shell am instrument -w -e reportFile my-report-file.xml}
 * </pre>
 */
public class JUnitReportTestRunner extends InstrumentationTestRunner {
    /**
     * Name of the report file(s) to write, may contain $(suite) in multiFile mode.
     */
    private static final String ARG_REPORT_FILE = "reportFile";
    /**
     * If specified, path of the directory to write report files to.  If not set the files are
     * written to the test application's data area.
     */
    private static final String ARG_REPORT_DIR = "reportDir";
    /**
     * If true, stack traces in the report will be filtered to remove common noise (e.g. framework
     * methods).
     */
    private static final String ARG_FILTER_TRACES = "filterTraces";
    /**
     * If true, produce a separate file for each test suite.  By default a single report is created
     * for all suites.
     */
    private static final String ARG_MULTI_FILE = "multiFile";
    /**
     * Default name of the single report file.
     */
    private static final String DEFAULT_SINGLE_REPORT_FILE = "junit-report.xml";
    /**
     * Default name pattern for multiple report files.
     */
    private static final String DEFAULT_MULTI_REPORT_FILE = "junit-report-$(suite).xml";

    private JUnitListener mListener;
    private String mReportFile;
    private String mReportDir;
    private boolean mFilterTraces = true;
    private boolean mMultiFile = true;

    @Override
    public void onCreate(Bundle arguments) {
        if (arguments != null) {
            mReportFile = arguments.getString(ARG_REPORT_FILE);
            mReportDir = arguments.getString(ARG_REPORT_DIR);
            mFilterTraces = getBooleanArgument(arguments, ARG_FILTER_TRACES, true);
            mMultiFile = getBooleanArgument(arguments, ARG_MULTI_FILE, false);
        }

        if (mReportFile == null) {
            mReportFile = mMultiFile ? DEFAULT_MULTI_REPORT_FILE : DEFAULT_SINGLE_REPORT_FILE;
        }
        super.onCreate(arguments);
    }

    private boolean getBooleanArgument(Bundle arguments, String name, boolean defaultValue)
    {
        String value = arguments.getString(name);
        if (value == null) {
            return defaultValue;
        } else {
            return Boolean.parseBoolean(value);
        }
    }

	/** you can subclass and override this if you want to use a different TestRunner */
	protected AndroidTestRunner makeAndroidTestRunner() {
		//setting context so data generator can read files from assets
		DataDrivenDataGenerator.setContext(getContext());
		return new BotBotTestRunner(getTargetContext());
	}
	
    @Override
    protected AndroidTestRunner getAndroidTestRunner() {
        AndroidTestRunner runner = makeAndroidTestRunner();
        mListener = new JUnitListener(getContext(), getTargetContext(), "junit-report-$(suite).xml", mReportDir, mFilterTraces, true);
        runner.addTestListener(mListener);
        return runner;
    }

    @Override
    public void finish(int resultCode, Bundle results) {
        if (mListener != null) {
            mListener.close();
        }

        super.finish(resultCode, results);
    }

	private List<Class<?>> classLister(Class<?> filteringclass) {
		// In theory, the class loader is not required to be a PathClassLoader
		PathClassLoader classLoader = (PathClassLoader) getTargetContext().getClassLoader();
		Field field = null;
		ArrayList<Class<?>> candidates = new ArrayList<Class<?>>();

		try {
			field = PathClassLoader.class.getDeclaredField("mDexs");
			field.setAccessible(true);
		} catch (Exception e) {
			// nobody promised that this field will always be there
			Log.e("logging", "Failed to get mDexs field", e);
		}

		DexFile[] dexFile = null;
		try {
			dexFile = (DexFile[]) field.get(classLoader);
		} catch (Exception e) {
			Log.e("logging", "Failed to get DexFile", e);
		}

		for (DexFile dex : dexFile) {
			Enumeration<String> entries = dex.entries();
			while (entries.hasMoreElements()) {
				// Each entry is a class name, like "foo.bar.MyClass"
				String entry = entries.nextElement();

				// Load the class
				Class<?> entryClass = dex.loadClass(entry, classLoader);
				if (entryClass != null
						&& filteringclass.isAssignableFrom(entryClass)) {
					Log.d("logging", "Found: " + entryClass.getName());
					candidates.add(entryClass);
				}
			}
		}

		return candidates;
	}

	private TestSuite createTestSuite() {
		TestSuite suite = new TestSuite();
		List<Class<?>> classList = classLister(DataDrivenTestCase.class);
		List<Class<?>> dataCases = new ArrayList<Class<?>>();
		for (Class<?> cls : classList) {
			boolean isDataDriven = false;
			if (DataDrivenTestCase.class.isAssignableFrom(cls)) {
				Method method;
				try {
					method = cls.getMethod("suite", new Class[] {});
					if (Modifier.isStatic(method.getModifiers())) {
						isDataDriven = true;
					}
				} catch (SecurityException e) {
					// Neglecting as in case of exception we will not consider
					// the said class for data-driven
				} catch (NoSuchMethodException e) {
					// Neglecting as in case of exception we will not consider
					// the said class for data-driven
				}
			}
			if (isDataDriven) {
				dataCases.add(cls);
			}
		}
		for (Class<?> dataCls : dataCases) {
			try {
				Method method = dataCls.getMethod("suite", new Class[] {});
				Object dataSuite = method.invoke(new Object(), new Object[] {});
				suite.addTest((Test) dataSuite);
			} catch (Exception e) {

			}
		}

		return suite;
	}
}
