package com.babcock.test.steps;

import com.babcock.test.helper.asserter.WaitForService;
import com.babcock.test.helper.asserter.WaitUntilAssertionError;
import com.babcock.test.runtime.RuntimeState;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.fail;

public class BeforeAfterSteps extends AbstractStep{

    @Autowired
    RuntimeState runtimeState;

    @Autowired
    RestTemplate restTemplate;

    @Value("${security.admin.service.url}")
    String baseUrl;

    //@Value("${zuul.service.url}")
    //String zuulUrl;

    private static boolean serviceUnavailable = false;

    @Before
    public void setUp(Scenario scenario) throws InterruptedException {
        runtimeState.initialise();
        runtimeState.setScenario(scenario);

        WaitForService waitForSecurityAdminService = new WaitForService(baseUrl + "/info", restTemplate);
        waitForSecurityAdminService.setMaxWaitTime(420000);

        if(serviceUnavailable) {
            fail("security-admin-service docker environment unavailable");
        }

        System.out.println("waiting for service : " + baseUrl + "/info");

        try {
            waitForSecurityAdminService.performAssertion();
        }catch (WaitUntilAssertionError wae) {
            serviceUnavailable = true;
            fail("security-admin-service docker environment unavailable");
        }
    }

    @After
    public void tearDown(Scenario scenario) {

    }
}

