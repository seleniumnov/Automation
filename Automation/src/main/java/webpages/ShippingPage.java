package webpages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ShippingPage {

	@FindBy(how=How.NAME,using="firstName")
	private WebElement firstName;
	
	public WebElement getFirstName() {
		
		return firstName;
	}
	
	@FindBy(how=How.NAME,using="regionIso")
	private WebElement regionIso;
	
	public WebElement getRegionIso() {
		
		return regionIso;
	}
}
