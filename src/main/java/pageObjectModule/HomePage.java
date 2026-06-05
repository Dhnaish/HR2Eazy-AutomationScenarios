package pageObjectModule;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.DriverSetup;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    public HomePage() {
        this.driver = DriverSetup.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    By homeTab = By.xpath("//a[@id='ctl00_btnHomeTab']");
    By informationPanel = By.xpath("//span[text()='Information Panel']");
    By popUpCloseButton = By.xpath("//div[@class='modal-body']/button[@class='close']");
    By leaveApplyBtn = By.xpath("//div[@id='ctl00_ContentPlaceHolder1_divApplyLeave']");

    public void closePopUp() {
        try {
        	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOfElementLocated(informationPanel));
            driver.findElement(popUpCloseButton).click();
        } catch (Exception e) {
            System.out.println("Information Panel pop-up is not displayed.");
        }
    }

    public void waitForHomePage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(homeTab));
    }

    public void clickLeaveApply() {
        driver.findElement(leaveApplyBtn).click();
    }
}