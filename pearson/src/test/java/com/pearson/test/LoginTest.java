package com.pearson.test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.AssertJUnit.*;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
		 String textToSelect = "JavaScript";
	        WebDriverWait wait = new WebDriverWait(driver, 2);
	 
	        // Type text in Textbox
	        WebElement textBox = driver.findElement(By.id("tags"));
	        textBox.sendKeys("Ja");
	 
	        // Store the auto suggest weblement location
	        WebElement autoOption = driver.findElement(By
	                .xpath("//*[contains(@id,'ui-id')]"));
	        wait.until(ExpectedConditions.visibilityOf(autoOption));
	 
	        // Store the all auto suggests element in the list
	        List<WebElement> optionsToSelect = autoOption.findElements(By
	                .tagName("li"));
	        for (WebElement option : optionsToSelect) {
	 
	            // Compare Expected to available options in list and select if
	            // present
	            if (option.getText().equals(textToSelect)) {
	                System.out.println("Trying to select: " + textToSelect);
	                option.click();
	                break;
	            }
	        }
	}

	@Test
	public void testLoginAllwin() {
		objLoginPage = new LoginPage();
		assertTrue(objLoginPage.getLoginTitlePage());
	}


	@AfterTest
	public void tearDown() {
		quitDriver();
	}
}
