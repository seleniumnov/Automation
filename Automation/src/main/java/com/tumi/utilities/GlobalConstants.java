package com.tumi.utilities;


import com.tumi.reports.Reports;

public class GlobalConstants extends Reports {
	//initiating driver
	
	
	public static String url = "https://www.stg-hybris-akamai.tumi.com/p/alpha-patch-tracer-key-fob-014757D/";

	// Drivers Path
	public static String chromePath = "./BrowserDrivers/chromedriver.exe";
	public static String firefoxPath = "./BrowserDrivers/geckodriver.exe";
	public static String iePath = "./BrowserDrivers/IEDriverServer.exe";
	
	// Driver System Set Property Syntax
	public static String chrome 	= "webdriver.chrome.driver";
	public static String firefox 	= "webdriver.gecko.driver";
	public static String ie 		= "webdriver.ie.driver";

	public static String excelPath = "./TestData/Tumi.xlsx";	
	public static String propertiesPath = "./Validations/FlagMessages.properties"; 

}
