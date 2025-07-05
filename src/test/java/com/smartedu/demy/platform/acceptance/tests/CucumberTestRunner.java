package com.smartedu.demy.platform.acceptance.tests;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.acme.banking.platform.acceptance.tests.steps",
        plugin = {"pretty", "html:target/cucumber.html"}
)
public class CucumberTestRunner {
}
