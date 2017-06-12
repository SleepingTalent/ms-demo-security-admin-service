package com.babcock.securityadmin.controller;

import com.babcock.securityadmin.controller.request.PermissionRequest;
import com.babcock.securityadmin.controller.request.RoleRequest;
import com.babcock.securityadmin.controller.request.SubjectRequest;
import com.babcock.securityadmin.controller.request.UserRequest;
import com.babcock.securityadmin.model.domain.SecurityPermission;
import com.babcock.securityadmin.model.domain.SecurityRole;
import com.babcock.securityadmin.model.domain.SecuritySubject;
import com.babcock.securityadmin.model.domain.User;
import com.babcock.securityadmin.model.exception.PermissionCreateException;
import com.babcock.securityadmin.model.exception.RoleCreateException;
import com.babcock.securityadmin.service.SecurityAdminService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityAdminController.class)
@EntityScan("com.babcock.securityadmin.model.domain")
@ActiveProfiles("test")
public class SecurityAdminControllerTest {

    @Autowired
    private SecurityAdminController securityAdminController;

    @MockBean
    private SecurityAdminService securityAdminService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private List<SecurityRole> allSecurityRoles;
    private List<SecuritySubject> allSecuritySubjects;
    private List<SecurityPermission> allSecurityPermissions;

    @Before
    public void setUp() throws Exception {
        allSecuritySubjects = new ArrayList<>();
        allSecuritySubjects.add(newSecuritySubject("call1234", true));
        allSecuritySubjects.add(newSecuritySubject("simo9999", true));

        allSecurityRoles = new ArrayList<>();
        SecurityRole securityRole1 = new SecurityRole("Admin", "Administrator role");
        SecurityRole securityRole2 = new SecurityRole("Manager", "Manager role");
        SecurityRole securityRole3 = new SecurityRole("ReadOnly", "ReadOnly role");
        allSecurityRoles.add(securityRole1);
        allSecurityRoles.add(securityRole2);
        allSecurityRoles.add(securityRole3);

        allSecurityPermissions = new ArrayList<>();
        allSecurityPermissions.add(new SecurityPermission());
    }

    @Test
    public void findAllSecurityRoles() {
        when(securityAdminService.findAllSecurityRoles()).thenReturn(allSecurityRoles);

        securityAdminController.findAllRoles();

        verify(securityAdminService, times(1)).findAllSecurityRoles();
    }

    @Test
    public void findAllSecurityPermissions() {
        when(securityAdminService.findAllSecurityPermissions()).thenReturn(allSecurityPermissions);

        securityAdminController.findAllPermissions();

        verify(securityAdminService, times(1)).findAllSecurityPermissions();
    }

    @Test
    public void createSecurityRole_success() throws Exception {

        SecurityRole role = new SecurityRole("ReviewerRole", "Role for document reviewer");

        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setName("ReviewerRole");
        roleRequest.setDescription("Role for document reviewer");

        when(securityAdminService.createSecurityRole(roleRequest)).thenReturn(role);

        SecurityRole createdRole = securityAdminController.createSecurityRole(roleRequest);

        verify(securityAdminService, times(1)).createSecurityRole(roleRequest);
        assertSame(role, createdRole);
    }

    @Test
    public void createSecurityRole_fail_roleAlreadyExists() throws Exception {
        expectedException.expect(RoleCreateException.class);
        expectedException.expectMessage("Role 'ReviewerRole' already exists");

        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setName("ReviewerRole");
        roleRequest.setDescription("Role for document reviewer");

        when(securityAdminService.createSecurityRole(roleRequest)).thenThrow(new RoleCreateException("SecurityRole 'ReviewerRole' already exists"));

        SecurityRole createdRole = securityAdminController.createSecurityRole(roleRequest);

        verify(securityAdminService, times(1)).createSecurityRole(roleRequest);
        assertNull(createdRole);
    }

    @Test
    public void createSecurityPermission_success() throws Exception {
        SecurityPermission securityPermission = new SecurityPermission("file:delete", "Permission to delete a file");

        PermissionRequest permissionRequest = new PermissionRequest();
        permissionRequest.setName("file:delete");
        permissionRequest.setDescription("Permission to delete a file");

        when(securityAdminService.createSecurityPermission(permissionRequest)).thenReturn(securityPermission);

        SecurityPermission createdPermission = securityAdminController.createSecurityPermission(permissionRequest);

        verify(securityAdminService, times(1)).createSecurityPermission(permissionRequest);
        assertSame(securityPermission, createdPermission);
    }

