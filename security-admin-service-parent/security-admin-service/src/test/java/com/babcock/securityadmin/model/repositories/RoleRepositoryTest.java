package com.babcock.securityadmin.model.repositories;

import com.babcock.securityadmin.model.domain.SecurityPermission;
import com.babcock.securityadmin.model.domain.SecurityRole;
import com.babcock.securityadmin.model.domain.SecuritySubject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = SecurityRole.class)
public class RoleRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private SecurityRole securityRole1;
    private SecurityRole securityRole2;
    private SecurityPermission permission1;
    private SecurityPermission permission2;

    @Before
    public void setUp() throws Exception {
        permission1 = new SecurityPermission("document:add", "Add a document");
        permission2 = new SecurityPermission("document:delete", "delete a document");
        entityManager.persist(permission1);
        entityManager.persist(permission2);
        List<SecurityPermission> securityPermissions = new ArrayList<>();
        securityPermissions.add(permission1);
        securityPermissions.add(permission2);

        securityRole1 = new SecurityRole("DocController", "Document controller");
        securityRole1.setSecurityPermissions(securityPermissions);

        securityRole2 = new SecurityRole("DocManage", "Document manager");
        securityRole2.setSecurityPermissions(securityPermissions);

        entityManager.persist(securityRole1);
        entityManager.persist(securityRole2);
    }

    @After
    public void tearDown() throws Exception {
        entityManager.remove(permission1);
        entityManager.remove(permission2);
        entityManager.remove(securityRole1);
        entityManager.remove(securityRole2);
    }

    @Test
    public void findAll() {
        List<SecurityRole> securityRoles = roleRepository.findAll();
        assertEquals(2, securityRoles.size());
    }

    @Test
    public void countByName() {
        assertEquals(0L, roleRepository.countByName("RoleNotThere").longValue());
        assertEquals(1L, roleRepository.countByName("DocController").longValue());
    }


    @Test
    public void findRolesBySecuritySubject() {
        List<SecurityRole> securityRoles = new ArrayList<>();
        securityRoles.add(securityRole1);

        SecuritySubject securitySubject = new SecuritySubject();
        securitySubject.setUsername("walk2345");
        securitySubject.setPassword("pa55wuRdZ");
        securitySubject.setSalt("54L7S@Lt");
        securitySubject.setEnabled(true);
        securitySubject.setSecurityRoles(securityRoles);
        List<SecuritySubject> securitySubjects = new ArrayList<>();
        securitySubjects.add(securitySubject);
        entityManager.persist(securitySubject);
        securityRole1.setSecuritySubjects(securitySubjects);
        List<SecurityRole> allSecurityRoles = roleRepository.findAll();
        assertEquals(2, allSecurityRoles.size());
        List<SecurityRole> subjectsSecurityRoles = roleRepository.findBySecuritySubjects(securitySubjects);
        assertEquals(1, subjectsSecurityRoles.size());
    }

    @Test
    public void findByName_success() {
        SecurityRole foundRole = roleRepository.findByName("DocController");
        assertNotNull(foundRole);
        assertEquals("DocController", foundRole.getName());
        assertEquals("Document controller", foundRole.getDescription());
    }

    @Test
    public void findByName_not_found() {
        SecurityRole roleToFind = roleRepository.findByName("RoleDoesNotExist");
        assertNull(roleToFind);
    }

}