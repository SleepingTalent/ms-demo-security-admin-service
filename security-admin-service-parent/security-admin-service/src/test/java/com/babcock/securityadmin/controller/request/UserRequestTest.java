package com.babcock.securityadmin.controller.request;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserRequestTest {

    private UserRequest userRequest;

    @Before
    public void setUp() throws Exception {
        userRequest = new UserRequest();
        userRequest.setId("29");
        userRequest.setPayNumber("234234J");
        userRequest.setName("Bob Smart");
        userRequest.setEmail("bs@email.com");
        userRequest.setJobTile("Draughtsman");
        userRequest.setDepartment("Engineering");
        userRequest.setLocation("Rosyth");
    }

    @Test
    public void getId() throws Exception {
        assertEquals("29", userRequest.getId());
    }

    @Test
    public void getPayNumber() throws Exception {
        assertEquals("234234J", userRequest.getPayNumber());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("Bob Smart", userRequest.getName());
    }

    @Test
    public void getEmail() throws Exception {
        assertEquals("bs@email.com", userRequest.getEmail());
    }

    @Test
    public void getJobTile() throws Exception {
        assertEquals("Draughtsman", userRequest.getJobTile());
    }

    @Test
    public void getDepartment() throws Exception {
        assertEquals("Engineering", userRequest.getDepartment());
    }

    @Test
    public void getLocation() throws Exception {
        assertEquals("Rosyth", userRequest.getLocation());
    }

}