package com.babcock.test.steps;


import com.babcock.test.helper.rest.RestHelper;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class RestStep extends AbstractStep {

    @Autowired
    RestHelper restHelper;

    @When("^get all \"(.*?)\" is called$")
    public void a_is_called_on_the_url(String url) throws Throwable {
        restHelper.callRestUrl("get",url);
    }
}
