package hooks;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import Utilities.DriverSetup;
import Utilities.ExcelUtilities;
import Utilities.ScreenshotUtils;
import Utilities.TestContext;
import Utilities.Utilities;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooksetup {
	
	 @Before(order = 0)
	    public void setupBrowser() throws IOException {

	        Properties property = Utilities.getProperty();

	        String browser = property.getProperty("browser");
	        String headless = property.getProperty("headless");

	        DriverSetup.initDriver(browser, headless);
	    }

	    @Before(order = 1)
	    public void loadRowData(Scenario scenario) {

	        // Execute only for Excel-based runners
	        if (TestContext.getTotalScenarios() > 0) {

	            int row = TestContext.getCurrentRow();

	            Map<String, String> rowData =
	                    ExcelUtilities.getRowData(row);

	            System.out.println("Row " + row +
	                    " | Running next scenario...");

	            TestContext.setRowData(rowData);

	            TestContext.incrementScenarioCount();
	        }
	    }

	    @After
	    public void quitDriver(Scenario scenario) {

	        if (scenario.isFailed()) {

	            try {

	                if (DriverSetup.getDriver() != null) {

	                    String safeName = scenario.getName()
	                            .replaceAll("[^a-zA-Z0-9]", "_");

	                    byte[] screenshotBytes =
	                            ((TakesScreenshot) DriverSetup.getDriver())
	                                    .getScreenshotAs(OutputType.BYTES);

	                    scenario.attach(
	                            screenshotBytes,
	                            "image/png",
	                            "Failure Screenshot"
	                    );

	                    String screenshotPath =
	                            ScreenshotUtils.saveScreenshot(
	                                    screenshotBytes,
	                                    safeName
	                            );

	                    ExtentCucumberAdapter.getCurrentStep()
	                            .fail(
	                                    "❌ Scenario Failed: "
	                                            + scenario.getName(),

	                                    MediaEntityBuilder
	                                            .createScreenCaptureFromPath(
	                                                    screenshotPath)
	                                            .build()
	                            );

	                    System.out.println(
	                            "📸 Screenshot attached successfully"
	                    );
	                }

	            } catch (Exception e) {

	                System.out.println(
	                        "❌ Screenshot capture failed: "
	                                + e.getMessage()
	                );
	            }
	        }

	        if (DriverSetup.getDriver() != null) {
	            DriverSetup.quitDriver();
	        }
	    }
	
	
}
