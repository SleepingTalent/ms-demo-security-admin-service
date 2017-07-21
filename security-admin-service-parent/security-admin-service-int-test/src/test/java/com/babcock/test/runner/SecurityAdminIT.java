package com.babcock.test.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(format={"pretty","html:target/cucumber-report","json:target/cucumber-report/result.json"}, glue="com.babcock.test",
        features="classpath:features", tags={"~@wip"}, monochrome = true)
public class SecurityAdminIT {

}
