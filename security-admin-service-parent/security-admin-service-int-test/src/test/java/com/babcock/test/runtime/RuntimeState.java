package com.babcock.test.runtime;

import com.babcock.securityadmin.model.domain.SecurityRole;
import com.babcock.securityadmin.model.domain.SecuritySubject;
import com.babcock.securityadmin.model.domain.User;
import cucumber.api.Scenario;
import org.springframework.stereotype.Component;

import java.security.SecurityPermission;
import java.util.List;

@Component
public class RuntimeState {

    private Scenario scenario;
    private String host;
    private List<SecurityPermission> securityPermissions;
    private List<SecurityRole> securityRole;
    private List<User> user;
    private List<SecuritySubject> securitySubject;

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void initialise() {
    }

    public void setSecurityPermissions(List<SecurityPermission> securityPermissions) {
        this.securityPermissions = securityPermissions;
    }

    public List<SecurityPermission> getSecurityPermissions() {
        return securityPermissions;
    }

    public void setSecurityRole(List<SecurityRole> securityRole) {
        this.securityRole = securityRole;
    }

    public List<SecurityRole> getSecurityRole() {
        return securityRole;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public List<User> getUser() {
        return user;
    }

    public void setSecuritySubject(List<SecuritySubject> securitySubject) {
        this.securitySubject = securitySubject;
    }

    public List<SecuritySubject> getSecuritySubject() {
        return securitySubject;
    }
}
