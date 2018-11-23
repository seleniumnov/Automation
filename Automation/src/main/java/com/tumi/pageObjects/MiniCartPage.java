package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class MiniCartPage {
	
	@FindBy(how=How.XPATH,using="//a[@title='Proceed to Checkout']")
	private WebElement proceedtoCheck;
	
	public WebElement getProceedCheckOut() {
		
		return proceedtoCheck;
	}

}
