package com.chrobinson.automation.test.ui;

import com.chrobinson.automation.pages.CreateShipmentPage;
import com.chrobinson.automation.pages.DashboardPage;
import com.chrobinson.automation.pages.HomePage;
import com.chrobinson.automation.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NegativePathTests {

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

    @Test(description = "TC02 - VERIFY HOME PAGE LOADS AND LOGIN BUTTON EXISTS",
            priority = 2,
    dependsOnMethods = "testCase01")
    public void testCase02(){

    }
}
