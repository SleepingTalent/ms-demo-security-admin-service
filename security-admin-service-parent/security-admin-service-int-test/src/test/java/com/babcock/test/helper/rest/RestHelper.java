package com.babcock.test.helper.rest;

import com.babcock.securityadmin.model.domain.SecurityRole;
import com.babcock.securityadmin.model.domain.SecuritySubject;
import com.babcock.securityadmin.model.domain.User;
import com.babcock.test.runtime.RuntimeState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RestHelper {

    @Autowired
    RuntimeState runtimeState;

    @Autowired
    RestTemplate restTemplate;

    @Value("${security.admin.service.url}")
    String baseUrl;

    @Autowired
    PermissionRestCaller permissionRestCaller;

    @Autowired
    RoleRestCaller roleRestCaller;

    @Autowired
    SubjectRestCaller subjectRestCaller;

    @Autowired
    UserRestCaller userRestCaller;

    public void callRestUrl(String httpMethod, String url) {

        if(url.equals("permissions")) {
            permissionRestCaller.callUrl(httpMethod, url);
        }else if(url.equals("roles")) {
            roleRestCaller.callUrl(httpMethod, url);
        }else if(url.equals("subjects")) {
            subjectRestCaller.callUrl(httpMethod, url);
        }else if(url.equals("users")) {
            userRestCaller.callUrl(httpMethod, url);
            //List<User> response = restTemplate.getForObject(baseUrl+"/securityadmin/users", List.class);
            //runtimeState.setUser(response);
        }
    }
}
