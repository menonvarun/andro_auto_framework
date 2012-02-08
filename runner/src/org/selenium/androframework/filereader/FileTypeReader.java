package org.selenium.androframework.filereader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 
 * Creates the object list for all supported file reading locator strategies.
 * Returns the object of the locator class that supports the file used to store
 * the locators.
 * 
 * @author Varun Menon
 */

public class FileTypeReader {

	private HashMap<String, String> supportedTypes = new HashMap<String, String>();

	public FileTypeReader() {
		supportedTypes.put("properties", "PropertiesReader");
	}

	public BaseReader getReader(String filePath) throws Exception {

		Iterator<String> it = supportedTypes.keySet().iterator();
		if (filePath.equalsIgnoreCase("") || (new File(filePath).isDirectory())) {
			throw new Exception("Path either empty or a directory");
		}
		while (it.hasNext()) {
			String fileType = it.next();
			if ((new File(filePath).isFile())
					&& filePath.endsWith("." + fileType)) {
				String className = supportedTypes.get(fileType);
				BaseReader readObject = (BaseReader) Class.forName(className)
						.newInstance();
				readObject.loadReaderFile(filePath);
				return readObject;
			}
		}
		throw new Exception("Path either empty or a directory");
	}

	public ArrayList<File> getFileList(String filePath) {
		File locatorFile = new File(filePath);
		ArrayList<File> fileList = new ArrayList<File>();
		if (!filePath.equalsIgnoreCase("") && locatorFile.isDirectory()) {
			for (File subLocator : locatorFile.listFiles()) {
				fileList.add(subLocator);
			}
			return fileList;
		} else {
			fileList.add(locatorFile);
			return fileList;
		}
	}

	public boolean isSupportedFileType(String fileType) {
		return this.supportedTypes.containsKey(fileType);
	}
}
