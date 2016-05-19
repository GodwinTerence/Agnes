package com.pearson.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pearson.framework.*;

public class HomePage extends AutomationAgent{
	

	@FindBy(xpath="//table//tr[@class='heading3']")
	WebElement homePageUserName;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Click on login button
	public String getHomePageDashboardUserName() {
		return homePageUserName.getText();
	}

}
