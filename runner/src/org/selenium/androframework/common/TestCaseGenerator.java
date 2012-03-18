package org.selenium.androframework.common;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import junit.framework.Assert;

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

		while (it.hasNext()) {
			String classPath = it.next();
			String packagePath = classPath.toLowerCase();

			packagePath = replaceSpecialChar(packagePath);
			String javaClassFile = new File(packagePath).getName();
			String javaClassName = javaClassFile.substring(0, 1).toUpperCase()
					+ javaClassFile.substring(1);
			try{
			packagePath = packagePath
					.substring(0, packagePath.lastIndexOf("/"));}
			catch(StringIndexOutOfBoundsException e)
			{
				System.out.println("Exception out of bounds");
				packagePath="";
			}
			new File("src/test/" + packagePath).mkdirs();
			Writer output = new BufferedWriter(new FileWriter("src/test/"
					+ packagePath + "/" + javaClassName + ".java"));
			ArrayList<String> ar = hm.get(classPath);

			classWriter(classPath, ar, output);
			output.close();
		}

	}

	public static void main(String[] args) {
		TestCaseGenerator tc = new TestCaseGenerator();
		HashMap<String, ArrayList<String>> hm1 = new HashMap<String, ArrayList<String>>();
		DefaultProperties prop = DefaultProperties.getDefaultProperty();
		tc.listDirectory(
				new File("resources/"
						+ prop.getValueFromProperty("TESTCASE_FOLDER")), "",
				hm1);

		try {
			tc.classGenerator(hm1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void classWriter(String classPath, ArrayList<String> ar,
			Writer output) {

		FileInputStream fstream;
		try {
			String tempString = "";
			String importString = classPath.replace("/", ".");
			String outputStringClass="";
			importString = replaceSpecialChar(importString);
			try{
			importString = importString.substring(0,
					importString.lastIndexOf("."));
			}
			catch(StringIndexOutOfBoundsException e)
			{
				System.out.println("Exception out of bounds");
				outputStringClass = "package test;\n";
			}
			if(outputStringClass==""){
			outputStringClass = "package test." + importString + ";\n";
			}
			String outputStringTestCase = "";
			String testCase = "";

			fstream = new FileInputStream("resources/Template");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			boolean flag = false;
			while ((tempString = br.readLine()) != null) {
				if (tempString.equalsIgnoreCase("@Test"))
					flag = true;

				if (!flag)
					outputStringClass += tempString + "\n";
				else
					outputStringTestCase += tempString + "\n";

			}
			String packagePath = classPath.toLowerCase();
			String javaClassFile = new File(packagePath).getName();
			javaClassFile = replaceSpecialChar(javaClassFile);
			String javaClassName = javaClassFile.substring(0, 1).toUpperCase()
					+ javaClassFile.substring(1);

			outputStringClass = outputStringClass.replace("TestClassName",
					javaClassName);
			output.write(outputStringClass + "\n");

			Iterator<String> arrayIt = ar.iterator();
			while (arrayIt.hasNext()) {
				String testfile = arrayIt.next();
				String fileName = new File(testfile).getName();
				fileName = fileName.substring(0, fileName.indexOf(".csv"));

				fileName = replaceSpecialChar(fileName);

				testCase = outputStringTestCase.replace("testName", fileName);
				testCase = testCase.replace("filePath", testfile);

				output.write(testCase + "\n");
			}

			output.write("}");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Assert.fail(e.toString());
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			Assert.fail(e.toString());
		}
	}
	private String replaceSpecialChar(String fileName)
	{
		fileName=fileName.replace(" ", "");
		fileName=fileName.replace("-", "");
		fileName=fileName.replace("_", "");
		return fileName;
	}
}
