package com.chrobinson.automation.pages;

import com.chrobinson.automation.utils.ConfigurationReader;
import com.chrobinson.automation.utils.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.chrobinson.automation.utils.Driver.getDriver;

public class HomePage {

    private final WebDriver driver;

    // ================= LOCATORS =================
    private final By logInButton = By.xpath("//button[normalize-space()='Einloggen']");

    // ================= PAGE INFO =================
    private final String homePageURL = ConfigurationReader.getProperty("homepage_url");

    // ================= CONSTRUCTOR =================
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // ================= NAVIGATION =================
    public void navigateToHomePage() {
        driver.get(homePageURL);
    }

    // ================= ACTIONS =================
    public void clickOnLogInButton() {
        // FIX: Added an explicit wait for the login button to be clickable
        //      before interacting with it. The previous code called
        //      driver.findElement() immediately after driver.get(), which
        //      raced against page load and caused intermittent
        //      NoSuchElementException / ElementNotInteractableException.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(logInButton)).click();
    }

    // ================= GETTERS =================
    public String getHomePageURL() {
        return homePageURL;
    }

    public String getHomePageTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> !d.getTitle().isEmpty());
        return driver.getTitle();
    }

    public By getLogInButton() {
        return logInButton;
    }
}
