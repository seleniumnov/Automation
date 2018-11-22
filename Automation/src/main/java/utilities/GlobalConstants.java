package utilities;


import reports.Reports;

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
	
	// Excel Data Path
	
	public static String excelPath = System.getProperty("user.dir") + "\\src\\test\\resources\\.xlsx";

}
