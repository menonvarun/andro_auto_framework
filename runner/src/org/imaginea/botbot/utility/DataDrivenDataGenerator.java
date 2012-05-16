package org.imaginea.botbot.utility;

import java.util.HashMap;
import java.util.List;
import junit.framework.Assert;
import org.imaginea.botbot.api.TestCSVReader;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

public class DataDrivenDataGenerator {

	String csvFilePath;
	List<String[]> row = null;
	String[] keySet = null;
	TestCSVReader csvReader = null;

	static Context context;

	public static void setContext(Context context) {
		DataDrivenDataGenerator.context = context;
	}

	public DataDrivenDataGenerator(String csvFilePath) {
		this.csvFilePath = csvFilePath;
		try {
			Log.e("context", context.getPackageName());
			Resources res = context.getResources();

			AssetManager asset = res.getAssets();
			csvReader = new TestCSVReader(asset.open(csvFilePath));

		} catch (Exception e) {
			Log.e("csv cannot be read", e + " CSV");
			Assert.fail("Unable to read the csv file :" + e.toString());
		}
		keySet = csvReader.getRow(0);

	}

	public HashMap<String, String> getHashMapForRow(int rowNumber) {
		HashMap<String, String> data = new HashMap<String, String>();
		//setting test case name by taking the first parameter in the row
		data.put("TestCaseName", csvReader.getData(rowNumber, 0));
		//creating hashmap with identifiers as keys and data in parameters as values 
		for (int count = 1; count < keySet.length+1; count++) {
			data.put(keySet[count-1], csvReader.getData(rowNumber, count));
		}
		return data;

	}

	public int getNumberOfRows() {
		// Subtracting 1 because first row is of keySet
		return csvReader.getLines() - 1;
	}

}
