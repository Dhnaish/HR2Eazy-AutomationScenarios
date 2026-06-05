package pageObjectModule;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.DriverSetup;

public class GetDataPage {

    WebDriver driver;
    WebDriverWait wait;

    public GetDataPage() {
        this.driver = DriverSetup.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    By sqlText = By.xpath("//span[contains(text(),'SQL')]");
    By textArea = By.xpath("//textarea[@id='ctl00_ContentPlaceHolder1_txtQuery']");
    By outputBtn = By.xpath("//input[@id='ctl00_ContentPlaceHolder1_btnExecute']");
    By checkTable = By.xpath("//table[@id='ctl00_ContentPlaceHolder1_gvResults']");
    By openLeaveValue = By.xpath("//tr[2]/td[count(//th[normalize-space()='OpenLeave']/preceding-sibling::th)+1]");
    By annualLeaveCount = By.xpath("//tr[2]/td[count(//th[normalize-space()='AnnualLeaveApplyLimit']/preceding-sibling::th)+1]");
    By medicalLeave = By.xpath("//tr[2]/td[count(//th[normalize-space()='MedicalLeaveApplyLimit']/preceding-sibling::th)+1]");
    String originalWindow;

    public void hitGetDataUrl() {

        originalWindow = driver.getWindowHandle();

        String currentUrl = driver.getCurrentUrl();

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(currentUrl);

        driver.navigate().to("https://qaen.hr2eazy.com/Modules/General/getData.aspx");

        wait.until(ExpectedConditions.visibilityOfElementLocated(sqlText));

        System.out.println("User navigate to the getData page successfully");
    }

    public void enterQueryForOpenLeaveSetting(String staffNo) {
        driver.findElement(textArea)
              .sendKeys("admin@1947 select * from userprofile where staffno = '" + staffNo + "'");
        driver.findElement(outputBtn).click();
    }
    
    public void enterQueryForAnnualLeaveCount(String orgID) {
        driver.findElement(textArea)
              .sendKeys("admin@1947 select * from leavepolicysetup where OrgaID = '" + orgID + "'");
        driver.findElement(outputBtn).click();
    }
    
    

    public String checkOpenLeaveSetting() {

        wait.until(ExpectedConditions.visibilityOfElementLocated(checkTable));

        String openLeaveValueText = driver.findElement(openLeaveValue).getText();
        System.out.println("Open Leave value for the user is: " + openLeaveValueText);

        driver.close();
        driver.switchTo().window(originalWindow);

        return openLeaveValueText;
    }
    
    public String checkAnnualLeaveCount() {

		wait.until(ExpectedConditions.visibilityOfElementLocated(checkTable));

		String annualLeaveCountText = driver.findElement(annualLeaveCount).getText();
		System.out.println("Annual Leave Count for the organization is: " + annualLeaveCountText);

		driver.close();
		driver.switchTo().window(originalWindow);

		return annualLeaveCountText;
	}
    
    public String checkMedicalLeaveCount() {

		wait.until(ExpectedConditions.visibilityOfElementLocated(checkTable));

		String medicalLeaveCountText = driver.findElement(medicalLeave).getText();
		System.out.println("Annual Leave Count for the organization is: " + medicalLeaveCountText);

		driver.close();
		driver.switchTo().window(originalWindow);

		return medicalLeaveCountText;
	}
    
}