package com.chrobinson.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {

    private final WebDriver driver;

    // ================= LOCATORS =================
    private final By createAShipmentLink = By.xpath("//*[contains(text(), 'Transportauftrag erstellen')]");
    private final By pickUpAndDropOffLink = By.xpath("//*[contains(text(), 'Lade- und Entladestellen')]");

    // ================= CONSTRUCTOR =================
    public DashboardPage(WebDriver driver) {
        this.driver = driver;

    }


    // ================= PAGE TITLE =================
    private final String dashBoardTitle = "fireTMS.com";

    //=================== ACTIONS======================

    public boolean isCreateShipmentPanelOpen() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pickUpAndDropOffLink)).isDisplayed();
    }
    public void clickCreateShipment(){
        driver.findElement(createAShipmentLink).click();
    }

    // ================= GETTERS =================

    public String getCreateAShipmentLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> !d.getTitle().isEmpty());
        return driver.getTitle();
    }


    public String getDashBoardTitle() {
        return dashBoardTitle;
    }
}
