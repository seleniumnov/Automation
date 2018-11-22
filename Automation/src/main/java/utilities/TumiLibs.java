package utilities;

import org.openqa.selenium.support.PageFactory;
import webpages.HomePage;

public class TumiLibs extends GenericMethods {
	
	public static void closeSignUp() {
		
		HomePage home = PageFactory.initElements(driver, HomePage.class);
		click(home.getSignupPopup(), "SignUp");
	}
	
	public static void acceptCookies() {
		
		HomePage home = PageFactory.initElements(driver, HomePage.class);
		click(home.getAcceptCookies(), "Accept Cookies");
	}

}
