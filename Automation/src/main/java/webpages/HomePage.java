package webpages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class HomePage {
	
	@FindBy(how=How.XPATH,using="//div[@id='firstTimeEmailSignupPopup']/div/span")
	private WebElement signupPopup;
	
	public WebElement getSignupPopup() {
		
		return signupPopup;
	}
	
	@FindBy(how=How.XPATH,using="(//div[contains(@class,'removeFocusIndicator')])[3]/div/a/span")
	private WebElement acceptCookies;
	
	public WebElement getAcceptCookies() {
		
		return acceptCookies;
	}
	
	

}
