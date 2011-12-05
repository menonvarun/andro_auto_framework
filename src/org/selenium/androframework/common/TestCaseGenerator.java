package org.selenium.androframework.common;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.selenium.androframework.api.DefaultProperties;

public class TestCaseGenerator {
	HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();

	public void listDirectory(File f, String directoryName,
			HashMap<String, ArrayList<String>> fileList) {

		File[] listOfFiles = f.listFiles();
		if (directoryName.equalsIgnoreCase("") && f.isDirectory()) {
			directoryName = f.getPath().split("resources/")[1];
		} else if (directoryName.equalsIgnoreCase("") && f.isFile()) {
			directoryName = new File(f.getParent()).getPath().split(
					"resources/")[1];
		}
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()
					&& listOfFiles[i].getName().endsWith(".csv")
					&& !listOfFiles[i].getName().startsWith(".")) {
				if (fileList.containsKey(directoryName)) {
					fileList.get(directoryName).add(
							listOfFiles[i].getAbsolutePath());
				} else {
					ArrayList<String> ar = new ArrayList<String>();
					ar.add(listOfFiles[i].getAbsolutePath());
					fileList.put(directoryName, ar);
				}
			} else if (listOfFiles[i].isDirectory()) {

				listDirectory(listOfFiles[i],
						listOfFiles[i].getPath().split("resources/")[1],
						fileList);
			}
		}
	}

	public void listDirectory1(File f, ArrayList<File> fileList) {
		File[] listOfFiles = f.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				fileList.add(listOfFiles[i]);
			} else if (listOfFiles[i].isDirectory()) {
				listDirectory1(listOfFiles[i], fileList);
			}
		}
	}

	public void classGenerator(HashMap<String, ArrayList<String>> hm)
			throws Exception {
		Iterator<String> it = hm.keySet().iterator();

		int count = 0;
		while (it.hasNext()) {
			String classPath = it.next();
			String packagePath = classPath.toLowerCase();
			String javaClassFile = new File(packagePath).getName();
			String javaClassName = javaClassFile.substring(0, 1).toUpperCase()
					+ javaClassFile.substring(1);
			count++;
			new File("src/test/" + packagePath).mkdirs();
			Writer output = new BufferedWriter(new FileWriter("src/test/"
					+ packagePath + "/" + javaClassName + ".java"));
			FileInputStream fstream = new FileInputStream(
					"resources/TestTemplate.java");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			output.write("package test." + packagePath.replace("/", ".")
					+ ";\n");
			for (int i = 0; i < 5; i++) {
				if ((strLine = br.readLine()) != null) {
					output.write(strLine + "\n");
				}
			}

			output.write("public class " + javaClassName
					+ " extends BaseClass{\n");
			ArrayList<String> ar = hm.get(classPath);
			Iterator<String> arrayIt = ar.iterator();
			while (arrayIt.hasNext()) {
				output.write("@Test\n");
				String testfile = arrayIt.next();
				String fileName = new File(testfile).getName();
				fileName = fileName.substring(0, fileName.indexOf(".csv"));
				output.write("public void " + fileName + "(){\n");
				// output.write("driver=getDriver();\n");
				output.write("AndroFrameworkExecutor afe = new AndroFrameworkExecutor();\n");
				output.write("afe.androExecutor(driver,\"" + testfile
						+ "\");\n}\n\n");

			}
			output.write("\n}");
			br.close();
			output.close();
		}

	}

	public static void main(String[] args) {
		TestCaseGenerator tc = new TestCaseGenerator();
		HashMap<String, ArrayList<String>> hm1 = new HashMap<String, ArrayList<String>>();
		DefaultProperties prop = new DefaultProperties();
		tc.listDirectory(
				new File("resources/"
						+ prop.getValueFromProperty("TESTCASE_FOLDER")), "",
				hm1);

		System.out.println(hm1);
		try {
			tc.classGenerator(hm1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
