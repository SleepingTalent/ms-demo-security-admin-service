package com.babcock.test.steps;

import com.babcock.test.configuration.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@Import(TestConfiguration.class)
public class AbstractStep {

}
