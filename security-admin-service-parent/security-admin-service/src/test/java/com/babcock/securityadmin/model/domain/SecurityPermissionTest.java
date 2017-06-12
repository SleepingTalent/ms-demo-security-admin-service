package com.babcock.securityadmin.model.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SecurityPermissionTest {

    private SecurityPermission securityPermission = new SecurityPermission();

    @Test
    public void getId() {
        securityPermission.setId(32L);
        assertEquals(32L, securityPermission.getId().longValue());
    }

    @Test
    public void getName() {
        securityPermission.setName("document:delete");
        assertEquals("document:delete", securityPermission.getName());
    }

    @Test
    public void getDescription() {
        securityPermission.setDescription("Allow user to delete a document");
        assertEquals("Allow user to delete a document", securityPermission.getDescription());
    }

    @Test
    public void getSecurityRoles() {
        SecurityRole securityRole1 = new SecurityRole("DocumentController", "Document controller role");
        SecurityRole securityRole2 = new SecurityRole("DocumentAdministrator", "Document administrator role");
        List<SecurityRole> securityRoles = new ArrayList<>();
        securityRoles.add(securityRole1);
        securityRoles.add(securityRole2);

        securityPermission.setSecurityRoles(securityRoles);

        assertEquals(2, securityPermission.getSecurityRoles().size());
        assertEquals("DocumentController", securityPermission.getSecurityRoles().get(0).getName());
        assertEquals("DocumentAdministrator", securityPermission.getSecurityRoles().get(1).getName());
    }

    @Test
    public void testConstructor() {
        SecurityPermission securityPermission = new SecurityPermission("document:edit", "Edit a document");
        assertEquals("document:edit", securityPermission.getName());
        assertEquals("Edit a document", securityPermission.getDescription());

    }

}