package com.babcock.securityadmin.model.repositories;

import com.babcock.securityadmin.model.domain.SecurityPermission;
import com.babcock.securityadmin.model.domain.SecurityRole;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = SecurityPermission.class)
public class PermissionRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private PermissionRepository permissionRepository;

    private SecurityPermission securityPermission1;
    private SecurityPermission securityPermission2;
    private SecurityPermission unrelatedPermission;

    @Before
    public void setUp() throws Exception {
        securityPermission1 = new SecurityPermission("document:add", "Allows user to add a document");
        securityPermission2 = new SecurityPermission("document:delete", "Allows user to delete a document");
        unrelatedPermission = new SecurityPermission("admin:*", "Admin all");

        entityManager.persist(securityPermission1);
        entityManager.persist(securityPermission2);
        entityManager.persist(unrelatedPermission);
    }

    @After
    public void tearDown() throws Exception {
        entityManager.remove(securityPermission1);
        entityManager.remove(securityPermission2);
        entityManager.remove(unrelatedPermission);
    }

    @Test
    public void countPermissions() {
        assertEquals(3, permissionRepository.count());
    }

    @Test
    public void countByName() {
        assertEquals(0L, permissionRepository.countByName("permission:that-does-not-exist").longValue());
        assertEquals(1L, permissionRepository.countByName(securityPermission1.getName()).longValue());
    }

    @Test
    public void getAll() {
        List<SecurityPermission> securityPermissions = permissionRepository.findAll();
        assertEquals(3, securityPermissions.size());
        assertEquals("document:add", securityPermissions.get(0).getName());
        assertEquals("Allows user to add a document", securityPermissions.get(0).getDescription());
        assertEquals("document:delete", securityPermissions.get(1).getName());
        assertEquals("Allows user to delete a document", securityPermissions.get(1).getDescription());
        assertEquals("admin:*", securityPermissions.get(2).getName());
        assertEquals("Admin all", securityPermissions.get(2).getDescription());
    }

    @Test
    public void findPermissionsForRole() {
        List<SecurityPermission> securityPermissions = new ArrayList<>();
        securityPermissions.add(securityPermission1);
        securityPermissions.add(securityPermission2);

        SecurityRole securityRole = new SecurityRole("ChangeMgr", "Change manager");
        List<SecurityRole> securityRoles = new ArrayList<>();
        securityRoles.add(securityRole);
        securityRole.setSecurityPermissions(securityPermissions);
        entityManager.persist(securityRole);

        List<SecurityPermission> allSecurityPermissions = permissionRepository.findAll();
        List<SecurityPermission> securityPermissionsForRoles = permissionRepository.findBySecurityRoles(securityRoles);

        assertEquals(3, allSecurityPermissions.size());
        assertEquals(2, securityPermissionsForRoles.size());
        assertEquals("document:add", securityPermissionsForRoles.get(0).getName());
        assertEquals("Allows user to add a document", securityPermissionsForRoles.get(0).getDescription());
        assertEquals("document:delete", securityPermissionsForRoles.get(1).getName());
        assertEquals("Allows user to delete a document", securityPermissionsForRoles.get(1).getDescription());
    }

}