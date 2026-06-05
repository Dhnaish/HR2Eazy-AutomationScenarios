package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;



@CucumberOptions(features = "src/test/java/feature/LoginPage.feature", glue = { "stepDefination",
"hooks" }, dryRun = !true, plugin = { "pretty", "html:target/cucumber-reports/LoginPageReport.html","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})
public class LoginPageTestRunner extends AbstractTestNGCucumberTests {
	
	

}
