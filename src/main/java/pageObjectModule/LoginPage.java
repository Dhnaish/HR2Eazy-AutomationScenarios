package pageObjectModule;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.DriverSetup;

public class LoginPage {

	WebDriver driver;
	WebDriverWait wait;

	public LoginPage() {
		this.driver = DriverSetup.getDriver();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
	}

	By userName = By.xpath("//input[@id='txtLanId']");
	By password = By.xpath("//input[@id='txtPassword']");
	By signIn = By.xpath("//input[@id='btnSubmit']");
	By forgetPasswordText = By.xpath("//a[@id='HLForgotPassword']");
	By userNameText = By.xpath("//label[text()='Username']");
	By passwordText = By.xpath("//label[text()='Password']");
	By rememberMeText = By.xpath("//label[@for='rememberme']");
	By signInYourAccountText = By.xpath("//h3[text()='Sign In to Your Account']");
	By supportHelpDeskBtn = By.xpath("//button[@value='Support & Helpdesk']");
	By supportHelpDeskPopUpCloseBtn = By.xpath("//div[@class='modal-header']/button[@class='pop-cls-btn close']");	
	By featuresBtn = By.xpath("//button[normalize-space()='Features']");
	By servicesBtn = By.xpath("//button[normalize-space()='Services']");
	By trainingBtn = By.xpath("//button[normalize-space()='Training']");
	By featuresPopUpCloseBtn = By.xpath("//div[@class='modal-header mt-2']/button[@class='pop-cls-btn close']");
	By servicesPopUpCloseBtn = By.xpath("(//div[@class='modal-header mt-2']/button[@class='pop-cls-btn close'])[2]");
	By trainingPopUpCloseBtn = By.xpath("(//div[@class='modal-header mt-2']/button[@class='pop-cls-btn close'])[3]");
	By assertionCheck = By.xpath("//span[@id='lblError']");
	By rememberMeCheckBox = By.xpath("//input[@id='chkRemember']");
	
	public void enterUserName(String user) {
		driver.findElement(userName).sendKeys(user);
	}

	public void enterPassword(String pass) {
		driver.findElement(password).sendKeys(pass);
	}

	public void clickSignIn() {
		driver.findElement(signIn).click();
	}
	
	public @Nullable String grabTextCssValue(By xpath) {

		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));

		String color = element.getCssValue("color");

		System.out.println("Color value: " + color);

		return color;

	}


	public List<String> textCSSValue() {

	    List<String> val = new ArrayList<>();
	    
	    @Nullable
		String forgetPassVal = grabTextCssValue(forgetPasswordText);
	    System.out.println("Forget Password Text CSS Value: " + forgetPassVal);
	    val.add(forgetPassVal);
	    

	    @Nullable
	    String userNameVal = grabTextCssValue(userNameText);

	    System.out.println("Username Text CSS Value: " + userNameVal);

	    val.add(userNameVal);

	    String passwordVal = grabTextCssValue(passwordText);

	    System.out.println("Password Text CSS Value: " + passwordVal);

	    val.add(passwordVal);

	    System.out.println(val);
	    
	    String rememberMeVal = grabTextCssValue(rememberMeText);
	    
	    System.out.println("Remember Me Text CSS Value: " + rememberMeVal);
	    
	    val.add(rememberMeVal);
	    
//	    String signInYourAccountVal = grabTextCssValue(signInYourAccountText);
//	    
//	    System.out.println("Sign In Your Account Text CSS Value: " + signInYourAccountVal);
//	    
//	    val.add(signInYourAccountVal);
	    
	    return val;
	    
	    
	}
	
	public void clickBtn(By xpath) {
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
		element.click();
	}
	
	public void popupHandle(By xpath) {
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
		if (element.isDisplayed()) {
			element.click();
		}
		
	}
	
	
	public void clickSupportHelpDeskBtn() {
		clickBtn(supportHelpDeskBtn);
	}
	
	public void closeSupportHelpDeskPopup() {
		popupHandle(supportHelpDeskPopUpCloseBtn);
	}
	
	public void clickFeaturesBtn() {
		clickBtn(featuresBtn);
	}
	
	public void clickServicesBtn() {
		clickBtn(servicesBtn);
	}
	
	public void clickTrainingBtn() {
		clickBtn(trainingBtn);
	}
	
	
	public void closeFeaturesPopUp() {
		popupHandle(featuresPopUpCloseBtn);
	}
	
	public void closeServicesPopUp() {
		popupHandle(servicesPopUpCloseBtn);
	}
	
	public void closeTrainingPopUp() {
		popupHandle(trainingPopUpCloseBtn);
	}
	
	public String loginAssertionCheck() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(assertionCheck));
		String assertionText = driver.findElement(assertionCheck).getText();
		System.out.println("Assertion Text: " + assertionText);
		
		return assertionText;
	}
	
	
	public void validUserNameWithoutPassword(String user) {
		enterUserName(user);
		clickSignIn();
	}
	
	public void validPasswordWithoutUserName(String pass) {
		enterPassword(pass);
		clickSignIn();
	}
	
	public void invalidUserNameAndPassword(String user, String pass) {
		enterUserName(user);
		enterPassword(pass);
		clickSignIn();
	}
	
	public void clickRememberMe() {
		wait.until(ExpectedConditions.elementToBeClickable(rememberMeCheckBox)).click();
	}
	
	public void checkEnabledRememberMe() {
		boolean isEnabled = driver.findElement(rememberMeCheckBox).isEnabled();
		System.out.println("Is Remember Me checkbox enabled? " + isEnabled);
	}
	
	
}