package com.chrobinson.automation.pages;

import com.chrobinson.automation.utils.ConfigurationReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CreateShipmentPage {

    private final WebDriver driver;

    // ================= LOCATORS =================

    private final By CreateShipmentPopUpPage = By.xpath("//div[contains(text(), 'Kilometer und Kosten')]");
    private final By customerInputBox = By.xpath("(//input[contains(@class, 'v-filterselect-input')])[1]");
    private final By cuisineRemyDropDown = By.xpath("//td[@class='gwt-MenuItem gwt-MenuItem-selected']");


    // ================= CONSTRUCTOR =================
    public CreateShipmentPage(WebDriver driver) {
        this.driver = driver;
    }

    // ================= PAGE TITLE =================

    // ================= GETTERS =================

    public By getCustomerInputBox() {
        return customerInputBox;
    }


    // ================= PAGE ACTIONS =================

    public void clickOnCustomer(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(customerInputBox).click();
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(getCustomerInputBox()).sendKeys("r");
        driver.findElement(cuisineRemyDropDown).click();
    }
}
