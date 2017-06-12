package com.babcock.securityadmin.controller.request;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoleRequestTest {

    private RoleRequest roleRequest;

    @Before
    public void setUp() throws Exception {
        roleRequest = new RoleRequest();
        roleRequest.setId("747");
        roleRequest.setName("Manager");
        roleRequest.setDescription("Manager role");
        roleRequest.setPermissions("user:add,document:delete,document:add");
    }

    @Test
    public void getId() throws Exception {
        assertEquals("747", roleRequest.getId());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("Manager", roleRequest.getName());
    }

    @Test
    public void getDescription() throws Exception {
        assertEquals("Manager role", roleRequest.getDescription());
    }

    @Test
    public void getPermissions() throws Exception {
        assertEquals("user:add,document:delete,document:add", roleRequest.getPermissions());
    }

}