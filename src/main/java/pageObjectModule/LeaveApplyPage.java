package pageObjectModule;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.DriverSetup;

public class LeaveApplyPage {

	WebDriver driver;
	WebDriverWait wait;

	public LeaveApplyPage() {
		this.driver = DriverSetup.getDriver();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
	}

	// private String appliedDate;

	By applyLeaveBtn = By.xpath("//input[@id='ctl00_ContentPlaceHolder1_btnSaveDown']");
	By leaveTypeDropdown = By.xpath("//select[@id='ctl00_ContentPlaceHolder1_ddlLeaveType']");
	By fromDate = By.xpath("//input[@id='ctl00_ContentPlaceHolder1_txtLeaveFrom']");
	By toDate = By.xpath("//input[@id='ctl00_ContentPlaceHolder1_txtLeaveTo']");
	By reasonTextArea = By.xpath("//textarea[@id='ctl00_ContentPlaceHolder1_txtReason']");
	By fileUpload = By.xpath("//input[@id='ctl00_ContentPlaceHolder1_fileupload']");
	By publicHoliday = By.xpath("//td[@class='Public-leave']/a");
	By offDay = By.xpath("//td[@class='OffDay']/a");
	By restDay = By.xpath("//td[@class='RestDay']/a");
	By normalDay = By.xpath("//td[@title='Available']/a");
	By fromDateText = By.xpath("//span[contains(@id,'lblFromDate')]");
	By deletBtn = By.xpath("//input[contains(@name,'Delete')]");
	By carryForwardRadioBtn = By.id("ctl00_ContentPlaceHolder1_rbtnCarryForward");
	By carryForwardCount = By.id("ctl00_ContentPlaceHolder1_lblavailableCountAndDate");
	By entitledRadioBtn = By.id("ctl00_ContentPlaceHolder1_rbtnEntitled");
	By leaveDetailsTable = By.id("TblLeaveRequest");
	By grabEarnLeave = By.xpath(
			"//table[@id='ctl00_ContentPlaceHolder1_GVRemaingLeaves']//tr[td[normalize-space()='Annual Leave']]/td[5]");
	By grabCarryForwardCount = By.xpath(
			"//table[@id='ctl00_ContentPlaceHolder1_GVRemaingLeaves']//tr[td[normalize-space()='Annual Leave']]/td[4]");
	By halfDayCheckBox = By.id("ctl00_ContentPlaceHolder1_chkHalfday");
	By morningSessionRadioBtn = By.id("ctl00_ContentPlaceHolder1_rbtnleavesession_0");
	By eveningSessionRadioBtn = By.id("ctl00_ContentPlaceHolder1_rbtnleavesession_1");
	By leaveSession = By.id("ctl00_ContentPlaceHolder1_LblLeaveSession");

