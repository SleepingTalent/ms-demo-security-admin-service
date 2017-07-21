package com.babcock.test.steps;

import com.babcock.test.runtime.RuntimeState;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

public class DataStep extends AbstractStep {

    @Autowired
    RuntimeState runtimeState;

    @Given("^permissions exist$")
    public void permissions_exist() throws Throwable {
        // Would insert database code here
    }

    @Given("^roles exist$")
    public void roles_exist() throws Throwable {
        // Would insert database code here
    }

    @Given("^subjects exist$")
    public void subjects_exist() throws Throwable {
        // Would insert database code here
    }

    @Given("^users exist$")
    public void users_exist() throws Throwable {
        // Would insert database code here
    }
}

