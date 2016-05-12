package com.pearson.test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.AssertJUnit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import com.pearson.PageObject.LoginPage;
import com.pearson.PageObject.HomePage;
import com.pearson.framework.*;

public class LoginTest {	
	public AutomationAgent AssessmentAutomationAgent;
	WebDriver driver;
	public LoginPage objLoginPage;
	public HomePage objHomePage;

	@BeforeMethod
	@BeforeTest
	public void setUp() throws Exception {
		//AssessmentAutomationAgent.loadProperties();
		driver = new FirefoxDriver();
		driver.get("https://www.google.co.in");
	}

	@Test
	public void testLogin() {
		objLoginPage=new LoginPage(driver);
		assertFalse(objLoginPage.getLoginTitlePage());
	}

	@AfterMethod
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
