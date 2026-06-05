package stepDefination;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import Utilities.DriverSetup;
import Utilities.TestContext;
import Utilities.Utilities;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjectModule.GetDataPage;
import pageObjectModule.HomePage;
import pageObjectModule.LeaveApplyPage;
import pageObjectModule.LoginPage;

public class LeaveApplyTest {

	WebDriver driver = DriverSetup.getDriver();

	LoginPage login;
	HomePage homePage;
	GetDataPage getDataPage;
	LeaveApplyPage leaveApplyPage;

	String checkOpenLeaveSettingValue;

	String LeaveType = TestContext.LeaveType;
	String reason = TestContext.Reason;
	String userName = TestContext.UserName;
	String password = TestContext.Password;
	String staffNo = TestContext.StaffNo;
	String orgID = TestContext.OrgID;

	@Given("User enters valid Authentication username and Authentication password")
	public void user_enters_valid_authentication_username_and_authentication_password() throws IOException {

		Properties property = Utilities.getProperty();

		String url = property.getProperty("appUrl");

		driver.get(url);

		login = new LoginPage();
		login.enterUserName(userName);
		login.enterPassword(password);

	}

	@When("User click the login button")
	public void user_click_the_login_button() {
		login.clickSignIn();
	}

	@Then("Check user navigate to the Home page")
	public void check_user_navigate_to_the_home_page() {
		homePage = new HomePage();

		homePage.closePopUp();

		homePage.waitForHomePage();
		System.out.println("User navigate to the Home page successfully");
	}

	@Given("Hit the getData url and check the open leave setting.")
	public void hit_the_get_data_url_and_check_the_open_leave_setting() throws InterruptedException {

		getDataPage = new GetDataPage();
		getDataPage.hitGetDataUrl();
		getDataPage.enterQueryForOpenLeaveSetting(staffNo);
		checkOpenLeaveSettingValue = getDataPage.checkOpenLeaveSetting();

	}

	@Given("User click the Leave Module")
	public void user_click_the_leave_module() throws InterruptedException {
		homePage.clickLeaveApply();

	}

	@Given("User need to check the leave module page is open or not.")
	public void user_need_to_check_the_leave_module_page_is_open_or_not() {
		leaveApplyPage = new LeaveApplyPage();
		leaveApplyPage.checkLeaveApplyPage();
	}

	@Given("User select the leave type and select the from date and to date and enter the reason for leave.")
	public void user_select_the_leave_type_and_select_the_from_date_and_to_date_and_enter_the_reason_for_leave()
			throws InterruptedException {

		String LeaveType = "UnPaid Leave";
		String fromDate = "15-Apr-2026";
		String toDate = "15-Apr-2026";
		String reason = "test";

		leaveApplyPage.applyLeaveLogicBasedOnOpenLeaveSetting(checkOpenLeaveSettingValue, LeaveType, fromDate, toDate,
				reason);

		Thread.sleep(5000);
	}

	@Given("Based on open leave setting, user try to apply public holiday leave")
	public void based_on_open_leave_setting_user_try_to_apply_public_holiday_leave() {

		String leaveDay = "Public Holiday";
		leaveApplyPage.holidayLeaveApplyLogic(checkOpenLeaveSettingValue, LeaveType, reason, leaveDay);

	}

	@When("User click the Apply Leave Button")
	public void user_click_the_apply_leave_button() {
		leaveApplyPage.clickApplyLeaveBtn(checkOpenLeaveSettingValue);
	}

	@Then("User need to check valid error message for successful leave application.")
	public void user_need_to_check_valid_error_message_for_successful_leave_application() {
		leaveApplyPage.checkValidInvalidErrorMessage(checkOpenLeaveSettingValue);
	}

	@Given("Based on open leave setting, user try to apply rest day leave")
	public void based_on_open_leave_setting_user_try_to_apply_rest_day_leave() {
		String leaveDay = "Rest Day";
		leaveApplyPage.holidayLeaveApplyLogic(checkOpenLeaveSettingValue, LeaveType, reason, leaveDay);
	}

	@Given("Based on open leave setting, user try to apply off day leave")
	public void based_on_open_leave_setting_user_try_to_apply_off_day_leave() {
		String leaveDay = "Off-Day";
		leaveApplyPage.holidayLeaveApplyLogic(checkOpenLeaveSettingValue, LeaveType, reason, leaveDay);
	}

	String checkLeaveValue;

	@Given("Based on open leave setting, user try to apply normal day leave")
	public void based_on_open_leave_setting_user_try_to_apply_normal_day_leave() {
		// String leaveDay = "Off-Day";
		getDataPage = new GetDataPage();
		if (LeaveType.equals("Annual Leave")) {
			getDataPage.hitGetDataUrl();
			getDataPage.enterQueryForAnnualLeaveCount(orgID);
			checkLeaveValue = getDataPage.checkAnnualLeaveCount();
			System.out.println("Annual Leave Count value is: " + checkLeaveValue);
		} else if (LeaveType.equals("Medical Leave")) {
			getDataPage.hitGetDataUrl();
			getDataPage.enterQueryForAnnualLeaveCount(orgID);
			checkLeaveValue = getDataPage.checkMedicalLeaveCount();
			System.out.println("Medical Leave Count value is: " + checkLeaveValue);
		}

		leaveApplyPage.normalLeaveApplyLogic(checkOpenLeaveSettingValue, LeaveType, reason, checkLeaveValue);

	}

	@When("User clicked the Apply Leave Button")
	public void user_clicked_the_apply_leave_button() {
		leaveApplyPage.clickedApplyLeaveBtn();
	}

