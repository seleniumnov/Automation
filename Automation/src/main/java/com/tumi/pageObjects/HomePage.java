package com.tumi.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.tumi.reports.Reports;
import com.tumi.utilities.GenericMethods;

public class HomePage extends GenericMethods {
	
	public HomePage(WebDriver driver) {
		Reports.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(how=How.XPATH,using="//div[@id='firstTimeEmailSignupPopup']/div/span")
	private WebElement signupPopup;
	
	public WebElement getSignupPopup() {
		
		return signupPopup;
	}
	
	@FindBy(how=How.XPATH,using="//a[contains(text(),'Close')]")
	private WebElement acceptCookies;
	
	public WebElement getAcceptCookies() {
		
		return acceptCookies;
	}
	
	@FindBy(how=How.XPATH,using="(//a[contains(text(),'Sign In')])[1]")
	private WebElement signIn;
	
	public WebElement getSignIn() {
		
		return signIn;
	}
	
	@FindBy(how=How.XPATH,using="//input[@id='j_username']")
	private WebElement j_username;
	
	public WebElement getUserName() {
		
		return j_username;
	}
	
	@FindBy(how=How.XPATH,using="//input[@id='j_password']")
	private WebElement j_password;
	
	public WebElement getPassWord() {
		
		return j_password;
	}
	
	@FindBy(how=How.XPATH,using="//input[@value='Sign in']")
	private WebElement submit;
	
	public WebElement getLogOn() {
		
		return submit;
	}
	
	@FindBy(how=How.XPATH,using="(//div[@class='error-message'])[1]")
	private WebElement invalidCredentials;
	
	public WebElement getInvalidCredentialsError() {
		
		return invalidCredentials;
	}
}
