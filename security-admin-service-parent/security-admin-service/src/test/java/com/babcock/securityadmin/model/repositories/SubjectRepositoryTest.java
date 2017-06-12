package com.babcock.securityadmin.model.repositories;

import com.babcock.securityadmin.model.domain.SecurityRole;
import com.babcock.securityadmin.model.domain.SecuritySubject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = SecuritySubject.class)
public class SubjectRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;

    private SecuritySubject securitySubject;
    private SecurityRole role1;
    private SecurityRole role2;

    @Before
    public void setUp() throws Exception {
        List<SecurityRole> securityRoles = new ArrayList<>();
        role1 = new SecurityRole("Manager", "Manager role");
        role2 = new SecurityRole("Administrator", "Administrator role");
        entityManager.persist(role1);
        entityManager.persist(role2);
        securityRoles.add(role1);
        securityRoles.add(role2);
        securitySubject = new SecuritySubject();
        securitySubject.setUsername("bogs1122");
        securitySubject.setPassword("p455wuRdz");
        securitySubject.setSalt("54Lt");
        securitySubject.setEnabled(true);
        securitySubject.setSecurityRoles(securityRoles);
        entityManager.persist(securitySubject);
    }

    @After
    public void tearDown() throws Exception {
        entityManager.remove(securitySubject);
        entityManager.remove(role1);
        entityManager.remove(role2);
    }

    @Test
    public void findAll() {
        assertEquals(1, subjectRepository.findAll().size());
    }

}