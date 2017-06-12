package com.babcock.securityadmin.model.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SecurityRoleTest {

    private SecurityRole securityRole = new SecurityRole();

    @Test
    public void getId() {
        securityRole.setId(56L);
        assertEquals(56L, securityRole.getId().longValue());
    }

    @Test
    public void getName() {
        securityRole.setName("Document Controller");
        assertEquals("Document Controller", securityRole.getName());
    }

    @Test
    public void getDescription() {
        securityRole.setDescription("Controller of Document");
        assertEquals("Controller of Document", securityRole.getDescription());
    }

    @Test
    public void getSecuritySubjects() {
        SecuritySubject securitySubject = new SecuritySubject();
        securitySubject.setUsername("walk2345");
        securitySubject.setPassword("pa55wuRdZ");
        securitySubject.setSalt("54L7S@Lt");
        securitySubject.setEnabled(true);
        List<SecuritySubject> securitySubjects = new ArrayList<>();
        securitySubjects.add(securitySubject);
        securityRole.setSecuritySubjects(securitySubjects);
        assertEquals(1, securityRole.getSecuritySubjects().size());
        assertEquals("walk2345", securityRole.getSecuritySubjects().get(0).getUsername());
    }

    @Test
    public void getSecurityPermissions() {
        SecurityPermission permission1 = new SecurityPermission("document:add", "Add a document");
        SecurityPermission permission2 = new SecurityPermission("document:edit", "Edit a document");
        SecurityPermission permission3 = new SecurityPermission("document:delete", "Delete a document");
        List<SecurityPermission> securityPermissions = new ArrayList<>();
        securityPermissions.add(permission1);
        securityPermissions.add(permission2);
        securityPermissions.add(permission3);

        securityRole.setSecurityPermissions(securityPermissions);

        assertEquals(3, securityRole.getSecurityPermissions().size());
        assertEquals("document:add", securityRole.getSecurityPermissions().get(0).getName());
        assertEquals("document:edit", securityRole.getSecurityPermissions().get(1).getName());
        assertEquals("document:delete", securityRole.getSecurityPermissions().get(2).getName());
    }

}