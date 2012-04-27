package org.imaginea.botbot.common;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.imaginea.botbot.api.DefaultProperties;

public class TestCaseGenerator {

    HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();
    private static String baseFolder="";
    //private static String baseFolder=new File("runner").getPath()+File.separatorChar;
    public void listDirectory(File f, String directoryName,
            HashMap<String, ArrayList<String>> fileList) {
        String testcaseFolder="testcases"+File.separatorChar;
        File[] listOfFiles = f.listFiles();
        if (directoryName.equalsIgnoreCase("") && f.isDirectory()) {
            directoryName = f.getPath().split(testcaseFolder)[1];
        } else if (directoryName.equalsIgnoreCase("") && f.isFile()) {
            directoryName = new File(f.getParent()).getPath().split(
                    testcaseFolder)[1];
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
                        listOfFiles[i].getPath().split(testcaseFolder)[1],
                        fileList);
            }
        }
    }

    public void listAbsoluteDirectory(File f, String directoryName,    HashMap<String, ArrayList<String>> fileList) {
        char pathSeparator=File.separatorChar;
        String testcaseFolder=baseFolder+"testcases"+pathSeparator;
        File[] listOfFiles = f.listFiles();

        if (directoryName.equalsIgnoreCase("") && f.isDirectory()) {
            String path=f.getPath();
                directoryName=getDirectoryName(testcaseFolder,path);
        } else if (directoryName.equalsIgnoreCase("") && f.isFile()) {
            directoryName = getDirectoryName(testcaseFolder,new File(f.getParent()).getPath()                 );
        }
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()
&& listOfFiles[i].getName().endsWith(".csv")
&& !listOfFiles[i].getName().startsWith(".")) {
                if (fileList.containsKey(directoryName)) {
                    fileList.get(directoryName).add(
                            listOfFiles[i].getPath().replace("\\", "/"));
                } else {
                    ArrayList<String> ar = new ArrayList<String>();
                    ar.add(listOfFiles[i].getPath().replace("\\", "/"));
                    fileList.put(directoryName, ar);
                }
            } else if (listOfFiles[i].isDirectory()) {
                listAbsoluteDirectory(listOfFiles[i],
                        getDirectoryName(testcaseFolder,listOfFiles[i].getPath()),
                        fileList);
            }
        }
    }
    private String getDirectoryName(String path,String testFolder){
        String directoryName = "";
        if (String.valueOf(File.separatorChar).contentEquals("\\")) {
            List<String> paths = new ArrayList<String>(Arrays.asList(path.split("\\\\")));
            List<String> testCaseFolders = new ArrayList<String>(Arrays
                    .asList(testFolder.split("\\\\")));
            int pathsLength = paths.size();
            for (int i = 0; i < pathsLength && paths.size() != 0; i++) {
                if (!paths.get(0).contentEquals(testCaseFolders.get(0))) {
                    break;
                }
                paths.remove(0);
                testCaseFolders.remove(0);
            }
            Iterator<String> it = testCaseFolders.iterator();
            while (it.hasNext()) {
                directoryName = directoryName + File.separatorChar + it.next();
            }
            directoryName = directoryName.substring(1);
        } else {
            directoryName = testFolder.split(path)[1];
        }
        return directoryName;
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

    public void classGenerator(HashMap<String, ArrayList<String>> hm,String template)
            throws Exception {
        char pathSeparator= File.separatorChar;
        Iterator<String> it = hm.keySet().iterator();
        String testSource=baseFolder+("src:test:".replace(":", String.valueOf(pathSeparator)));
        while (it.hasNext()) {
            String classPath = it.next();
            String packagePath = classPath.toLowerCase();

            packagePath = replaceSpecialChar(packagePath);
            String javaClassFile = new File(packagePath).getName();
            String javaClassName = javaClassFile.substring(0, 1).toUpperCase()
                    + javaClassFile.substring(1);
            try{
            packagePath = packagePath
                    .substring(0, packagePath.lastIndexOf(pathSeparator));}
            catch(StringIndexOutOfBoundsException e)
            {
                System.out.println("Exception out of bounds");
                packagePath="";
            }
            new File(testSource + packagePath).mkdirs();
            Writer output = new BufferedWriter(new FileWriter(testSource
                    + packagePath + pathSeparator + javaClassName + ".java"));
            ArrayList<String> ar = hm.get(classPath);

            classWriter(classPath, ar, output,template);
            output.close();
        }

    }

    public static void main(String[] args) {
        TestCaseGenerator tc = new TestCaseGenerator();
        HashMap<String, ArrayList<String>> hm1 = new HashMap<String, ArrayList<String>>();
        DefaultProperties prop = DefaultProperties.getDefaultProperty();
        boolean isRobotium=false;
        String template ="Template";
        char pathSeparator=File.separatorChar;
        File f =new File("");
        System.out.println(f.getAbsolutePath());
        String testFolderPath=baseFolder+"testcases"+pathSeparator;
        if(prop.getValueFromProperty("FRAMEWORK").equalsIgnoreCase("robotium")){
            isRobotium=true;
            template="RobotiumTemplate";
        }
        if(isRobotium){
            tc.listAbsoluteDirectory(new File(testFolderPath
                    + prop.getValueFromProperty("TESTCASE_FOLDER")), "",
            hm1);
        }
        else{
            tc.listDirectory(
                    new File(testFolderPath
                            + prop.getValueFromProperty("TESTCASE_FOLDER")), "",
                    hm1);

        }
        System.out.println(hm1);
        try {
            tc.classGenerator(hm1,template);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void classWriter(String classPath, ArrayList<String> ar,
            Writer output,String template) {
        boolean isRobotium=false;
        FileInputStream fstream;
        char pathSeparator=File.separatorChar;
        String resourcesPath=baseFolder+"resources"+pathSeparator;
        if(template.contentEquals("RobotiumTemplate")){
            isRobotium=true;
        }
        try {
            String tempString = "";
            String importString = classPath.replace(String.valueOf(pathSeparator), ".");
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

            fstream = new FileInputStream(resourcesPath+template);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            boolean flag = false;
            while ((tempString = br.readLine()) != null) {
                if (tempString.equalsIgnoreCase("@Test") || (isRobotium && tempString.contains("testName")))
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
                if(isRobotium){
                    fileName="test"+fileName.substring(0, 1).toUpperCase()
                            + fileName.substring(1);
                }

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



