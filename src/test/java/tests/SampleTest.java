package tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

@Feature("Selenium Grid Test on Docker")
public class SampleTest {
	private WebDriver driver;

	@BeforeClass
	@Parameters("browser")
	public void setup(@Optional("chrome") String browser) throws MalformedURLException {
		// Generates `executor.json` for Allure
		DesiredCapabilities capabilities = new DesiredCapabilities();

		if (browser.equalsIgnoreCase("chrome")) {
			capabilities = DesiredCapabilities.chrome();
		} else if (browser.equalsIgnoreCase("firefox")) {
			capabilities = DesiredCapabilities.firefox();
		}

		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
	}

	@Test
	@Story("Google Homepage Verification")
	@Description("This test verifies the Google homepage title using Selenium Grid on Docker.")
	@Step("Opening Google and verifying title")
	public void testGoogleHomePage() {
		openGoogle();
		String title = driver.getTitle();
		Assert.assertTrue(title.contains("Google"), "Title does not match!");
	}

	@Step("Navigating to Google Homepage")
	public void openGoogle() {
		driver.get("https://www.google.com");
	}

	@AfterClass
	@Step("Closing the browser")
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

}