package com.babcock.securityadmin.controller.request;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SubjectRequestTest {

    private SubjectRequest subjectRequest;

    @Before
    public void setUp() throws Exception {
        subjectRequest = new SubjectRequest();
        subjectRequest.setId("72");
        subjectRequest.setUsername("WILL9999");
        subjectRequest.setPassword("laskjdhgWaiu2jhn");
        subjectRequest.setRoles("ChangeManager,DocumentController,DocumentChecker");
    }

    @Test
    public void getId() throws Exception {
        assertEquals("72", subjectRequest.getId());
    }

    @Test
    public void getUsername() throws Exception {
        assertEquals("WILL9999", subjectRequest.getUsername());
    }

    @Test
    public void getPassword() throws Exception {
        assertEquals("laskjdhgWaiu2jhn", subjectRequest.getPassword());
    }

    @Test
    public void getRoles() throws Exception {
        assertEquals("ChangeManager,DocumentController,DocumentChecker", subjectRequest.getRoles());
    }

}