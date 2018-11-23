package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import dataProvider.ReadTestData;
import pageObjects.HomePage;



public class GenericMethods extends GlobalConstants {

	public static String browser = null;
	public Properties prop = null;
	public static HomePage home = null;

	@BeforeClass(alwaysRun = true)
	public static void launchBrowser() {
		try {
			getBrowser("chrome");
			maximizeBrowser();
			driver.get(url);
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			TumiLibs.closeSignUp();
			TumiLibs.acceptCookies();
		} catch (Exception e) {
			Assert.fail("Faile to launch Application "+e.getMessage());
		}
	}

	@AfterClass(alwaysRun = true)
	public static void closeSession() {
		driver.close();
		try {
			killSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@BeforeMethod(alwaysRun = true)
	public static void initiatePageObjects(Method name) {
		
		logger = report.createTest(name.getName());
		home = new HomePage(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void verifyTestResult(ITestResult result) {

		try {
			if (result.getStatus() == ITestResult.SUCCESS) {

				logger.log(Status.PASS, "Test Method : " + result.getName() + "is Passed");

			} else if (result.getStatus() == ITestResult.FAILURE) {

				Timestamp time = new Timestamp(System.currentTimeMillis());
				String screenlocation = "./Screenshots/" + result.getName() + "" + time.getTime()
						+ ".png";
				getScreen("./ExtentReports/Screenshots/" + result.getName() + "" + time.getTime() + ".png");
				logger.fail(MarkupHelper.createLabel(result.getName() + " Test Case Failed", ExtentColor.RED));
				logger.fail(result.getThrowable());
				logger.fail("Screen Shot Reference:  ",
						MediaEntityBuilder.createScreenCaptureFromPath(screenlocation).build());

			} else if (result.getStatus() == ITestResult.SKIP) {

				logger.log(Status.SKIP, "Test Method : " + result.getName() + "is Skipped");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getScreen(String path) {
		try {
			File destination = new File(path);
			TakesScreenshot screenshot = (TakesScreenshot) driver;
			File src = screenshot.getScreenshotAs(OutputType.FILE);
			FileHandler.copy(src, destination.getAbsoluteFile());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void login(String sheetName,String testCaseName){
		try {
			Map<String, String> testData = ReadTestData.retrieveData(sheetName, testCaseName);
			click(home.getSignIn(), "Sign In");
			input(home.getUserName(), testData.get("EmailID"), "Email Address");
			input(home.getPassWord(), testData.get("Password"), "Password");
			click(home.getLogOn(), "Login");
		} catch (Exception e) {
			Assert.fail("Fail to Login due to "+e.getMessage());
		}
	}

	public static void getBrowser(String browserName) throws Exception {
		logger = report.createTest("Initialize Browser");
		browser = browserName;

		if (browserName.equalsIgnoreCase("firefox")) {

			System.setProperty(GlobalConstants.firefox, GlobalConstants.firefoxPath);

			driver = new FirefoxDriver();
			//logger.log(Status.INFO, "Firefox has been successfully Launched");

		} else if (browserName.equalsIgnoreCase("Chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-infobars");
			options.addArguments("--disable-notifications");
			System.setProperty(GlobalConstants.chrome, GlobalConstants.chromePath);
			driver = new ChromeDriver(options);
			//logger.log(Status.INFO, "Chrome has been successfully Launched");
		} else if (browserName.equalsIgnoreCase("ie")) {
			System.setProperty(GlobalConstants.ie, GlobalConstants.iePath);
			driver = new InternetExplorerDriver();
		} else {
			Assert.fail("No Browser has been selected");
		}
	}

	public static void getURL(String URL) {

		logger = report.createTest("initiate Application");

		if (URL.equalsIgnoreCase("Q2")) {

			driver.get("");
			logger.log(Status.INFO, "Successfully Navigated to Environment");

		} else if (URL.equalsIgnoreCase("Q3")) {

			driver.get("");
			logger.log(Status.INFO, "Successfully Navigated to  Environment");
		}
	}

	public static void click(WebElement element, String buttonName) {

		try {
			if (element.isEnabled()) {
				// Clicking on WebElement
				element.click();
				logger.log(Status.INFO, "Clicked on " + buttonName);
				WaitForJStoLoad();
			} else {
				logger.log(Status.FAIL, "Button is not enabled " + buttonName);
			}
		} catch (Exception e) {
			Assert.fail(buttonName + "is not Enabled or Unable to interact at this point");
		}

	}

	public static void input(WebElement element, String Value, String fieldName) {
		try {
			if (element.isDisplayed()) {
				// To clear the existed value
				element.clear();
				// To enter current value
				element.sendKeys(Value);
				logger.log(Status.INFO, "Entered the value in " + fieldName + " as: " + Value);
				WaitForJStoLoad();
			}
		} catch (Exception e) {
			logger.log(Status.FAIL, "Fail to Enter Value in  " + fieldName);
		}
	}

	public static String getText(WebElement element) {
		String text = null;
		try {
			if (element.isDisplayed()) {
				text = element.getText().trim();
			}
		} catch (Exception e) {
			Assert.fail("Unable to Fetch text " + e.getMessage());
		}
		return text;
	}

	public static String getInnerText(WebElement element) {
		String text = null;
		try {
			if (element.isDisplayed()) {
				text = element.getAttribute("innerHTML").trim();
			}
		} catch (Exception e) {
			Assert.fail("Unable to Fetch innerHTML " + e.getMessage());
		}
		return text;
	}

	public static String getAttributeValue(WebElement element) {
		String text = null;
		try {
			if (element.isDisplayed()) {
				text = element.getAttribute("value").trim();
			}
		} catch (Exception e) {
			Assert.fail("Unable to Fetch value " + e.getMessage());
		}
		return text;
	}

	public static WebElement explicitWait(WebElement element) {
		try {
			// WebDriverWait Initialization
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			e.getMessage();
		}
		return element;
	}

	public static WebElement elementToBeClickable(WebElement element) {
		try {
			// WebDriverWait Initialization
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			e.getMessage();
		}
		return element;
	}

	public static void selectByVisibleText(WebElement ele, String testData, String fieldName) {
		try {
			new Select(ele).selectByVisibleText(testData);
			logger.log(Status.INFO, "Selected " + testData + " from " + fieldName);
		} catch (Exception e) {
			Assert.fail("Fail to Select " + testData + " from " + fieldName + "Error " + e.getMessage());
		}
	}

	public static void selectByValue(WebElement ele, String testData, String fieldName) {
		try {
			new Select(ele).selectByValue(testData);
			logger.log(Status.INFO, "Selected " + testData + " from " + fieldName);
		} catch (Exception e) {
			Assert.fail("Fail to Select " + testData + " from " + fieldName + "Error " + e.getMessage());
		}
	}

	public static void selectByIndex(WebElement ele, int testData, String fieldName) {
		try {
			new Select(ele).selectByIndex(testData);
			logger.log(Status.INFO, "Selected " + testData + " from " + fieldName);
		} catch (Exception e) {
			Assert.fail("Fail to Select " + testData + " from " + fieldName + "Error " + e.getMessage());
		}
	}

	public static void domClick(WebElement element, String fieldName) {

		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			logger.log(Status.INFO, "Successfully Clicked on " + fieldName);
			WaitForJStoLoad();
		} catch (Exception e) {
			Assert.fail("Failed to Click on "+fieldName + "Error "+e.getMessage());
		}
	}

	public static boolean WaitForJStoLoad() {
		boolean jsLoad = false;
		try {
			for (int i = 0; i < 180; i++) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				jsLoad = (Boolean) executor.executeScript("return jQuery.active == 0");
				Thread.sleep(1000);
				if (jsLoad)
					break;
			}
			if (!jsLoad) {
				logger.log(Status.FAIL, "Page load Incomplete");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsLoad;
	}

	public static boolean switchToFrame(String frameType, int index, String nameOrId, WebElement frameElement) {
		boolean isFrameSwitched = false;
		try {
			switch (frameType.toLowerCase()) {
			case "index":
				driver.switchTo().frame(index);
				isFrameSwitched = true;
				break;
			case "nameorid":
				driver.switchTo().frame(nameOrId);
				isFrameSwitched = true;
				break;
			case "frameelement":
				driver.switchTo().frame(frameElement);
				isFrameSwitched = true;
				break;
			default:
				logger.log(Status.FAIL, "Please give proper 'input' to Switch to Frame");
			}
		} catch (Exception e) {
			Assert.fail("Swith to Frame is Failed due to "+e.getMessage());
		}
		return isFrameSwitched;
	}

	public static boolean defaultContent() {
		boolean isDefaultContent = false;
		try {
			driver.switchTo().defaultContent();
			WaitForJStoLoad();
			isDefaultContent = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDefaultContent;
	}

	public String getPageTitle() {
		String strWebPageTitle = null;
		try {
			strWebPageTitle = driver.getTitle().trim();
			logger.log(Status.INFO, "Page Title : " + strWebPageTitle);
		} catch (Exception e) {
			Assert.fail("Unable to Fetch Page Title");
		}
		return strWebPageTitle;
	}

	public void scrollDown() {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,200)", "");
		WaitForJStoLoad();
	}
	
	public void scrollUp() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(250,0)", "");
		WaitForJStoLoad();
	}
	
	public void scrollToWebElement(WebElement ele) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].scrollIntoView(true);", ele);
			WaitForJStoLoad();
		} catch (Exception e) {
			Assert.fail("Unable to Scroll "+e.getMessage());
		}
	}
	
	public String toolTip(WebElement ele) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(ele).perform();
		} catch (Exception e) {
			Assert.fail("Fail to Fetch Tool Tip "+e.getMessage());
		}
		return getText(ele);
	}
	
	
	public  String repository(String propertyName) {
		prop = new Properties();
		try {
			String propPath = propertiesPath;
			File file = new File(propPath);
			FileInputStream fin = new FileInputStream(file);
			prop.load(fin);
			
		} catch (Exception e) {
			Assert.fail("Unable to Load Properties "+e.getMessage());
		}
		return prop.getProperty(propertyName);
	}

	public static void maximizeBrowser() {
		if (OSFinder.isWindows()) {
			driver.manage().window().maximize();
		} else {
			driver.manage().window().setSize(new Dimension(1600, 900));
		}
	}

	public static void killSession() {
		try {
			switch (browser.toUpperCase()) {
			case "CHROME":
				try {
					Runtime.getRuntime().exec("taskkill /im chromedriver.exe /f");
				} catch (Exception e) {
					Runtime.getRuntime().exec("pkill chrome");
					Runtime.getRuntime().exec("pkill chromedriver");
				}
				break;
			case "FIREFOX":
				try {
					Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
				} catch (Exception e) {
					Runtime.getRuntime().exec("pkill firefox");
					Runtime.getRuntime().exec("pkill geckodriver");
				}

			default:
				driver.quit();
				break;
			}
		} catch (Exception e) {
			Assert.fail("Unable to Kill the Session");
		}
	}
	
	public static void acceptAlert() {
		try {
			driver.switchTo().alert().accept();
		} catch (Exception e) {
			logger.log(Status.FAIL, "No Alert Present in this window");
		}
	}
	
	public static void verifyAssertEquals(String actual,String expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (Exception e) {
			logger.log(Status.FAIL, e.getMessage());
		}
	}
	
	public static void verifyAssertContains(String actual,String text,String errorMsg) {
		try {
			Assert.assertEquals(actual.contains(text),errorMsg);
		} catch (Exception e) {
			logger.log(Status.FAIL, e.getMessage());
		}
	}
	
	public static void executeAutoITFile(String path) {
		try {
			Runtime.getRuntime().exec(path);
		} catch (Exception e) {
			Assert.fail("Fail to Execute .exe file "+e.getMessage());
		}
	}
	
	public static String getFirstSelectedOption(WebElement ele) {
		String option = null;
		try {
			option = new Select(ele).getFirstSelectedOption().getText();
		} catch (Exception e) {
			Assert.fail("Unable to Fetch First Selected Option due to "+e.getMessage());
		}
		return option;
	}
	
	public static void keyEnter(WebElement ele) {
		ele.sendKeys(Keys.ENTER);
	}
	
	public static void keyTab(WebElement ele) {
		ele.sendKeys(Keys.TAB);
	}
	
	/**
	 * Desc : Provide a Delay while the Browser is loaded.
	 * @param time
	 */
	public static void waitForLoad(int time) {
		boolean status = false;
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		long timeTaken;
	    ExpectedCondition<Boolean> pageLoadCondition = new
	        ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver input) {
					return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
				}
	        };
	    WebDriverWait wait = new WebDriverWait(driver, time);
	    do {
	    	
			try {
				wait.until(pageLoadCondition);
				if(wait.until(pageLoadCondition)==true){	
					status = true;
				}else{
					status = false;
				}
				} catch (NoSuchElementException e) {
					status = false;
				}
				endTime = Calendar.getInstance();
				timeTaken = (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 1000;
				if (timeTaken > time){
					timeTaken = timeTaken-time;	
					startTime = Calendar.getInstance();
				}
		} while(status == false && timeTaken <= time);		   		   
	}
	
	/**
	 * Desc :Provide a Delay till the page Javascript is loaded.
	 * @param locater
	 * @param timeOut
	 * @return 
	 * @throws InterruptedException
	 */
	public static long  waitForJavaScriptLoad(WebElement locater, int timeOut) throws InterruptedException {
		boolean status = false;
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		long timeTaken;
		do {
			try {
				if(locater.getText().equals("")){
					status = true;
				}else{
					status = false;
				}				
			} catch (NoSuchElementException e) {
				status = false;
			}
			endTime = Calendar.getInstance();
			timeTaken = (endTime.getTimeInMillis() - startTime
					.getTimeInMillis()) / 1000;

		} while (status == false && timeTaken <= timeOut);
		System.out.println("Time taken for Loading is "+timeTaken);			
		if (timeTaken > timeOut) {
			System.out.println("timeTaken > timeOut  case");
			Thread.sleep(30000);
		}
		return timeTaken;
	}
	
	/**
	 * Desc: Provide a wait Till the Page got updated with Javascripts
	 * @param timeOut
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unlikely-arg-type")
	public static void waitUnitlrocDetailsLoad(int timeOut) throws InterruptedException {
		boolean status = false;
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		long timeTaken;
		do {
			try {
				String string = "loading";
				if(!driver.findElement(By.tagName("title")).equals(string)){
					status = true;
				}else{
					status = false;
				}				
			} catch (NoSuchElementException e) {
				status = false;
			}
			endTime = Calendar.getInstance();
			timeTaken = (endTime.getTimeInMillis() - startTime
					.getTimeInMillis()) / 1000;

		} while (status == false && timeTaken <= timeOut);
		System.out.println("Time taken for Tags got Loading is "+timeTaken);			
		if (timeTaken > timeOut) {
			System.out.println("timeTaken > timeOut  case");
			Thread.sleep(30000);
		}	
	}
	/**
	 * Desc: Get All the Links and Images and Verify whether they are broken or not
	 * @throws IOException
	 */
	public void verifyBrokenLinksAndImages(){
		
        List <WebElement> totalLinksAndImages = driver.findElements(By.tagName("a"));
        totalLinksAndImages.addAll(driver.findElements(By.tagName("img")));
        
        System.out.println("total number of links and images are ----->" + totalLinksAndImages.size());

        List <String> activeLinks = new ArrayList<String>();

        for(int i = 0; i < totalLinksAndImages.size(); i++){
            if(totalLinksAndImages.get(i).getAttribute("href") != null){
                activeLinks.add(totalLinksAndImages.get(i).getAttribute("href"));
            }
        }
        System.out.println("total number of active links are ----->" + activeLinks.size());

        int count = 1;
        for(int j = 0; j < activeLinks.size(); j++){
            String url = activeLinks.get(j);
            try {
                HttpURLConnection connection = (HttpURLConnection)new URL(activeLinks.get(j)).openConnection();
                connection.connect();
                String massage = connection.getResponseMessage();
                connection.disconnect();
                System.out.println(count + "--->" + url + "----->" + massage);
                count++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	/**
	 * Desc: Delete All Cookies
	 */
	public void deleteAllCookies(){
        driver.manage().deleteAllCookies();
    }
	
	public void delay(int mili){
        try {
            Thread.sleep(mili);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
