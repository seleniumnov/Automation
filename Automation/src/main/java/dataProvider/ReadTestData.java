package dataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utilities.GlobalConstants;

public class ReadTestData {

	public static String sPreviousTestCaseName;
	public static int iRowNo;
	public static File file;
	public static FileInputStream fin;
	public static XSSFWorkbook wb;
	public static XSSFSheet sh;
	public static int rows;
	public static int cols;
	public static String sCurTestCaseName;
	public static String sKey;
	public static String sValue;
	public static Map.Entry<String, Map<String, String>> entry;
	public static Map<String, String> values;

	/*
	 * Below Method is Used to get multiple sets of data from excel. Parameters:
	 * File Path, Sheet Name, TestCase Name
	 */
	
	public static Map<String, Map<String, String>> readTestData(String sFilePath, String sSheetName,
			String sTestCaseName) throws Exception {

		Map<String, Map<String, String>> objTestData = new HashMap<String, Map<String, String>>();
		try {
			sPreviousTestCaseName = "";
			iRowNo = 1;
			// Go To File Path
			file = new File(sFilePath);
			fin = new FileInputStream(file);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sSheetName);
			rows = sh.getLastRowNum() + 1;
			cols = sh.getRow(0).getLastCellNum();
			/*
			 * i is used to get the Test Case Name
			 */
			for (int i = 1; i < rows; i++) {

				// Below Map Interface is used to find Single Set of Data
				Map<String, String> objRowData = new HashMap<String,String>();

				sCurTestCaseName = sh.getRow(i).getCell(0).getStringCellValue().trim();

				if ((sPreviousTestCaseName.length() != 0) && (sCurTestCaseName != sPreviousTestCaseName)
						&& (sTestCaseName.trim().length() > 0)) {
					break;
				}
				if (sCurTestCaseName.equalsIgnoreCase(sTestCaseName)) {
					sPreviousTestCaseName = sCurTestCaseName;
					// Below Loop is used to get all the headers names in the excel
					for (int j = 0; j < cols; j++) {
						sKey = sh.getRow(0).getCell(j).getStringCellValue().trim();
						sValue = "";
						if (sh.getRow(i).getCell(j) != null) {
							if (sh.getRow(i).getCell(j).getCellType() == CellType.STRING) {
								sValue = sh.getRow(i).getCell(j).getStringCellValue().trim();
							} else {
								sValue = sh.getRow(i).getCell(j).getRawValue();
							}
						}
						objRowData.put(sKey, sValue);
					}
					objTestData.put("Row" + iRowNo, objRowData);

					objRowData = null;

					iRowNo++;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objTestData;
	}

	public static Map<String, String> retrieveData(String sheetName, String testCase_Name) {

		Map<String, Map<String, String>> testData = null;

		try {
			testData = readTestData(GlobalConstants.excelPath, sheetName, testCase_Name);

		} catch (Exception e) {

			e.printStackTrace();
		}

		Iterator<Entry<String, Map<String, String>>> entries = testData.entrySet().iterator();

		while (entries.hasNext()) {

			entry = (Entry<String, Map<String, String>>) entries.next();
			values = (Map<String, String>) entry.getValue();
			System.out.println(values);
		}
		return values;
	}
}
