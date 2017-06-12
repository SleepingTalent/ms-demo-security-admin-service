package com.babcock.securityadmin.controller.request;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PermissionRequestTest {

    private PermissionRequest permissionRequest;

    @Before
    public void setUp() throws Exception {
        permissionRequest = new PermissionRequest();
        permissionRequest.setId("23");
        permissionRequest.setName("file:delete");
        permissionRequest.setDescription("Delete a file");
    }

    @Test
    public void getId() throws Exception {
        assertEquals("23", permissionRequest.getId());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("file:delete", permissionRequest.getName());
    }

    @Test
    public void getDescription() throws Exception {
        assertEquals("Delete a file", permissionRequest.getDescription());
    }

}