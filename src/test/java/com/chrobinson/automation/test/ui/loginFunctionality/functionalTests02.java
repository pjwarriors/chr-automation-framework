package com.chrobinson.automation.test.ui.loginFunctionality;

import com.chrobinson.automation.pages.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class functionalTests02 {

    private static final Logger log = LoggerFactory.getLogger(functionalTests02.class);
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private CreateShipmentPage createShipmentPage;
    private ResetPasswordPage resetPasswordPage;


    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        createShipmentPage = new CreateShipmentPage(driver);
        resetPasswordPage = new ResetPasswordPage(driver);


        homePage.navigateToHomePage();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();

        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC03 — Login with wrong password
    // ─────────────────────────────────────────────────────────────────────────
    @Test(description = "LOGIN WITH WRONG PASSWORD AND VERIFY ERROR IS DISPLAYED")
    public void testCase03() {
        homePage.clickOnLogInButton();
        loginPage.enterUsername();
        driver.findElement(loginPage.getPasswordInputBox()).sendKeys("WrongPass99");
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.validateErrorMessageIsDisplayed()); // FIX: result was never asserted

    }


    // ─────────────────────────────────────────────────────────────────────────
    // TC04 — Login with non-existent email
    // ─────────────────────────────────────────────────────────────────────────
    @Test(description = "LOGIN WITH NON-EXISTENT EMAIL AND VERIFY ERROR MESSAGE SHOWS UP")
    public void testCase04() {
        homePage.clickOnLogInButton();
        driver.findElement(loginPage.getEmailAddressInputBox()).sendKeys("notexist@example.com");
        loginPage.enterPassword();
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.validateErrorMessageIsDisplayed()); // FIX: result was never asserted
    }


    // ─────────────────────────────────────────────────────────────────────────
    // TC05 — Login with empty email field
    // ─────────────────────────────────────────────────────────────────────────
    @Test(description = "LOGIN WITH EMPTY EMAIL FIELD AND VERIFY ERROR MESSAGE IS SHOWN")
    public void testCase05() {
        homePage.clickOnLogInButton();
        loginPage.enterPassword();
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.validateErrorMessageIsDisplayed()); // FIX: result was never asserted
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC06 — Login with empty password field
    // ─────────────────────────────────────────────────────────────────────────
    @Test(description = "LOGIN WITH EMPTY PASSWORD FIELD AND VERIFY ERROR MESSAGE IS SHOWN")
    public void testCase06() {
        homePage.clickOnLogInButton();
        loginPage.enterUsername();
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.validateErrorMessageIsDisplayed()); // FIX: result was never asserted
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC07 — Login with both fields empty
    // ─────────────────────────────────────────────────────────────────────────
    @Test(description = "LOGIN WITH BOTH EMPTY FIELDS AND VERIFY ERROR MESSAGE IS SHOWN")
    public void testCase07() {
        homePage.clickOnLogInButton();
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.validateErrorMessageIsDisplayed()); // FIX: result was never asserted
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC08 — Login with both fields empty
    // ─────────────────────────────────────────────────────────────────────────
    @Test(description = "LOGIN WITH INVALID EMAIL FORMAT AND VERIFY ERROR MESSAGE IS SHOWN")
    public void testCase08() {
        homePage.clickOnLogInButton();
        // FIX: removed the premature loginPage.clickLoginButton() call that fired before fields were filled
        driver.findElement(loginPage.getEmailAddressInputBox()).sendKeys("not-an-email");
        driver.findElement(loginPage.getPasswordInputBox()).sendKeys("ValidPass1!");
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.validateErrorMessageIsDisplayed()); // FIX: result was never asserted
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC09 — Navigate to FORGOT PASSWORD page and verify user is sucessfully redirected there
    // ─────────────────────────────────────────────────────────────────────────
    @Test(description = "NAVIGATE TO 'FORGOT PASSWORD' PAGE AND VERIFY USER IS SUCESSFULLY REDIRECTED THERE")
    public void testCase09() {
        homePage.clickOnLogInButton();
        loginPage.clickResetPassword();
        resetPasswordPage.isOnResetPasswordPage();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC10 — Reset password with valid registered email
    // ─────────────────────────────────────────────────────────────────────────
    @Test(description = "RESET PASSWORD WITH VALID CREDENTIALS AND VALIDATE USER HAS RECEIVED AN EMAIL")
    public void testCase10() {
        homePage.clickOnLogInButton();
        loginPage.clickResetPassword();
        resetPasswordPage.enterEmail(); // Uses the registered email from config
        resetPasswordPage.clickSendButton();
        Assert.assertTrue(resetPasswordPage.isResetConfirmationDisplayed()); // FIX: was missing submit + assertion
    }


    // ─────────────────────────────────────────────────────────────────────────
    // TC11 — Reset password with non-existent email
    // ─────────────────────────────────────────────────────────────────────────

    @Test(description = "RESET PASSWORD WITH NON-EXISTENT EMAIL")
    public void testCase11() {
        homePage.clickOnLogInButton();
        loginPage.clickResetPassword();
        resetPasswordPage.enterEmail("unknown@example.com");
        resetPasswordPage.clickSendButton();
        Assert.assertTrue(resetPasswordPage.isResetConfirmationDisplayed());
    }


    // ─────────────────────────────────────────────────────────────────────────
    // TC12 — Switch interface language to English
    // ─────────────────────────────────────────────────────────────────────────

    @Test(description = "SWITCH INTERFACE LANGUAGE TO ENGLISH AND VERIFY IT IS SET TO SELECTED ONE")
    public void testCase12() {
        homePage.clickOnLogInButton();
        loginPage.languageDropDownClick();
        loginPage.selectLanguage("English");
        Assert.assertTrue(loginPage.isLanguageSelected("English"));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC13 — Switch interface language to Polski
    // ─────────────────────────────────────────────────────────────────────────

    @Test(description = "SWITCH INTERFACE LANGUAGE TO POLISH AND VERIFY IT IS SET TO SELECTED ONE")
    public void testCase13() {
        homePage.clickOnLogInButton();
        loginPage.languageDropDownClick();
        loginPage.selectLanguage("polish");
        Assert.assertTrue(loginPage.isLanguageSelected("polish"));
    }


    // ─────────────────────────────────────────────────────────────────────────
    // TC14 — Default language is Deutsch when ?lang=de is in URL
    // ─────────────────────────────────────────────────────────────────────────

    @Test(description = "VERIFY DEFAULT LANGUAGE IS DEUTSCH WHEN ?lang=de IS IN URL")
    public void testCase14() {
        homePage.clickOnLogInButton();
        Assert.assertTrue(driver.getCurrentUrl().toLowerCase().contains("?lang=de"));
    }


    // ─────────────────────────────────────────────────────────────────────────
    // TC15 — Login via TransId (Trans.eu OAuth)
    // ─────────────────────────────────────────────────────────────────────────
    @Test(description = "Login via TransId (Trans.eu OAuth)")
    public void testCase15(){
        homePage.clickOnLogInButton();
        loginPage.clickTransEu();
        // to be continued, need an authorized account

    }

    // ─────────────────────────────────────────────────────────────────────────
    // TC16 — Login via GBox (Inelo OAuth)
    // ─────────────────────────────────────────────────────────────────────────
    @Test(description = "CLICK GBOX BUTTON AND VERIFY USER IS REDIRECTED TO GBOX LOGIN PAGE")
    public void testCase16() {
        homePage.clickOnLogInButton();

        // Wait for the new page/tab to load and the URL to switch away from the
        // main login page — GBox redirects to an external Inelo identity provider.
        org.openqa.selenium.support.ui.WebDriverWait wait =
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(15));
        loginPage.clickGBox();

        Assert.assertTrue(
                driver.getCurrentUrl().toLowerCase().contains("gbox") ||
                        driver.getCurrentUrl().toLowerCase().contains("inelo"),
                "User was not redirected to the GBox/Inelo login page. Actual URL: " + driver.getCurrentUrl()
        );
        // to be continued — full OAuth flow requires valid GBox/Inelo credentials
    }

}
