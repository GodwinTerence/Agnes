package com.pearson.test;

import org.openqa.selenium.*;
import org.testng.*;
import org.testng.annotations.*;

import com.pearson.PageObject.*;
import com.pearson.framework.*;

public class LoginTest {
	WebDriver driver;
	public AutomationAgent AssessmentAutomationAgent;
	LoginPage objLoginPage;
	HomePage objHomePage;

	@BeforeTest
	public void setUp() throws Exception {
		AssessmentAutomationAgent = new AutomationAgent("**Application Name Under Test**");
	}

	@Test
	public void testLogin() {
		System.out.println("Test");
	}

	@AfterTest
	public void tearDown() {
		AssessmentAutomationAgent.quitDriver();
	}
}
