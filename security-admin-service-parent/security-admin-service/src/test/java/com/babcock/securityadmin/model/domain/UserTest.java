package com.babcock.securityadmin.model.domain;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class UserTest {

    User user = new User();

    @Test
    public void getId() {
        user.setId(67L);
        assertEquals(67L, user.getId().longValue());
    }

    @Test
    public void getPayNumber() {
        user.setPayNumber("333444");
        assertEquals("333444", user.getPayNumber());
    }

    @Test
    public void getName() {
        user.setName("Bob Smith");
        assertEquals("Bob Smith", user.getName());
    }

    @Test
    public void getEmail() {
        user.setEmail("bob.smith@email.com");
        assertEquals("bob.smith@email.com", user.getEmail());
    }

    @Test
    public void getManagerUser() {
        User manager = new User();
        manager.setName("Mr Burns");
        user.setManagerUser(manager);
        assertEquals("Mr Burns", user.getManagerUser().getName());
    }

    @Test
    public void getCreatedBySubject() {
        SecuritySubject securitySubject = new SecuritySubject();
        securitySubject.setUsername("step0177");
        user.setCreatedBySubject(securitySubject);
        assertEquals("step0177", user.getCreatedBySubject().getUsername());
    }

    @Test
    public void getCreationDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
        Date date = format.parse("26/03/2014");
        user.setCreationDate(date);
        assertEquals("26/03/2014", format.format(user.getCreationDate()));
    }

    @Test
    public void getSecuritySubject() {
        SecuritySubject securitySubject = new SecuritySubject();
        securitySubject.setUsername("bone9922");
        user.setSecuritySubject(securitySubject);
        assertEquals("bone9922", user.getSecuritySubject().getUsername());
    }

    @Test
    public void getJobTitle() {
        user.setJobTitle("Cheese Maker");
        assertEquals("Cheese Maker", user.getJobTitle());
    }

    @Test
    public void getDepartment() {
        user.setDepartment("Department of Whey");
        assertEquals("Department of Whey", user.getDepartment());
    }

    @Test
    public void getAddress() {
        user.setAddress("12B Rue de la Fromage, Paris");
        assertEquals("12B Rue de la Fromage, Paris", user.getAddress());
    }

    @Test
    public void getLocation() {
        user.setLocation("Paris");
        assertEquals("Paris", user.getLocation());
    }

    @Test
    public void getPhoneNumber() {
        user.setPhoneNumber("0171 2233445566");
        assertEquals("0171 2233445566", user.getPhoneNumber());
    }

    @Test
    public void getLicenceDetails() {
        user.setLicenceDetails("Project 77 Licence");
        assertEquals("Project 77 Licence", user.getLicenceDetails());
    }

    @Test
    public void isManager() {
        assertEquals("N", user.getIsManager());
        user.setIsManager("Y");
        assertEquals("Y", user.getIsManager());
    }

}