package reports;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Reports {
	public static ExtentHtmlReporter htmlreport;
	public static ExtentReports report;
	public static ExtentTest logger;
	public static WebDriver driver = null;
	String timeStamp;
	String extentReportPath;

	@BeforeTest(alwaysRun=true)
	public void startReport() {
		timeStamp = new SimpleDateFormat("dd-MMM-yy  hh.mm.ss aa").format(Calendar.getInstance().getTime());
		extentReportPath = System.getProperty("user.dir") + "//ExtentReports//" + timeStamp + ".html";
		htmlreport = new ExtentHtmlReporter(extentReportPath);
		htmlreport.loadXMLConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
		report = new ExtentReports();
		report.attachReporter(htmlreport);
	}

	@AfterTest(alwaysRun=true)
	public void endReport() {
		// writing everything to document
		// flush() - to write or update test information to your report.
		report.flush();
	}
	
	@BeforeSuite(alwaysRun=true)
	public static void removeExistingFiles() {
		try {
			File files = new File("./ExtentReports/Screenshots/");
			for (File file : files.listFiles()) {
				if (!file.isDirectory()) {
					file.delete();
				}
			}
		} catch (Exception e) {
			logger.log(Status.INFO, "File(s) are not available in ScreenShot Folder");
		}
	}

}
