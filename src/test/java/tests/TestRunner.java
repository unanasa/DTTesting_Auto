package tests;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/Features/ManageTodos.feature",
				glue="stepDefinitions",
				tags= "@smoke or @regression",
				dryRun=false,
				monochrome=true,
				plugin = {"pretty","html:target/HtmlReports/cucumber-report.html",
						"json:target/JsonReports/json-report.json",
						"junit:target/JUnitReports/junit-report.xml",
						"json:target/cucumber.json"
						}
				)

public class TestRunner {


}
