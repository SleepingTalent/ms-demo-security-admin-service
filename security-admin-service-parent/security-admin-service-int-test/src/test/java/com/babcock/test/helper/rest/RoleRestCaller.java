package com.babcock.test.helper.rest;

import com.babcock.securityadmin.model.domain.SecurityRole;
import com.babcock.test.runtime.RuntimeState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RoleRestCaller implements RestCaller {

    @Autowired
    RuntimeState runtimeState;

    @Autowired
    RestTemplate restTemplate;

    @Value("${security.admin.service.url}")
    String baseUrl;

    @Override
    public void callUrl(String httpMethod, String url) {
        List<SecurityRole> response = restTemplate.getForObject(baseUrl+"/securityadmin/roles", List.class);
        runtimeState.setSecurityRole(response);
    }
}
