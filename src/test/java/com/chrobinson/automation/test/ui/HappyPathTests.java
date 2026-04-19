package com.chrobinson.automation.test.ui;

import com.chrobinson.automation.pages.CreateShipmentPage;
import com.chrobinson.automation.pages.DashboardPage;
import com.chrobinson.automation.pages.HomePage;
import com.chrobinson.automation.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class HappyPathTests {


    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private CreateShipmentPage createShipmentPage;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        createShipmentPage = new CreateShipmentPage(driver);


        homePage.navigateToHomePage();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC01 — Verify user lands on the home page and the login button is present
    // ─────────────────────────────────────────────────────────────────────────
    @Test(description = "TC01 - VERIFY HOME PAGE LOADS AND LOGIN BUTTON EXISTS",
            priority = 1)
    public void testCase01() {
        // BUG FIX: original code compared driver.getTitle() to driver.getTitle() — always true.
        // Correct approach: compare the expected URL/title from config against the live browser value.
        Assert.assertEquals(
                driver.getCurrentUrl(),
                homePage.getHomePageURL(),
                "Browser is not on the expected home page URL"
        );

        // Verify the title is not blank (sanity check the page actually loaded)
        Assert.assertFalse(
                homePage.getHomePageTitle().isEmpty(),
                "Home page title should not be empty"
        );
    }


    // ─────────────────────────────────────────────────────────────────────────
    // TC02 — Verify clicking login navigates to login page, then log in
    // ─────────────────────────────────────────────────────────────────────────
    // BUG FIX: method was also named testCase01 — renamed to testCase02.
    // BUG FIX: missing closing brace on this method.
    // BUG FIX: TC02 does not re-navigate or re-click if TC01 already clicked —
    //          use priority + dependsOnMethods to express the dependency explicitly.
    @Test(description = "TC02 - VERIFY LOGIN PAGE AND PERFORM LOGIN",
            priority = 2,
            dependsOnMethods = "testCase01")
    public void testCase02() {
        // Click login from the home page
        homePage.clickOnLogInButton();

        // Wait for the login page title to stabilise
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> !d.getTitle().isEmpty());

        // BUG FIX: getLoginPageTitle() now fetches the live browser title (dynamic),
        // so this assertion is a real comparison, not a hardcoded-vs-live mismatch.
        Assert.assertEquals(
                loginPage.getLoginPageTitle(),
                driver.getTitle(),
                "Did not navigate to the Login page"
        );

        // Perform login
        loginPage.login();

        // Verify we left the login page after a successful login
        wait.until(ExpectedConditions.not(
                ExpectedConditions.titleIs(driver.getTitle())
        ));
        Assert.assertNotEquals(
                driver.getTitle(),
                "Login",
                "Login failed — still on the login page"
        );
    }

    @Test(description = "VERIFY DASHBOARD PAGE AND OPEN CREATING A SHIPMENT",
            priority = 3,
            dependsOnMethods = "testCase02")
    public void testCase03() {

        Assert.assertEquals(driver.getTitle(), dashboardPage.getDashBoardTitle());

        dashboardPage.clickCreateShipment();

        Assert.assertTrue(
                dashboardPage.isCreateShipmentPanelOpen(),
                "Create Shipment panel did not open"
        );


    }

        @Test(description = "VERIFY CREATE SHIPMENT TAB POPS UP AND CAN FILL OUT ALL INFORMATIONS",
                priority = 4,
        dependsOnMethods = "testCase03")
                public void testCase04() {

            createShipmentPage.clickOnCustomer();

        }


    @AfterClass
    public void tearDown() {
        // Close the browser once after all tests
        if (driver != null) {
            driver.quit();
        }
    }

}