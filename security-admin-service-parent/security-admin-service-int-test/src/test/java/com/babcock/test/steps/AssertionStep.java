package com.babcock.test.steps;


import com.babcock.test.runtime.RuntimeState;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class AssertionStep extends AbstractStep {

    @Autowired
    RuntimeState runtimeState;

    @Then("^\"(.*?)\" permissions are returned$")
    public void permissions_are_returned(String count) throws Throwable {
        assertEquals(Integer.valueOf(count).intValue(), runtimeState.getSecurityPermissions().size());
    }

    @Then("^\"(.*?)\" roles are returned$")
    public void roles_are_returned(String count) throws Throwable {
        assertEquals(Integer.valueOf(count).intValue(), runtimeState.getSecurityRole().size());
    }

    @Then("^\"(.*?)\" subjects are returned$")
    public void subjects_are_returned(String count) throws Throwable {
        assertEquals(Integer.valueOf(count).intValue(), runtimeState.getSecuritySubject().size());
    }

    @Then("^\"(.*?)\" users are returned$")
    public void users_are_returned(String count) throws Throwable {
        assertEquals(Integer.valueOf(count).intValue(), runtimeState.getUser().size());
    }
}
