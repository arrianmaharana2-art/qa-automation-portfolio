package com.qa.portfolio.base;

import com.qa.portfolio.pages.InventoryPage;
import com.qa.portfolio.pages.LoginPage;
import com.qa.portfolio.utils.Config;
import com.qa.portfolio.utils.DriverFactory;
import com.qa.portfolio.utils.TestData;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Every test class extends this. It opens a fresh browser on the login page
 * before each test and closes it afterwards, so tests never depend on each other.
 */
public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.createDriver();
        driver.get(Config.get("baseUrl"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /** Used by the screenshot listener. */
    public WebDriver getDriver() {
        return driver;
    }

    protected InventoryPage loginAsStandardUser() {
        return new LoginPage(driver).loginAs(TestData.STANDARD_USER, TestData.PASSWORD);
    }
}
