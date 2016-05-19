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

public class LoginTest extends AutomationAgent {

	public LoginPage objLoginPage;
	public HomePage objHomePage;

	@BeforeTest
	public static void setUp() throws Exception {
		preLaunch();
	}

	@Test
	public void testLoginGodwin() {
		objLoginPage = new LoginPage();
		assertTrue(objLoginPage.getLoginTitlePage());
	}

	@Test
	public void testLoginAllwin() {
		objLoginPage = new LoginPage();
		assertTrue(objLoginPage.getLoginTitlePage());
	}

	@Test
	public void testLoginZubin() {
		objLoginPage = new LoginPage();
		assertFalse(objLoginPage.getLoginTitlePage());
	}

	@AfterTest
	public void tearDown() {
		quitDriver();
	}
}
