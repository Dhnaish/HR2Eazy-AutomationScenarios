package stepDefination;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import Utilities.DriverSetup;
import Utilities.Utilities;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjectModule.LoginPage;

public class LoginPageTest {
	
	WebDriver driver = DriverSetup.getDriver();
	LoginPage loginPage;

	@Given("User enter the login page")
	public void user_enter_the_login_page() throws IOException {
		Properties property = Utilities.getProperty();

		String url = property.getProperty("appUrl");

		driver.get(url);
	    System.out.println("User is on the login page and navigates to the Forget Password Text.");
	}
	
//	private String forgetPasswordTextCssValue;
	List<String> textCSSValue;
	@When("Grab the Css values for all Text.")
	public void grab_the_css_values_for_all_text() {

	    loginPage = new LoginPage();

//	    forgetPasswordTextCssValue =
//	            loginPage.grabForgetPasswordTextCssValue();
//
//	    System.out.println("Grabbed CSS Value: " + forgetPasswordTextCssValue);
	    
	    textCSSValue = loginPage.textCSSValue();
	   System.out.println("Text CSS Values: " + textCSSValue);
	}
	
	
	@Then("User need to check the background color match or not with the expected background color.")
	public void user_need_to_check_the_background_color_match_or_not_with_the_expected_background_color() throws IOException {
		Properties property = Utilities.getProperty();

		String forgetPasswordCSS = property.getProperty("forgetPasswordCSS");
		
//		Assert.assertEquals(forgetPasswordCSS, forgetPasswordTextCssValue);
//		 System.out.println("✅ CSS value matches the expected value: " + forgetPasswordCSS);
		 
		// for loop
		 for (String cssValue : textCSSValue) {
	            Assert.assertEquals(forgetPasswordCSS, cssValue);
	            System.out.println("✅ CSS value matches the expected value: " + cssValue);
	        }
		 
	}
	
	@When("User click the Support and Helpdesk button")
	public void user_click_the_support_and_helpdesk_button() {
		loginPage = new LoginPage();
	    loginPage.clickSupportHelpDeskBtn();
	}
	
	
	@Then("User need to check the popup is displayed or not.")
	public void user_need_to_check_the_popup_is_displayed_or_not() {
	    loginPage.closeSupportHelpDeskPopup();
	}
	
	@When("User click the Features button")
	public void user_click_the_features_button() {
	   loginPage = new LoginPage();
	   loginPage.clickFeaturesBtn();
	}
	
	
	@Then("User need to check the features popup is displayed or not.")
	public void user_need_to_check_the_features_popup_is_displayed_or_not() {
	   loginPage.closeFeaturesPopUp();
	}
	
	@When("User click the Services button")
	public void user_click_the_services_button() {
	    loginPage = new LoginPage();
	    loginPage.clickServicesBtn();
	}
	
	
	@Then("User need to check the searvices popup is displayed or not.")
	public void user_need_to_check_the_searvices_popup_is_displayed_or_not() {
	   loginPage.closeServicesPopUp();
	}
	
	@When("User click the Training button")
	public void user_click_the_training_button() {
	   loginPage = new LoginPage();
	   loginPage.clickTrainingBtn();
	}
	
	
	@Then("User need to check the training popup is displayed or not.")
	public void user_need_to_check_the_training_popup_is_displayed_or_not() {
	    loginPage.closeTrainingPopUp();
	}
	
	@When("Without enter anything in username and password and click the sign in button")
	public void without_enter_anything_in_username_and_password_and_click_the_sign_in_button() {
	   loginPage = new LoginPage();
	   loginPage.clickSignIn();
	}
	
	
	@Then("User need to check the valid error message is displayed or not.")
	public void user_need_to_check_the_valid_error_message_is_displayed_or_not() {
	   String expectedErrorMessage = "Please enter your user name and password.";
	   String actualErrorMessage = loginPage.loginAssertionCheck();
	   
	   Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@When("User enter the invalid username and invalid password and click the sign in button")
	public void user_enter_the_invalid_username_and_invalid_password_and_click_the_sign_in_button() {
		String username = "dev002";
		String password = "qaz432";
		loginPage = new LoginPage();
	    loginPage.invalidUserNameAndPassword(username, password);
	    
	}
	
	
	@Then("User need to check the valid error message is displayed or not for invalid username and invalid password.")
	public void user_need_to_check_the_valid_error_message_is_displayed_or_not_for_invalid_username_and_invalid_password() {
	    String expectedErrorMessage = "Invalid Username or Password. Please try again.";
	    String actualErrorMessage = loginPage.loginAssertionCheck();
	    Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	@When("User without enter username and enter valid password and click the sign in button")
	public void user_without_enter_username_and_enter_valid_password_and_click_the_sign_in_button() {
		String pass = "qaz4321";
		loginPage = new LoginPage();
		loginPage.validPasswordWithoutUserName(pass);
	}
	
	
	@Then("User need to check the valid error message is displayed or not for without enter username and enter valid password.")
	public void user_need_to_check_the_valid_error_message_is_displayed_or_not_for_without_enter_username_and_enter_valid_password() {
	    String expectedErrorMessage = "Invalid Username or Password. Please try again.";
	    String actualErrorMessage = loginPage.loginAssertionCheck();
	    Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
	    
	}
	
	
	@When("User enter valid username and without enter password and click the sign in button")
	public void user_enter_valid_username_and_without_enter_password_and_click_the_sign_in_button() {
		String userName = "deva002";
		loginPage = new LoginPage();
		loginPage.validUserNameWithoutPassword(userName);
	}
	
	@Then("User need to check the valid error message is displayed or not for valid username and without enter password.")
	public void user_need_to_check_the_valid_error_message_is_displayed_or_not_for_valid_username_and_without_enter_password() {
		String expectedErrorMessage = "Please enter the password";
	    String actualErrorMessage = loginPage.loginAssertionCheck();
	    Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
	}
	
	
	@When("User click the Remember me checkbox")
	public void user_click_the_remember_me_checkbox() {
	   loginPage = new LoginPage();
	   loginPage.clickRememberMe();
	}
	
	
	@Then("User need to check the Remember me checkbox is enabled or not.")
	public void user_need_to_check_the_remember_me_checkbox_is_enabled_or_not() {
	   loginPage.checkEnabledRememberMe();
	}
	
}
