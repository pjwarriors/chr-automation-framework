package com.chrobinson.automation.pages;

import com.chrobinson.automation.utils.ConfigurationReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chrobinson.automation.utils.Driver.getDriver;

public class LoginPage {


    private final WebDriver driver;

    // ================= CREDENTIALS =================
    private final String username = ConfigurationReader.getProperty("username");
    private final String password = ConfigurationReader.getProperty("password");

    // ================= LOCATORS =================
    private final By emailInput        = By.xpath("//input[@placeholder='E-Mail']");
    private final By passwordInput     = By.xpath("//input[@placeholder='Passwort']");
    private final By logInButton       = By.xpath("//input[@value='Einloggen']");
    private final By resetPasswordLink = By.xpath("//a[@href='/web/resetPassword']");
    private final By languageArrow     = By.xpath("//span[@class='select2-selection__arrow']");
    private final By loginErrorMessage = By.xpath("//div[@class='info-container-content']");
    private final By currentLanguage   = By.cssSelector(".select2-selection__rendered");
    private final By transEuContainer  = By.cssSelector(".transeu-container");
    private final By gboxContainer     = By.cssSelector(".gbox-container");


    // ================= LANGUAGE MAP =================
    private String languageText(String language) {
        switch (language.toLowerCase().trim()) {
            case "english":    return "English";
            case "polish":     return "Polski";
            case "german":     return "Deutsch";
            case "lithuanian": return "lietuvių";
            case "czech":      return "český";
            case "slovakian":  return "slovenský";
            case "romanian":   return "română";
            case "spanish":    return "español";
            case "hungarian":  return "magyar";
            case "russian":    return "русский";
            case "ukrainian":  return "українська";
            case "italian":    return "italiano";
            case "french":     return "français";
            case "bulgarian":  return "български";
            case "croatian":   return "hrvatski";
            case "slovenian":  return "slovenščina";
            case "turkish":    return "türkçe";
            default:
                throw new IllegalArgumentException("Unsupported language: " + language);
        }
    }

    // ================= GETTERS =================
    public By getPasswordInputBox()      { return passwordInput; }
    public By getEmailAddressInputBox()  { return emailInput; }
    public By getLogInButton()           { return logInButton; }

    // ================= CONSTRUCTOR =================
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // ================= PAGE TITLE =================
    public String getLoginPageTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> !d.getTitle().isEmpty());
        return driver.getTitle();
    }

    // ================= PAGE ACTIONS =================
    public void enterUsername() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        field.clear();
        field.sendKeys(username);
    }

    public void enterPassword() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        field.clear();
        field.sendKeys(password);
    }

    public void clickLoginButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(logInButton)).click();
    }

    public void login() {
        enterUsername();
        enterPassword();
        clickLoginButton();
    }

    public void clickTransEu() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(transEuContainer)).click();
    }

    public void clickGBox() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(gboxContainer)).click();
    }

    public void clickResetPassword() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(resetPasswordLink)).click();
    }

    public void languageDropDownClick() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(languageArrow)).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".select2-results__options")));

        List<WebElement> items = driver.findElements(By.cssSelector(".select2-results__option"));
        for (WebElement item : items) {
            System.out.println("[" + item.getText() + "]");
        }
    }

    public void selectLanguage(String language) {
        By locator = By.xpath("//li[contains(normalize-space(), '" + languageText(language) + "')]");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//ul[@class='select2-results__options']")));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    // ================= VALIDATION =================

    public boolean isLanguageSelected(String language) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // FIX: Used Unicode-normalised comparison to guard against invisible
        //      unicode characters or non-breaking spaces in the dropdown text
        //      that .trim() alone would not strip, causing intermittent failures.
        String selectedText = wait
                .until(ExpectedConditions.visibilityOfElementLocated(currentLanguage))
                .getText()
                .trim()
                .replaceAll("\\p{C}", "");   // strip invisible control/format chars
        String expected = languageText(language).trim().replaceAll("\\p{C}", "");
        return selectedText.equalsIgnoreCase(expected);
    }

    public boolean validateLanguageIsDeutsch() {
        // FIX: The original implementation read the `title` attribute from the
        //      select2 arrow span — that element has no title attribute, so it
        //      always returned null → false. The rendered/selected language text
        //      lives on `.select2-selection__rendered`, the same element used by
        //      isLanguageSelected(). We now read that element's visible text and
        //      compare it to the German display string "Deutsch".
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String selectedText = wait
                .until(ExpectedConditions.visibilityOfElementLocated(currentLanguage))
                .getText()
                .trim()
                .replaceAll("\\p{C}", "");
        return selectedText.equalsIgnoreCase("Deutsch");
    }

    public boolean validateErrorMessageIsDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement error = wait.until(
                ExpectedConditions.visibilityOfElementLocated(loginErrorMessage));
        return error.getText().contains("Benutzer oder das Passwort");
    }
}