	public void checkLeaveApplyPage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(applyLeaveBtn));
		System.out.println("User navigate to the Leave Apply page successfully");
	}

	public void applyLeaveLogicBasedOnOpenLeaveSetting(String openLeaveValue, String leaveType, String fromDateValue,
			String toDateValue, String reason) {

		if (openLeaveValue.equalsIgnoreCase("0")) {
			System.out.println("Open Leave setting is disabled. User cannot apply for Public,Rest Day, Off-Day leave.");

			selectLeaveType(leaveType);
			fromDateSelection(fromDateValue);
			toDateSelection(toDateValue);
			enterReasonForLeave(reason);
			fileUploadDocument();

		} else if (openLeaveValue.equalsIgnoreCase("1")) {
			System.out.println("Open Leave setting is enabled. User can apply for all days leave.");
		} else {
			System.out.println("Invalid Open Leave setting value: " + openLeaveValue);
		}

	}

	String publicOffRestLeaveDate;

	public void holidayLeaveApplyLogic(String openLeaveValue, String leaveType, String reason, String leaveDay) {

		if (openLeaveValue.equalsIgnoreCase("0")) {
			selectLeaveType(leaveType);
			annualLeaveLogic(leaveType);
			if (leaveDay.equalsIgnoreCase("Public Holiday")) {
				publicOffRestLeaveDate(publicHoliday);
			} else if (leaveDay.equalsIgnoreCase("Off-Day")) {
				publicOffRestLeaveDate(offDay);
			} else if (leaveDay.equalsIgnoreCase("Rest Day")) {
				publicOffRestLeaveDate(restDay);
			} else {
				System.out.println("Invalid leave day: " + leaveDay);
			}
			System.out.println("Open Leave setting is disabled. User cannot apply for Public,Rest Day, Off-Day leave.");

		} else if (openLeaveValue.equalsIgnoreCase("1")) {
			System.out.println("Open Leave setting is enabled. User can apply for Public,Rest Day, Off-Day leave.");
			selectLeaveType(leaveType);
			annualLeaveLogic(leaveType);
			if (leaveDay.equalsIgnoreCase("Public Holiday")) {
				publicOffRestLeaveDate = publicOffRestLeaveDate(publicHoliday);
			} else if (leaveDay.equalsIgnoreCase("Off-Day")) {
				publicOffRestLeaveDate = publicOffRestLeaveDate(offDay);
			} else if (leaveDay.equalsIgnoreCase("Rest Day")) {
				publicOffRestLeaveDate = publicOffRestLeaveDate(restDay);
			} else {
				System.out.println("Invalid leave day: " + leaveDay);
			}
			enterReasonForLeave(reason);
			fileUploadDocument();
		} else {
			System.out.println("Invalid Open Leave setting value: " + openLeaveValue);
		}

	}

	public void annualLeaveLogic(String leaveType) {

		if (leaveType.equalsIgnoreCase("Annual Leave")) {
			System.out.println("User select Annual Leave type. So, user can apply for random date leave.");
			wait.until(ExpectedConditions.visibilityOfElementLocated(carryForwardRadioBtn));

			boolean carryForwardRadioButton = driver.findElement(carryForwardRadioBtn).isSelected();
			System.out.println("Is Carry Forward radio button selected? " + carryForwardRadioButton);
			// check the carry forward count
			String text = driver.findElement(carryForwardCount).getText();
			if (text.equalsIgnoreCase("No Leaves Entitled.")) {
				// carry forward count is zero. So, user can apply for entitled leave.
				driver.findElement(entitledRadioBtn).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(carryForwardCount));
				String text1 = driver.findElement(carryForwardCount).getText();
				System.out.println("Entitled leave count and date: " + text1);

				// Split using escaped dot
				String[] split = text1.split("\\.");
				String val = split[0].trim();

				// Convert to integer properly
				int count = Integer.parseInt(val);

				System.out.println("Entitled leave count: " + count);

				if (count > 0) {
					System.out.println(
							"User can apply for entitled leave as carry forward count is zero but entitled leave count is greater than zero.");
				} else {
					System.out.println(
							"User cannot apply for entitled leave as both carry forward and entitled leave count are zero.");
				}

			} else {
				// carry forward count is greater than zero. So, user can apply for entitled
				// leave.

			}

		} else {
			System.out.println("User select other than Annual Leave type. Ignore some process.");
		}

	}

	private String apliedDate;

	public void normalLeaveApplyLogic(String openLeaveValue, String leaveType, String reason, String leaveCount) {

		if (openLeaveValue.equalsIgnoreCase("0")) {
			if (leaveType.equalsIgnoreCase("Annual Leave")) {
				String annualLeaveCountVal = annualLeaveCount(leaveCount);
				System.out.println("Annual Leave count value is: " + annualLeaveCountVal);
				selectLeaveType(leaveType);
				annualLeaveLogic(leaveType);
				apliedDate = uniqueDateSelection(annualLeaveCountVal);
				enterReasonForLeave(reason);
				fileUploadDocument();
			} else if (leaveType.equalsIgnoreCase("Medical Leave")) {
				System.out.println("User select Medical Leave type. So, user can apply for today date leave.");
				selectLeaveType(leaveType);
				apliedDate = medicalLeaveLogic(leaveCount);
				enterReasonForLeave(reason);
				fileUploadDocument();

			}

			else {
				System.out.println("Open Leave setting is enabled. User can apply random date leave.");
				selectLeaveType(leaveType);
				annualLeaveLogic(leaveType);
				apliedDate = randomDateLeaveLogic();
				enterReasonForLeave(reason);
				fileUploadDocument();
			}

		} else if (openLeaveValue.equalsIgnoreCase("1")) {
			System.out.println("Open Leave setting is enabled. User can apply today date leave.");
			if (leaveType.equalsIgnoreCase("Annual Leave")) {
				String annualLeaveCountVal = annualLeaveCount(leaveCount);
				System.out.println("Annual Leave count value is: " + annualLeaveCountVal);
				selectLeaveType(leaveType);
				annualLeaveLogic(leaveType);
				apliedDate = uniqueDateSelection(annualLeaveCountVal);

				enterReasonForLeave(reason);
				fileUploadDocument();
			} else if (leaveType.equalsIgnoreCase("Medical Leave")) {
				System.out.println("User select Medical Leave type. So, user can apply for today date leave.");
				selectLeaveType(leaveType);
				apliedDate = medicalLeaveLogic(leaveCount);
				enterReasonForLeave(reason);
				fileUploadDocument();

			}

			else {
				System.out.println("Open Leave setting is enabled. User can apply random date leave.");
				selectLeaveType(leaveType);
				annualLeaveLogic(leaveType);
				apliedDate = randomDateLeaveLogic();
				enterReasonForLeave(reason);
				fileUploadDocument();
			}
		} else {
			System.out.println("Invalid Open Leave setting value: " + openLeaveValue);
		}

	}

	public void halfDayLogic(String sessionValue) {

		wait.until(ExpectedConditions.visibilityOfElementLocated(halfDayCheckBox));
		boolean displayedHaldLeave = driver.findElement(halfDayCheckBox).isDisplayed();

		if (displayedHaldLeave) {
			System.out.println("Half day checkbox is displayed for the user.");
			driver.findElement(halfDayCheckBox).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(leaveSession));

			if (sessionValue.equalsIgnoreCase("Morning")) {
				System.out.println("User select Morning Session for half day leave.");
				driver.findElement(morningSessionRadioBtn).click();
			} else {
				System.out.println("User select Evening Session for half day leave.");
				driver.findElement(eveningSessionRadioBtn).click();
			}

		} else {
			System.out.println("Half day checkbox is not displayed for the user.");
		}

	}

	

	public void halfDayLeaveLogic(String openLeaveValue, String leaveType, String reason, String leaveCount,
			String halfDayLeaveSession) {

		if (openLeaveValue.equalsIgnoreCase("0")) {
			if (leaveType.equalsIgnoreCase("Annual Leave")) {
				String annualLeaveCountVal = annualLeaveCount(leaveCount);
				System.out.println("Annual Leave count value is: " + annualLeaveCountVal);
				selectLeaveType(leaveType);
				annualLeaveLogic(leaveType);
				apliedDate = uniqueDateSelection(annualLeaveCountVal);
				halfDayLogic(halfDayLeaveSession);
				enterReasonForLeave(reason);
				fileUploadDocument();
			} else if (leaveType.equalsIgnoreCase("Medical Leave")) {
				System.out.println("User select Medical Leave type. So, user can apply for today date leave.");
				selectLeaveType(leaveType);
				apliedDate = medicalLeaveLogic(leaveCount);
				halfDayLogic(halfDayLeaveSession);
				enterReasonForLeave(reason);
				fileUploadDocument();

			}

			else {
				System.out.println("Open Leave setting is enabled. User can apply random date leave.");
				selectLeaveType(leaveType);
				annualLeaveLogic(leaveType);
				apliedDate = randomDateLeaveLogic();
				halfDayLogic(halfDayLeaveSession);
				enterReasonForLeave(reason);
				fileUploadDocument();
			}

		} else if (openLeaveValue.equalsIgnoreCase("1")) {
			System.out.println("Open Leave setting is enabled. User can apply today date leave.");
			if (leaveType.equalsIgnoreCase("Annual Leave")) {
				String annualLeaveCountVal = annualLeaveCount(leaveCount);
				System.out.println("Annual Leave count value is: " + annualLeaveCountVal);
				selectLeaveType(leaveType);
				annualLeaveLogic(leaveType);
				apliedDate = uniqueDateSelection(annualLeaveCountVal);
				halfDayLogic(halfDayLeaveSession);
				enterReasonForLeave(reason);
				fileUploadDocument();
			} else if (leaveType.equalsIgnoreCase("Medical Leave")) {
				System.out.println("User select Medical Leave type. So, user can apply for today date leave.");
				selectLeaveType(leaveType);
				apliedDate = medicalLeaveLogic(leaveCount);
				halfDayLogic(halfDayLeaveSession);
				enterReasonForLeave(reason);
				fileUploadDocument();

			}

			else {
				System.out.println("Open Leave setting is enabled. User can apply random date leave.");
				selectLeaveType(leaveType);
				annualLeaveLogic(leaveType);
				apliedDate = randomDateLeaveLogic();
				halfDayLogic(halfDayLeaveSession);
				enterReasonForLeave(reason);
				fileUploadDocument();
			}
		} else {
			System.out.println("Invalid Open Leave setting value: " + openLeaveValue);
		}

	}

	public String annualLeaveCount(String val) {

		String trim = val.split(" Day")[0].trim();
		return trim;

	}

	public String medicalLeaveLogic(String medicalLeave) {

		String trim = medicalLeave.split(" Day")[0].trim();
		int medicalVal = Integer.parseInt(trim);

		LocalDate today = LocalDate.now(); // current date
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

		for (int i = 0; i <= medicalVal; i++) {

			LocalDate checkDate = today.plusDays(i);
			int day = checkDate.getDayOfMonth();

			String xpath = "//table[@id='ctl00_ContentPlaceHolder1_CalLeave']//td[@title='Available']/a[text()='" + day
					+ "']";

			System.out.println(xpath);

			List<WebElement> elements = driver.findElements(By.xpath(xpath));

			if (!elements.isEmpty()) {

				String formattedDate = checkDate.format(formatter);

				System.out.println("✅ Found Available Date: " + 
				
						
						formattedDate);

				fromDateSelection(formattedDate);
				toDateSelection(formattedDate);

				return formattedDate; // return full formatted date
			}
		}

		return "No available date within next " + medicalVal + " days";
	}

	public String uniqueDateSelection(String leavePolicyValue) {

		int int1 = Integer.parseInt(leavePolicyValue);

		LocalDate date = LocalDate.now().plusDays(int1);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

		while (true) {

			String dayStr = String.valueOf(date.getDayOfMonth());
			String xpath;
			
			if(dayStr.equals("29")|| dayStr.equals("30")|| dayStr.equals("31")) {
				 xpath = "(//table[@id='ctl00_ContentPlaceHolder1_CalLeave']//td/a[text()='" + dayStr + "'])[2]";
			}else {
				 xpath = "//table[@id='ctl00_ContentPlaceHolder1_CalLeave']//td/a[text()='" + dayStr + "']";
			}

		//	String xpath = "//table[@id='ctl00_ContentPlaceHolder1_CalLeave']//td/a[text()='" + dayStr + "']";
			System.out.println(xpath);
			List<WebElement> elements = driver.findElements(By.xpath(xpath));

			if (elements.size() > 0) {

				WebElement parentTd = elements.get(0).findElement(By.xpath("./parent::td"));
				String title = parentTd.getAttribute("title");

				System.out.println("Checking: " + dayStr + " -> " + title);

				if ("Available".equalsIgnoreCase(title)) {
					// Format and return date (NO CLICK)
					String formattedDate = date.format(formatter);
					System.out.println("✅ Found Available Date: " + formattedDate);

					fromDateSelection(formattedDate);
					toDateSelection(formattedDate);
					return formattedDate;

				}
			}

			// Move to next day
			date = date.plusDays(1);
		}

	}

	public void appiliedSameDate(String openLeaveValue, String leaveType, String reason) {

		if (openLeaveValue.equalsIgnoreCase("0")) {

			if (leaveType.equalsIgnoreCase("Annual Leave")) {
				System.out.println("User select Annual Leave type. So, user can apply for same date leave.");
				selectLeaveType(leaveType);
				annualLeaveLogic(leaveType);
				fromDateSelection(apliedDate);
				toDateSelection(apliedDate);
				enterReasonForLeave(reason);
				fileUploadDocument();
			} else {
				System.out.println("User select other than Annual Leave type. So, user can apply for same date leave.");
				System.out.println("Open Leave setting is enabled. User can apply same date leave.");
				selectLeaveType(leaveType);
				fromDateSelection(apliedDate);
				toDateSelection(apliedDate);
				enterReasonForLeave(reason);
				fileUploadDocument();
			}

		} else if (openLeaveValue.equalsIgnoreCase("1")) {
			System.out.println("Open Leave setting is enabled. User can apply today date leave.");
			if (leaveType.equalsIgnoreCase("Annual Leave")) {
				System.out.println("User select Annual Leave type. So, user can apply for same date leave.");
				selectLeaveType(leaveType);
				annualLeaveLogic(leaveType);
				fromDateSelection(apliedDate);
				toDateSelection(apliedDate);
				enterReasonForLeave(reason);
				fileUploadDocument();
			} else {
				System.out.println("User select other than Annual Leave type. So, user can apply for same date leave.");
				System.out.println("Open Leave setting is enabled. User can apply same date leave.");
				selectLeaveType(leaveType);
				fromDateSelection(apliedDate);
				toDateSelection(apliedDate);
				enterReasonForLeave(reason);
				fileUploadDocument();
			}
		} else {
			System.out.println("Invalid Open Leave setting value: " + openLeaveValue);
		}

	}

	public void halfDayAppliedSameDate(String openLeaveValue, String leaveType, String reason, String halfDayLeaveSession) {
		
		System.out.println("Applied date for half day leave: " + apliedDate);
		
		if (openLeaveValue.equalsIgnoreCase("0")) {

			if (leaveType.equalsIgnoreCase("Annual Leave")) {
				System.out.println("User select Annual Leave type. So, user can apply for same date leave.");
				selectLeaveType(leaveType);
				annualLeaveLogic(leaveType);
				fromDateSelection(apliedDate);
				toDateSelection(apliedDate);
				halfDayLogic(halfDayLeaveSession);
				enterReasonForLeave(reason);
				fileUploadDocument();
			} else {
				System.out.println("User select other than Annual Leave type. So, user can apply for same date leave.");
				System.out.println("Open Leave setting is enabled. User can apply same date leave.");
				selectLeaveType(leaveType);
				fromDateSelection(apliedDate);
				toDateSelection(apliedDate);
				halfDayLogic(halfDayLeaveSession);
				enterReasonForLeave(reason);
				fileUploadDocument();
			}

		} else if (openLeaveValue.equalsIgnoreCase("1")) {
			System.out.println("Open Leave setting is enabled. User can apply today date leave.");
			if (leaveType.equalsIgnoreCase("Annual Leave")) {
				System.out.println("User select Annual Leave type. So, user can apply for same date leave.");
				selectLeaveType(leaveType);
				annualLeaveLogic(leaveType);
				fromDateSelection(apliedDate);
				toDateSelection(apliedDate);
				halfDayLogic(halfDayLeaveSession);
				enterReasonForLeave(reason);
				fileUploadDocument();
			} else {
				System.out.println("User select other than Annual Leave type. So, user can apply for same date leave.");
				System.out.println("Open Leave setting is enabled. User can apply same date leave.");
				selectLeaveType(leaveType);
				fromDateSelection(apliedDate);
				toDateSelection(apliedDate);
				halfDayLogic(halfDayLeaveSession);
				enterReasonForLeave(reason);
				fileUploadDocument();
			}
		} else {
			System.out.println("Invalid Open Leave setting value: " + openLeaveValue);
		}
	}

	public String randomDateLeaveLogic() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(fromDate));
		int sizeOfDate = driver.findElements(normalDay).size();
		System.out.println("Total number of normal day available for leave application: " + sizeOfDate);

		if (sizeOfDate == 0) {
			throw new RuntimeException("No available dates found");
		}

		// Generate random index (1-based for XPath)
		Random random = new Random();
		int randomIndex = random.nextInt(sizeOfDate) + 1;

		System.out.println("Random index selected: " + randomIndex);

		// Modify xpath
		By randomNormalDay = By.xpath("(//td[@title='Available']/a)[" + randomIndex + "]");
		String date = driver.findElement(randomNormalDay).getText().trim();
		LocalDate currentDate = LocalDate.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-yyyy");
		String result = currentDate.format(formatter);

		String finalDate = date + "-" + result;

		System.out.println(finalDate);

		fromDateSelection(finalDate);
		toDateSelection(finalDate);

		return finalDate;

	}

	public void checkAppliedDate() {

		String inputDate = apliedDate;

		String[] parts = inputDate.split("-");

		String day = parts[0];
		String month = parts[1];
		String year = parts[2];

		// Add leading zero if needed
		if (day.length() == 1) {
			day = "0" + day;
		}

		String finalDate = day + " " + month + " " + year;

		System.out.println(finalDate);

		List<WebElement> elements = driver.findElements(fromDateText);
		for (WebElement ele : elements) {
			String text = ele.getText();
			if (text.equals(finalDate)) {
				System.out.println("Applied date is displayed in the From Date field.");
				ele.findElement(deletBtn).click();

				driver.switchTo().alert().accept();

				break;
			}
		}

	}

	public void checkAppliedPublicRestOffDay(String openLeaveSettingValue) {

		if (openLeaveSettingValue.equalsIgnoreCase("0")) {
			System.out.println(
					"Open Leave setting is disabled. So, user cannot apply for Public,Rest Day, Off-Day leave. Hence, applied date is not displayed in the From Date field.");
		} else if (openLeaveSettingValue.equalsIgnoreCase("1")) {
			System.out.println(
					"Open Leave setting is enabled. So, user can apply for Public,Rest Day, Off-Day leave. Hence, applied date is displayed in the From Date field.");

			String inputDate = publicOffRestLeaveDate;

			String[] parts = inputDate.split("-");

			String day = parts[0];
			String month = parts[1];
			String year = parts[2];

			// Add leading zero if needed
			if (day.length() == 1) {
				day = "0" + day;
			}

			String finalDate = day + " " + month + " " + year;

			System.out.println(finalDate);

			List<WebElement> elements = driver.findElements(fromDateText);
			for (WebElement ele : elements) {
				String text = ele.getText();
				if (text.equals(finalDate)) {
					System.out.println("Applied date is displayed in the From Date field.");
					ele.findElement(deletBtn).click();

					driver.switchTo().alert().accept();

					break;
				}
			}

		} else {
			System.out.println("Invalid Open Leave setting value: " + openLeaveSettingValue);
		}

	}

	public String publicOffRestLeaveDate(By holiday) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(fromDate));
		String date = driver.findElement(holiday).getText().trim();

		LocalDate currentDate = LocalDate.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-yyyy");
		String result = currentDate.format(formatter);

		String finalDate = date + "-" + result;
		System.out.println(finalDate);

		fromDateSelection(finalDate);
		toDateSelection(finalDate);

		return finalDate;
	}

	public void selectLeaveType(String leaveType) {

		WebElement element = driver.findElement(leaveTypeDropdown);
		Select select = new Select(element);
		select.selectByVisibleText(leaveType);

	}

	public void fromDateSelection(String fromDateSelection) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(fromDate));
		WebElement dateInput = driver.findElement(fromDate);

		// Remove readonly attribute
		((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly')", dateInput);

		// Enter date
		dateInput.clear();
		dateInput.sendKeys(fromDateSelection, Keys.TAB);
	}

	public void toDateSelection(String toDateSelection) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		// Retry loop to handle stale element
		int attempts = 0;
		while (attempts < 3) {
			try {
				// Wait for page to load completely
				wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState")
						.toString().equals("complete"));

				// Re-find the element each attempt
				WebElement dateInput = wait.until(ExpectedConditions.visibilityOfElementLocated(toDate));

				// Remove readonly
				((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly')", dateInput);

				// Clear and enter value
				dateInput.clear();
				dateInput.sendKeys(toDateSelection);

				// Trigger onchange for ASP.NET postback
				((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('change'));",
						dateInput);

				// Success → break loop
				break;

			} catch (StaleElementReferenceException e) {
				attempts++;
				System.out.println("StaleElementReferenceException caught, retrying... attempt " + attempts);
			}
		}

		if (attempts == 3) {
			throw new RuntimeException("Failed to set date due to stale element");
		}
	}

	public void enterReasonForLeave(String reason) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(reasonTextArea));
		driver.findElement(reasonTextArea).sendKeys(reason);
	}

	public void fileUploadDocument() {

		String filePath = System.getProperty("user.dir") + "\\File\\dummy-pdf_2.pdf";

		driver.findElement(fileUpload).sendKeys(filePath);

	}

	public void clickApplyLeaveBtn(String openLeaveValue) {
		if (openLeaveValue.equalsIgnoreCase("0")) {
			System.out
					.println("User cannot apply for Public,Rest Day, Off-Day leave. So, Apply button is not clicked.");
		} else if (openLeaveValue.equalsIgnoreCase("1")) {
			System.out.println("User can apply for all days leave. So, Apply button is clicked.");
			driver.findElement(applyLeaveBtn).click();
		} else {
			System.out.println("Invalid Open Leave setting value: " + openLeaveValue);
		}
	}

	public void clickedApplyLeaveBtn() {

		driver.findElement(applyLeaveBtn).click();
		System.out.println("Apply Leave button is clicked.");

	}

	public void checkValidInvalidErrorMessage(String openLeaveValue) {

		if (openLeaveValue.equalsIgnoreCase("0")) {
			WebElement errorMessage = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_lblMsg")));
			System.out.println("Error message displayed: " + errorMessage.getText());
			errorMessage.getText().contains("Public Holiday | Rest Day | Off-Day| Leave applied successfully");
			System.out.println(
					"Error message is valid for Public Holiday/Rest/Off-Day/Normal leave application when Open Leave setting is disabled.");
		} else if (openLeaveValue.equalsIgnoreCase("1")) {
			WebElement successMessage = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_lblMsg")));
			System.out.println("Success message displayed: " + successMessage.getText());
			successMessage.getText().contains("Leave applied successfully");
		} else {
			System.out.println("Invalid Open Leave setting value: " + openLeaveValue);
		}

	}

	public void checkValidDeleteErrorMessage() {

		String expectedMessage = "Record deleted successfully.";

		WebElement deleteMessage = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_lblMsg")));
		String actualMessage = deleteMessage.getText();
		System.out.println("Delete message displayed: " + actualMessage);

		Assert.assertEquals(actualMessage, expectedMessage);
	}

	public void checkValidDeleteErrorMessageForPublicRestOffDay(String openLeaveSettingValue) {

		if (openLeaveSettingValue.equalsIgnoreCase("0")) {
			System.out.println(
					"Open Leave setting is disabled. So, user cannot apply for Public,Rest Day, Off-Day leave. Hence, delete message is not displayed after deletion.");
		} else if (openLeaveSettingValue.equalsIgnoreCase("1")) {
			System.out.println(
					"Open Leave setting is enabled. So, user can apply for Public,Rest Day, Off-Day leave. Hence, delete message is displayed after deletion.");

			String expectedMessage = "Record deleted successfully.";

			WebElement deleteMessage = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_lblMsg")));
			String actualMessage = deleteMessage.getText();
			System.out.println("Delete message displayed: " + actualMessage);

			Assert.assertEquals(actualMessage, expectedMessage);
		} else {
			System.out.println("Invalid Open Leave setting value: " + openLeaveSettingValue);
		}

	}

	public void checkValidLeaveAlreadyAppliedErrorMessage() {

		String expectedMessage = "Leave already applied for this period";

		WebElement errorMessage = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_lblMsg")));
		String actualMessage = errorMessage.getText();
		System.out.println("Error message displayed: " + actualMessage);

		Assert.assertEquals(actualMessage, expectedMessage);
	}

	public void checkLeaveBalanceErrorMessage() {
		WebElement errorMessage = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_lblMsg")));
		String actualMessage = errorMessage.getText();
		System.out.println("Error message displayed: " + actualMessage);

		if (actualMessage.contains("Applied leave cannot exceed the available leave balance")) {
			System.out.println("Error message is valid for applied leave exceeding available leave balance.");
		} else {
			System.out.println("Unexpected error message: " + actualMessage);
		}
	}

	
	
	
	
	int earnLeaveCount;

	public void earnLeaveCountLogic() {

		wait.until(ExpectedConditions.visibilityOfElementLocated(leaveDetailsTable));

		String carryForwardCount = driver.findElement(grabCarryForwardCount).getText();
		// (int) Double.parseDouble(carryForwardCout);

		double value = Double.parseDouble(carryForwardCount);

		if (value > 0) {
			System.out.println("Carry forward count is greater than zero. So, user can apply for carry forward leave.");
			earnLeaveCount = (int) value;
			System.out.println("Grabbed carry Leave count with date: " + carryForwardCount);
		} else {
			System.out.println(
					"Carry forward count is zero. So, user can apply for entitled leave if entitled leave count is greater than zero. Otherwise, user cannot apply for leave.");
			String grabEarnLeaveCount = driver.findElement(grabEarnLeave).getText();
			System.out.println("Grabbed Earn Leave count with date: " + grabEarnLeaveCount);

			earnLeaveCount = (int) Double.parseDouble(grabEarnLeaveCount);
		}

	}

	public void getFromAndToDate(String openLeaveValue, String annualLeaveCountVal) {

		int applyDays = earnLeaveCount + 3;
		int offsetDays = Integer.parseInt(annualLeaveCountVal);

		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");

		// ✅ Start Date = Today + offset
		LocalDate today = LocalDate.now();
		LocalDate startDate = today.plusDays(offsetDays);

		List<LocalDate> filteredDates = new ArrayList<>();

		while (filteredDates.size() < applyDays) {

			// ✅ Get current calendar month & year
			String monthYear = driver.findElement(By.xpath("//table[@class='CalendarTitle']//td[2]")).getText().trim();

			YearMonth yearMonth = YearMonth.parse(monthYear, monthFormatter);

			// ✅ Align startDate if first iteration
			if (filteredDates.isEmpty()) {
				if (startDate.getMonthValue() != yearMonth.getMonthValue()
						|| startDate.getYear() != yearMonth.getYear()) {

					startDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
				}
			}

			// ✅ Get calendar cells
			List<WebElement> allCells;

			if (openLeaveValue.equalsIgnoreCase("0")) {
				// Only working days
				allCells = driver.findElements(By.xpath("//td[@class='DayStyle' and not(@disabled)]"));
			} else {
				// All selectable days
				allCells = driver.findElements(By.xpath(
						"//td[not(@disabled) and (contains(@class,'DayStyle') or contains(@class,'OffDay') or contains(@class,'Public-leave') or contains(@class,'RestDay'))]"));
			}

			// ✅ Process dates
			for (WebElement cell : allCells) {

				String dayText = cell.getText().trim();
				if (dayText.isEmpty())
					continue;

				int day = Integer.parseInt(dayText);

				LocalDate currentDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), day);

				// Skip past dates
				if (currentDate.isBefore(startDate))
					continue;

				// Avoid duplicates (important when switching months)
				if (filteredDates.contains(currentDate))
					continue;

				filteredDates.add(currentDate);

				System.out.println(filteredDates);

				if (filteredDates.size() == applyDays)
					break;
			}

			// ✅ If still not enough → go to next month
			if (filteredDates.size() < applyDays) {

				System.out.println("Moving to next month...");

				// 👉 Click next month button (update locator if needed)
				driver.findElement(By.xpath("//a[@title='Go to the next month']")).click();

				// 👉 Wait for calendar refresh (replace with WebDriverWait if possible)
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		// ✅ Debug logs
		System.out.println("Apply Days Required: " + applyDays);
		System.out.println("Collected Days: " + filteredDates.size());

		if (filteredDates.size() < applyDays) {
			throw new RuntimeException("Not enough valid days even after navigating months");
		}

		// ✅ Get From & To Dates
		LocalDate fromDate = filteredDates.get(0);
		LocalDate toDate = filteredDates.get(filteredDates.size() - 1);

		String fromDateValue = fromDate.format(outputFormatter);
		String toDateValue = toDate.format(outputFormatter);

		System.out.println("From Date: " + fromDateValue);
		System.out.println("To Date: " + toDateValue);

		// ✅ Perform selection
		fromDateSelection(fromDateValue);
		toDateSelection(toDateValue);
	}

	public void basedOnEarnLeaveApplyAnnualLeave(String openLeaveValue, String leaveType, String reason,
			String leaveCount) {

		String annualLeaveCountVal = annualLeaveCount(leaveCount);
		System.out.println("Annual Leave count value is: " + annualLeaveCountVal);

		selectLeaveType(leaveType);
		annualLeaveLogic(leaveType);
		getFromAndToDate(openLeaveValue, annualLeaveCountVal);
		enterReasonForLeave(reason);
		fileUploadDocument();

	}

}
