package com.chrobinson.automation.test.ui.loginFunctionality;

import com.chrobinson.automation.pages.CreateShipmentPage;
import com.chrobinson.automation.pages.DashboardPage;
import com.chrobinson.automation.pages.HomePage;
import com.chrobinson.automation.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class functionalTests01 {

    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private CreateShipmentPage createShipmentPage;

    // FIX: Changed @BeforeClass → @BeforeMethod so every test gets a
    //      fresh browser session and isolated state, eliminating shared-
    //      state flakiness across the two test methods.
    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        createShipmentPage = new CreateShipmentPage(driver);

        homePage.navigateToHomePage();
    }

    // FIX: Added @AfterMethod to quit the driver after every test.
    //      Without this, browser windows leaked when using @BeforeMethod.
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC01 — Verify user lands on the home page and the login button is present
    // ─────────────────────────────────────────────────────────────────────────
    @Test(description = "TC01 - VERIFY HOME PAGE LOADS AND LOGIN BUTTON EXISTS")
    // FIX: Removed priority = 1 and dependsOnMethods coupling. Each test now
    //      sets up its own state via @BeforeMethod, so ordering dependencies
    //      are unnecessary and were a source of silent test skipping.
    public void testCase01() {
        Assert.assertEquals(
                driver.getCurrentUrl(),
                homePage.getHomePageURL(),
                "Browser is not on the expected home page URL"
        );

        Assert.assertFalse(
                homePage.getHomePageTitle().isEmpty(),
                "Home page title should not be empty"
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC02 — Click login, verify language is Deutsch, then log in successfully
    // ─────────────────────────────────────────────────────────────────────────
    @Test(description = "TC02 - LOGIN WITH VALID CREDENTIALS AND LANGUAGE = DEUTSCH")
    // FIX: Removed dependsOnMethods = "testCase01". This test now navigates
    //      to its own clean starting state via @BeforeMethod, so it no longer
    //      relies on TC01's side-effects or skips when TC01 fails.
    public void testCase02() {
        homePage.clickOnLogInButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> !d.getTitle().isEmpty());

        Assert.assertEquals(
                loginPage.getLoginPageTitle(),
                driver.getTitle(),
                "Did not navigate to the Login page"
        );

        // FIX: The return value was previously discarded. Now it is asserted
        //      so a wrong language actually fails the test.
        Assert.assertTrue(
                loginPage.validateLanguageIsDeutsch(),
                "Default language on the login page is not Deutsch"
        );

        loginPage.login();

        wait.until(ExpectedConditions.not(ExpectedConditions.titleIs("Login")));
        Assert.assertNotEquals(
                driver.getTitle(),
                "Login",
                "Login failed — still on the login page"
        );
    }
}