package runner;

import org.testng.annotations.DataProvider;

import Utilities.ExcelUtilities;
import Utilities.TestContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;



@CucumberOptions(features = "src/test/java/feature/LeaveApply.feature", glue = { "stepDefination",
"hooks" }, dryRun = !true, plugin = { "pretty", "html:target/cucumber-reports/LeaveApplyTestReport.html","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})
public class LeaveApplyTestRunner extends AbstractTestNGCucumberTests {
	
	
	
	
	 @Override
	    @DataProvider(parallel = false)
	    public Object[][] scenarios() {

	        Object[][] cucumberScenarios = super.scenarios();

	        try {

	            ExcelUtilities.loadExcel(
	                    System.getProperty("user.dir")
	                            + "/Excel/LeaveApply.xlsx",

	                    "Sheet1"
	            );

	        } catch (Exception e) {

	            e.printStackTrace();
	        }

	        int totalRows = ExcelUtilities.getRowCount();

	        int totalScenarios = cucumberScenarios.length;

	        TestContext.setTotalScenarios(totalScenarios);

	        System.out.println("✅ Total Rows       : "
	                + totalRows);

	        System.out.println("✅ Total Scenarios  : "
	                + totalScenarios);

	        System.out.println("✅ Total Executions : "
	                + (totalRows * totalScenarios));

	        Object[][] result =
	                new Object[totalRows * totalScenarios][];

	        int index = 0;

	        for (int row = 1; row <= totalRows; row++) {

	            for (int scenarioIndex = 0;
	                 scenarioIndex < totalScenarios;
	                 scenarioIndex++) {

	                result[index++] =
	                        cucumberScenarios[scenarioIndex];
	            }
	        }

	        return result;
	    }
	

}
