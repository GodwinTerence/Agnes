package com.pearson.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pearson.framework.*;

public class LoginPage {
	WebDriver driver;
	@FindBy(name = "uid")
	WebElement userName;

	@FindBy(name = "password")
	WebElement password;

	@FindBy(id = "hplogo")
	WebElement titletext;
		
	@FindBy(name = "btnLogin")
	WebElement login;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Click on login button
	public void clickLogin() {
		login.click();
	}

	// Get Title of Login Page
	public Boolean getLoginTitlePage() {
		return titletext.isDisplayed();
	}

	// Set Username
	public void setUserName(String username) {
		userName.sendKeys(username);
	}

	// Set Password
	public void setPassword(String password) {
		userName.sendKeys(password);
	}

	// login Application
	public void loginApplication(String user, String Pwd) {
		this.setUserName(user);
		this.setPassword(Pwd);
		this.clickLogin();
	}
}