	@Then("User need to check the applied leave is display in the leave list or not and click the delete button for applied leave.")
	public void user_need_to_check_the_applied_leave_is_display_in_the_leave_list_or_not_and_click_the_delete_button_for_applied_leave() {
		leaveApplyPage.checkAppliedDate();
	}

	@Then("User need to check valid error message for successful leave deletion.")
	public void user_need_to_check_valid_error_message_for_successful_leave_deletion() {
		leaveApplyPage.checkValidDeleteErrorMessage();
	}

	@Then("User apply the same date leave")
	public void user_apply_the_same_date_leave() {

		leaveApplyPage.appiliedSameDate(checkOpenLeaveSettingValue, LeaveType, reason);
	}

	@Then("User need to check valid error message for unsuccessful leave application.")
	public void user_need_to_check_valid_error_message_for_unsuccessful_leave_application() {
		leaveApplyPage.checkValidLeaveAlreadyAppliedErrorMessage();
	}

	@Then("User need to check the applied public\\/rest\\/off day leave is display in the leave list or not and click the delete button for applied leave.")
	public void user_need_to_check_the_applied_public_rest_off_day_leave_is_display_in_the_leave_list_or_not_and_click_the_delete_button_for_applied_leave() {
		leaveApplyPage.checkAppliedPublicRestOffDay(checkOpenLeaveSettingValue);
	}

	@Then("User need to check valid error message for public\\/rest\\/off day successful deletion.")
	public void user_need_to_check_valid_error_message_for_public_rest_off_day_successful_deletion() {
		leaveApplyPage.checkValidDeleteErrorMessageForPublicRestOffDay(checkOpenLeaveSettingValue);
	}

	@Given("User need to check earn leave count before applying leave.")
	public void user_need_to_check_earn_leave_count_before_applying_leave() {
		leaveApplyPage.earnLeaveCountLogic();

	}

	@Given("Based on earn leave count, user try to apply normal day leave more than earn leave count")
	public void based_on_earn_leave_count_user_try_to_apply_normal_day_leave_more_than_earn_leave_count() {

		getDataPage = new GetDataPage();

		if (LeaveType.equals("Annual Leave")) {
			getDataPage.hitGetDataUrl();
			getDataPage.enterQueryForAnnualLeaveCount(orgID);
			checkLeaveValue = getDataPage.checkAnnualLeaveCount();
			System.out.println("Annual Leave Count value is: " + checkLeaveValue);

			leaveApplyPage.basedOnEarnLeaveApplyAnnualLeave(checkOpenLeaveSettingValue, LeaveType, reason,
					checkLeaveValue);

		} else {
			System.out.println("This test case is not applicable for this leave type");

		}

	}

	@Then("User need to check valid error message for unsuccessful leave balance message.")
	public void user_need_to_check_valid_error_message_for_unsuccessful_leave_balance_message() {
		leaveApplyPage.checkLeaveBalanceErrorMessage();
	}

	@Given("User try to apply hald day leave for Morning Session.")
	public void user_try_to_apply_hald_day_leave_for_morning_session() {

		String session = "Morning";

		getDataPage = new GetDataPage();
		if (LeaveType.equals("Annual Leave")) {
			getDataPage.hitGetDataUrl();
			getDataPage.enterQueryForAnnualLeaveCount(orgID);
			checkLeaveValue = getDataPage.checkAnnualLeaveCount();
			System.out.println("Annual Leave Count value is: " + checkLeaveValue);
		} else if (LeaveType.equals("Medical Leave")) {
			getDataPage.hitGetDataUrl();
			getDataPage.enterQueryForAnnualLeaveCount(orgID);
			checkLeaveValue = getDataPage.checkMedicalLeaveCount();
			System.out.println("Medical Leave Count value is: " + checkLeaveValue);
		}

		leaveApplyPage.halfDayLeaveLogic(checkOpenLeaveSettingValue, LeaveType, reason, checkLeaveValue, session);
	}

	@Then("User apply the same date half day leave")
	public void user_apply_the_same_date_half_day_leave() {
		String session = "Morning";
		leaveApplyPage.halfDayAppliedSameDate(checkOpenLeaveSettingValue, LeaveType, reason, session);
	}

	@Given("User try to apply hald day leave for Evening Session.")
	public void user_try_to_apply_hald_day_leave_for_evening_session() {
		String session = "Evening";

		getDataPage = new GetDataPage();
		if (LeaveType.equals("Annual Leave")) {
			getDataPage.hitGetDataUrl();
			getDataPage.enterQueryForAnnualLeaveCount(orgID);
			checkLeaveValue = getDataPage.checkAnnualLeaveCount();
			System.out.println("Annual Leave Count value is: " + checkLeaveValue);
		} else if (LeaveType.equals("Medical Leave")) {
			getDataPage.hitGetDataUrl();
			getDataPage.enterQueryForAnnualLeaveCount(orgID);
			checkLeaveValue = getDataPage.checkMedicalLeaveCount();
			System.out.println("Medical Leave Count value is: " + checkLeaveValue);
		}

		leaveApplyPage.halfDayLeaveLogic(checkOpenLeaveSettingValue, LeaveType, reason, checkLeaveValue, session);
	}

	@Then("User apply the same date half day leave - Evening Session")
	public void user_apply_the_same_date_half_day_leave_evening_session() {
		String session = "Evening";
		leaveApplyPage.halfDayAppliedSameDate(checkOpenLeaveSettingValue, LeaveType, reason, session);
	}
	
	@When("User check the valid error message for unsuccessful leave application for this conditions.")
	public void user_check_the_valid_error_message_for_unsuccessful_leave_application_for_this_conditions() {
	    
	}

}
