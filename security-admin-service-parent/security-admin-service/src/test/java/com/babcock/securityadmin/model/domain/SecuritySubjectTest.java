package com.babcock.securityadmin.model.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SecuritySubjectTest {

    private SecuritySubject securitySubject = new SecuritySubject();

    @Test
    public void getId() {
        securitySubject.setId(23L);
        assertEquals(23L, securitySubject.getId().longValue());
    }

    @Test
    public void getUsername() {
        securitySubject.setUsername("logo2233");
        assertEquals("logo2233", securitySubject.getUsername());
    }

    @Test
    public void getPassword() {
        securitySubject.setPassword("P!cTur3");
        assertEquals("P!cTur3", securitySubject.getPassword());
    }

    @Test
    public void getSalt() {
        securitySubject.setSalt("S4L7S4L7");
        assertEquals("S4L7S4L7", securitySubject.getSalt());
    }

    @Test
    public void isEnabled() {
        assertFalse(securitySubject.isEnabled());
        securitySubject.setEnabled(true);
        assertTrue(securitySubject.isEnabled());
    }

    @Test
    public void getUser() {
        User user = new User();
        user.setName("Eddie T. Eagle");
        securitySubject.setUser(user);
        assertEquals("Eddie T. Eagle", securitySubject.getUser().getName());
    }

    @Test
    public void getRolesForSecuritySubject() {
        SecurityRole role1 = new SecurityRole("DocController", "Document Controller");
        SecurityRole role2 = new SecurityRole("WorkflowMgr", "Workflow Manager");
        List<SecurityRole> securityRoles = new ArrayList<>();
        securityRoles.add(role1);
        securityRoles.add(role2);
        securitySubject.setSecurityRoles(securityRoles);

        assertEquals(2, securitySubject.getSecurityRoles().size());
        assertEquals("DocController", securitySubject.getSecurityRoles().get(0).getName());
        assertEquals("WorkflowMgr", securitySubject.getSecurityRoles().get(1).getName());
    }

}