    @Test
    public void createSecurityPermission_fail_permissionAlreadyExists() throws Exception {
        expectedException.expect(PermissionCreateException.class);
        expectedException.expectMessage("ss");

        PermissionRequest permissionRequest = new PermissionRequest();
        permissionRequest.setName("file:delete");
        permissionRequest.setDescription("Permission to delete a file");

        when(securityAdminService.createSecurityPermission(permissionRequest)).thenThrow(new PermissionCreateException("SecurityPermission 'file:delete' already exists"));

        SecurityPermission createdPermission = securityAdminController.createSecurityPermission(permissionRequest);

        verify(securityAdminService, times(1)).createSecurityPermission(permissionRequest);
        assertNull(createdPermission);
    }

    @Test
    public void findAllSecuritySubjects() {
        when(securityAdminService.findAllSecuritySubjects()).thenReturn(allSecuritySubjects);

        securityAdminController.findAllSecuritySubjects();

        verify(securityAdminService, times(1)).findAllSecuritySubjects();
    }

    @Test
    public void findAllUsers() {
        List<User> allUsers = new ArrayList<>();
        allUsers.add(new User());
        when(securityAdminService.findAllUsers()).thenReturn(allUsers);

        securityAdminController.findAllUsers();

        verify(securityAdminService, times(1)).findAllUsers();
    }

    @Test
    public void editSecurityRole() {
        RoleRequest roleRequest = new RoleRequest();
        SecurityRole editedSecurityRole = new SecurityRole("DocumentController", "Doc Controller Role");
        when(securityAdminService.editSecurityRole(roleRequest)).thenReturn(editedSecurityRole);

        SecurityRole securityRole = securityAdminController.editSecurityRole(roleRequest);

        verify(securityAdminService, times(1)).editSecurityRole(roleRequest);
        assertEquals("DocumentController", securityRole.getName());
    }

    @Test
    public void editSecurityPermission() {
        PermissionRequest permissionRequest = new PermissionRequest();
        SecurityPermission editedSecurityPermission = new SecurityPermission("document:create", "Doc Create permission");
        when(securityAdminService.editSecurityPermission(permissionRequest)).thenReturn(editedSecurityPermission);

        SecurityPermission securityPermission = securityAdminController.editSecurityPermission(permissionRequest);

        verify(securityAdminService, times(1)).editSecurityPermission(permissionRequest);
        assertEquals("document:create", securityPermission.getName());
    }

    @Test
    public void createSecuritySubject() {
        SubjectRequest subjectRequest = new SubjectRequest();
        SecuritySubject createdSecuritySubject = new SecuritySubject();
        createdSecuritySubject.setUsername("BOOT1234");
        when(securityAdminService.createSecuritySubject(subjectRequest)).thenReturn(createdSecuritySubject);

        SecuritySubject securitySubject = securityAdminController.createSecuritySubject(subjectRequest);

        verify(securityAdminService, times(1)).createSecuritySubject(subjectRequest);
        assertEquals("BOOT1234", securitySubject.getUsername());
    }

    @Test
    public void editSecuritySubject() {
        SubjectRequest subjectRequest = new SubjectRequest();
        SecuritySubject editedSecuritySubject = new SecuritySubject();
        editedSecuritySubject.setUsername("BOOT1234");
        when(securityAdminService.editSecuritySubject(subjectRequest)).thenReturn(editedSecuritySubject);

        SecuritySubject securitySubject = securityAdminController.editSecuritySubject(subjectRequest);

        verify(securityAdminService, times(1)).editSecuritySubject(subjectRequest);
        assertEquals("BOOT1234", securitySubject.getUsername());
    }

    @Test
    public void createUser() {
        UserRequest userRequest = new UserRequest();
        User createdUser = new User();
        createdUser.setName("Jane England");
        when(securityAdminService.createUser(userRequest)).thenReturn(createdUser);

        User user = securityAdminController.createUser(userRequest);

        verify(securityAdminService, times(1)).createUser(userRequest);
        assertEquals("Jane England", user.getName());
    }

    @Test
    public void editUser() {
        UserRequest userRequest = new UserRequest();
        User editedUser = new User();
        editedUser.setName("Billy Bob");
        when(securityAdminService.editUser(userRequest)).thenReturn(editedUser);

        User user = securityAdminController.editUser(userRequest);

        verify(securityAdminService, times(1)).editUser(userRequest);
        assertEquals("Billy Bob", user.getName());
    }

    private SecuritySubject newSecuritySubject(String username, boolean enabled) {
        SecuritySubject subject1 = new SecuritySubject();
        subject1.setUsername(username);
        subject1.setEnabled(enabled);
        return subject1;
    }


}