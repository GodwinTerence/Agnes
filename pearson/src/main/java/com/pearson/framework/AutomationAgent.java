package com.pearson.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

public class AutomationAgent implements IReporter {

	public static WebDriver driver;
	public static Properties prop = new Properties();
	private static Logger logger = Logger.getLogger(AutomationAgent.class);
	public ExtentReports extent;
	public ExtentTest test;

	public static void preLaunch() throws Exception {
		AutomationAgent at = new AutomationAgent();
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

	public void handleAutoSuggest(String searchText, String textToSelect) {

		WebDriverWait wait = new WebDriverWait(driver, 2);
		// Type text in Textbox
		WebElement textBox = driver.findElement(By.id("tags"));
		textBox.sendKeys(searchText);

		// Store the auto suggest weblement location
		WebElement autoOption = driver.findElement(By.xpath("//*[contains(@id,'ui-id')]"));
		wait.until(ExpectedConditions.visibilityOf(autoOption));

		// Store the all auto suggests element in the list
		List<WebElement> optionsToSelect = autoOption.findElements(By.tagName("li"));
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

	public void handleAlert(boolean accept) {
		Alert alert = driver.switchTo().alert();
		if (accept) {
			alert.accept();
		} else {
			alert.dismiss();
		}
	}

	public void handleAlertWithAuthentication(String user, String pwd) {
		Alert alert = driver.switchTo().alert();
		alert.authenticateUsing(new UserAndPassword(user, pwd));
	}

	public void handleRandomPopUp() {
		Set<String> windowId = driver.getWindowHandles();
		if (windowId.size() == 2) {
			Iterator<String> itr = windowId.iterator();
			String mainWindow = itr.next();
			String childWindow = itr.next();
			driver.switchTo().window(childWindow);
			driver.close();
			driver.switchTo().window(mainWindow);
		}
	}

	public void addCookies(String name, String content) {
		Cookie cookie = new Cookie(name, content);
		driver.manage().addCookie(cookie);
	}

	public void deleteCookies() {
		driver.manage().deleteAllCookies();
	}

	public void handleFrames(String frameName) {
		driver.switchTo().frame(frameName);
	}

	public void quitDriver() {
		driver.quit();
	}

	public void click(WebDriver driver, By by) {
		try {
			(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(by));
			driver.findElement(by).click();
		} catch (StaleElementReferenceException sere) {
			// simply retry finding the element in the refreshed DOM
			driver.findElement(by).click();
		} catch (TimeoutException toe) {
			test.log(LogStatus.ERROR, "Element identified by " + by.toString() + " was not clickable after 10 seconds");
		}
	}

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		extent = new ExtentReports(outputDirectory + File.separator + "Extent.html", true);

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();

				buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
				buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
				buildTestNodes(context.getPassedTests(), LogStatus.PASS);
			}
		}

		extent.flush();
	}

	private void buildTestNodes(IResultMap tests, LogStatus status) {

		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				test = extent.startTest(result.getMethod().getMethodName());

				String message = "Test " + status.toString().toLowerCase() + "ed";

				if (result.getThrowable() != null)
					message = result.getThrowable().getMessage();

				test.log(status, message);

				extent.endTest(test);
			}
		}
	}
}
