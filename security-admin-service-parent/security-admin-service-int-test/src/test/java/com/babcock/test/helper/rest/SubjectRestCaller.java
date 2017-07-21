package com.babcock.test.helper.rest;

import com.babcock.securityadmin.model.domain.SecuritySubject;
import com.babcock.test.runtime.RuntimeState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class SubjectRestCaller implements RestCaller {

    @Autowired
    RuntimeState runtimeState;

    @Autowired
    RestTemplate restTemplate;

    @Value("${security.admin.service.url}")
    String baseUrl;

    @Override
    public void callUrl(String httpMethod, String url) {
        List<SecuritySubject> response = restTemplate.getForObject(baseUrl+"/securityadmin/subjects", List.class);
        runtimeState.setSecuritySubject(response);
    }
}
