package com.chrobinson.automation.pages;

import com.chrobinson.automation.utils.ConfigurationReader;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
public class ResetPasswordPage{

private final WebDriver driver;

// ================= CONSTRUCTOR =================
public ResetPasswordPage(WebDriver driver) {
    this.driver = driver;
}

// ================= CREDENTIALS =================
private final String username = ConfigurationReader.getProperty("username");

// ================= LOCATORS =================
private final By emailInput         = By.xpath("//input[@name='emailAddress']");
private final By sendButton         = By.xpath("//input[@name='password_submit']");
private final By resetConfirmation  = By.cssSelector(".info-container-header");

// ================= GETTERS =================
public By getEmailAddressAtResetPassword() {
    return emailInput;
}

// ================= ACTIONS =================

public void clickSendButton() {
    // FIX: The previous implementation created a WebDriverWait but never
    //      used it — the click fired immediately via driver.findElement()
    //      with no guard, causing intermittent NoSuchElementException.
    //      Now uses elementToBeClickable so the button is ready before clicking.
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.elementToBeClickable(sendButton)).click();
}

public void enterEmail() {
    // FIX: The previous implementation called wait.until() to get the element
    //      reference but then discarded it, doing a second raw driver.findElement()
    //      lookup that could hit a stale element if the DOM re-rendered between
    //      the two calls. Now uses the element reference returned by wait.until().
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
    email.clear();
    email.sendKeys(username);
}

public void enterEmail(String emailAddress) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement emailField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(emailInput));
    emailField.clear();
    emailField.sendKeys(emailAddress);
}

// ================= VALIDATION =================

/**
 * Verifies the user has been navigated to the reset-password page by
 * checking the current URL — appropriate to call immediately after
 * clicking the "Forgot password" link, before any form submission.
 *
 * FIX: The old isOnResetPasswordPage() was checking for the post-submission
 *      confirmation message (which doesn't exist yet at navigation time),
 *      so it always timed out when called from TC09.  URL-based navigation
 *      checks and post-submission confirmation checks are now separate methods.
 */
public boolean isOnResetPasswordPageByUrl() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    try {
        wait.until(d -> d.getCurrentUrl().contains("resetPassword"));
        return driver.getCurrentUrl().contains("resetPassword");
    } catch (TimeoutException e) {
        return false;
    }
}

/**
 * Verifies the post-submission confirmation message is visible.
 * Call this only after clickSendButton() has been invoked.
 */
public boolean isResetConfirmationDisplayed() {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement confirmation = wait.until(
                ExpectedConditions.visibilityOfElementLocated(resetConfirmation));
        return confirmation.isDisplayed();
    } catch (TimeoutException e) {
        return false;
    }
}

/**
 * @deprecated Use isOnResetPasswordPageByUrl() to verify navigation to the
 *             reset-password page, or isResetConfirmationDisplayed() to verify
 *             the post-submission state. This method was misnamed and checked
 *             the wrong element for the wrong stage of the flow.
 */
@Deprecated
public boolean isOnResetPasswordPage() {
    return isOnResetPasswordPageByUrl();
}
}