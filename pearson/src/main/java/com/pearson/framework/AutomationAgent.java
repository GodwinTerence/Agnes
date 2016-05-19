package com.pearson.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AutomationAgent {

	public static WebDriver driver;
	public static Properties prop = new Properties();
	private static Logger logger = Logger.getLogger(AutomationAgent.class);

	
	public static void preLaunch() throws Exception {
		AutomationAgent at=new AutomationAgent();
		at.loadProperties();
		at.initialiseWebDriverAndLaunch(getConfigProperty("Browser"), getConfigProperty("ApplicationURL"));
	}

	public void loadProperties() throws Exception {
		logger.info("Loading the properties file!!!");
		URL resource = AutomationAgent.class.getResource("/Application.properties");
		File inputFile = new File(resource.toURI());
		InputStream is = new FileInputStream(inputFile);
		prop.load(is);
	}

	/*
	 * Returns value of parameter passed from the properties file
	 */
	public static String getConfigProperty(String key) {
		return prop.getProperty(key);
	}

	public enum WebDriverType {
		FIREFOX, CHROME, IE
	};

	public void initialiseWebDriverAndLaunch(String browserType, String applicationUrl) {
		WebDriverType browsertype = WebDriverType.valueOf(browserType.toUpperCase());

		switch (browsertype) {
		case FIREFOX:
			driver = new FirefoxDriver();
			logger.info("Created firefox web driver instance");
			break;
		case CHROME:
			System.setProperty("webdriver.chrome.driver",
					AutomationAgent.class.getResource("/chromedriver.exe").getPath());
			driver = new ChromeDriver(DesiredCapabilities.chrome());
			logger.info("Created Chrome web driver instance");
			break;
		case IE:
			System.setProperty("webdriver.ie.driver",
					AutomationAgent.class.getResource("/IEDriverServer.exe").getPath());
			driver = new InternetExplorerDriver(DesiredCapabilities.internetExplorer());
			logger.info("Created IE web driver instance");
			break;
		default:
			logger.error("Invalid browser: ");
			break;
		}
		driver.get(applicationUrl);
	}

	public void quitDriver() {
		driver.quit();
	}
}
