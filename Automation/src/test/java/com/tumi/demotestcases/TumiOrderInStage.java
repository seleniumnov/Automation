package testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import pageObjects.MiniCartPage;
import pageObjects.ShippingPage;
import utilities.GenericMethods;

public class TumiOrderInStage extends GenericMethods {
	private String baseUrl;
	
	WebDriver driver;
	
	@Test
	public void testTumiLogin() throws InterruptedException{
		
		MiniCartPage cart = PageFactory.initElements(driver, MiniCartPage.class);
		ShippingPage shipping = PageFactory.initElements(driver, ShippingPage.class);
		
		
		driver.findElement(By.id("addToCartBtn")).click();
		Thread.sleep(3000);
		
		//click on proceed to checkout in Mini cart
		click(cart.getProceedCheckOut(), "Proceed to Checkout");
		Thread.sleep(5000);
		
	
		//singlePageCheckout
		driver.findElement(By.xpath(".//*[@id='cart-summary-container']/div[2]/div[3]/div[1]/button")).click();
		driver.findElement(By.name("email")).sendKeys("skomma@coredatalabs.com");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(3000);
		
		//Shipping page
		
		input(shipping.getFirstName(), "test", "First Name");
		driver.findElement(By.name("lastName")).sendKeys("user");
		driver.findElement(By.name("line1")).sendKeys("1001 Durham Avenue");
		driver.findElement(By.name("townCity")).sendKeys("South plainfield");
		
		selectByVisibleText(shipping.getRegionIso(), "New Jersey", "Region");
		Thread.sleep(3000);
		
		driver.findElement(By.name("postcode")).sendKeys("07080");
		driver.findElement(By.name("phone")).sendKeys("12345678901");
		Thread.sleep(3000);
		
		driver.findElement(By.xpath("//html/body/div[1]/main/div[1]/div/div/div/div[3]/div[2]/button")).click();
		Thread.sleep(3000);
		
		//shipping method page
		driver.findElement(By.xpath("html/body/div[1]/main/div[1]/div/div/div/div[2]/div[2]/button")).click();
		Thread.sleep(5000);
		
		//billing page
		driver.findElement(By.name("nameOnCard")).sendKeys("test");
		driver.findElement(By.name("cardNumber")).sendKeys("4111111111111111");
		driver.findElement(By.xpath(".//*[@id='paymentSection']/div/div/form/div[1]/div[3]/div[1]/div/div/span/span/span[3]")).click();
		driver.findElement(By.linkText("04")).click();
		driver.findElement(By.xpath(".//*[@id='paymentSection']/div/div/form/div[1]/div[3]/div[2]/div/div/span/span/span[3]")).click();
		driver.findElement(By.linkText("2020")).click();
	
		
		driver.findElement(By.id("card_cvv")).sendKeys("123");
		driver.findElement(By.xpath("//div[@id='newPaymentButton']/button")).click();
		Thread.sleep(3000);
		
		//Review page
		driver.findElement(By.xpath("html/body/div[1]/main/div[1]/div/div/div/section[2]/button")).click();
		Thread.sleep(5000);
		
		//driver.quit();
		
	}
	

}