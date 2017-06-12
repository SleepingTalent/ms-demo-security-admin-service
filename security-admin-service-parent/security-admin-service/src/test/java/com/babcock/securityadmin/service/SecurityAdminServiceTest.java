package com.babcock.securityadmin.service;

import com.babcock.securityadmin.controller.request.PermissionRequest;
import com.babcock.securityadmin.controller.request.RoleRequest;
import com.babcock.securityadmin.controller.request.SubjectRequest;
import com.babcock.securityadmin.controller.request.UserRequest;
import com.babcock.securityadmin.model.domain.SecurityPermission;
import com.babcock.securityadmin.model.domain.SecurityRole;
import com.babcock.securityadmin.model.domain.SecuritySubject;
import com.babcock.securityadmin.model.domain.User;
import com.babcock.securityadmin.model.exception.*;
import com.babcock.securityadmin.model.repositories.PermissionRepository;
import com.babcock.securityadmin.model.repositories.RoleRepository;
import com.babcock.securityadmin.model.repositories.SubjectRepository;
import com.babcock.securityadmin.model.repositories.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityAdminService.class)
@EntityScan("com.babcock.securityadmin.model.domain")
@ActiveProfiles("test")
public class SecurityAdminServiceTest {

    @Autowired
    private SecurityAdminService securityAdminService;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    PermissionRepository permissionRepository;

    @MockBean
    SubjectRepository subjectRepository;


    @MockBean
    UserRepository userRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Captor
    private ArgumentCaptor<List<SecuritySubject>> subjectListCaptor;

    @Captor
    private ArgumentCaptor<List<SecurityRole>> roleListCaptor;


    @Test
    public void findAllSecurityRoles_success() {
        List<SecurityRole> securityRoles = new ArrayList<>();
        SecurityRole securityRole1 = new SecurityRole("Admin", "Admin role");
        SecurityRole securityRole2 = new SecurityRole("DocController", "Document controller role");
        securityRoles.add(securityRole1);
        securityRoles.add(securityRole2);
        when(roleRepository.findAll()).thenReturn(securityRoles);

        List<SecurityRole> foundSecurityRoles = securityAdminService.findAllSecurityRoles();

        verify(roleRepository, times(1)).findAll();
        assertEquals(2, foundSecurityRoles.size());
        assertEquals("Admin", foundSecurityRoles.get(0).getName());
        assertEquals("DocController", foundSecurityRoles.get(1).getName());
    }

    @Test
    public void findAllSecurityRoles_fail_noRolesExist() {
        expectedException.expect(RoleNotFoundException.class);
        expectedException.expectMessage("No securityRoles found");
        when(roleRepository.findAll()).thenReturn(Collections.emptyList());

        securityAdminService.findAllSecurityRoles();

        verify(roleRepository, times(1)).findAll();
    }

    @Test
    public void findSecurityRolesForSecuritySubject() {

        SecuritySubject securitySubject = new SecuritySubject();
        securitySubject.setUsername("scot3355");
        List<SecurityRole> securityRoles = new ArrayList<>();
        when(roleRepository.findBySecuritySubjects(anyListOf(SecuritySubject.class))).thenReturn(securityRoles);

        List<SecurityRole> securityRolesForSubject = securityAdminService.findSecurityRolesForSecuritySubject(securitySubject);

        verify(roleRepository, times(1)).findBySecuritySubjects(subjectListCaptor.capture());
        assertEquals(1, subjectListCaptor.getValue().size());
        assertEquals("scot3355", subjectListCaptor.getValue().get(0).getUsername());
        assertSame(securityRoles, securityRolesForSubject);
    }

    @Test
    public void findSecurityPermissionsForRole() {
        List<SecurityPermission> securityPermissions = new ArrayList<>();
        SecurityPermission securityPermission = new SecurityPermission("document:edit", "Edit a document");
        securityPermissions.add(securityPermission);
        SecurityRole securityRole = new SecurityRole("DocumentController", "Document controller role");
        when(permissionRepository.findBySecurityRoles(anyListOf(SecurityRole.class))).thenReturn(securityPermissions);

        List<SecurityPermission> permissionsForRole = securityAdminService.findSecurityPermissionsForRole(securityRole);

        verify(permissionRepository, times(1)).findBySecurityRoles(roleListCaptor.capture());
        assertEquals(1, roleListCaptor.getValue().size());
        assertEquals("DocumentController", roleListCaptor.getValue().get(0).getName());
        assertSame(securityPermissions, permissionsForRole);
    }

    @Test
    public void createSecurityRole_success() {
        SecurityRole securityRole = new SecurityRole("BatchRunner", "Batch Runner Role");
        when(roleRepository.countByName("BatchRunner")).thenReturn(0L);
        when(roleRepository.save(any(SecurityRole.class))).thenReturn(securityRole);
        ArgumentCaptor<SecurityRole> argumentCaptor = ArgumentCaptor.forClass(SecurityRole.class);

        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setName("BatchRunner");
        roleRequest.setDescription("Batch Runner Role");

        SecurityRole savedRole = securityAdminService.createSecurityRole(roleRequest);

        verify(roleRepository, times(1)).countByName(eq("BatchRunner"));
        verify(roleRepository, times(1)).save(argumentCaptor.capture());
        assertEquals("BatchRunner", savedRole.getName());
        assertEquals("BatchRunner", argumentCaptor.getValue().getName());
        assertEquals("Batch Runner Role", argumentCaptor.getValue().getDescription());
    }

    @Test
    public void createSecurityRole_fail_role_exists() throws Exception {
        expectedException.expect(RoleCreateException.class);
        expectedException.expectMessage("SecurityRole 'Admin' already exists");

        when(roleRepository.countByName("Admin")).thenReturn(1L);

        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setName("Admin");
        roleRequest.setDescription("Admin Role");

        securityAdminService.createSecurityRole(roleRequest);

        verify(roleRepository, times(1)).countByName(eq("Admin"));
        verify(roleRepository, never()).save(any(SecurityRole.class));
    }

    @Test
    public void createSecurityPermission_success() {
        SecurityPermission securityPermission = new SecurityPermission("batch:run", "Batch run permission");
        when(permissionRepository.countByName("batch:run")).thenReturn(0L);
        when(permissionRepository.save(any(SecurityPermission.class))).thenReturn(securityPermission);
        ArgumentCaptor<SecurityPermission> argumentCaptor = ArgumentCaptor.forClass(SecurityPermission.class);

        PermissionRequest permissionRequest = new PermissionRequest();
        permissionRequest.setName("batch:run");
        permissionRequest.setDescription("Batch run permission");

        SecurityPermission savedPermission = securityAdminService.createSecurityPermission(permissionRequest);

        verify(permissionRepository, times(1)).countByName(eq("batch:run"));
        verify(permissionRepository, times(1)).save(argumentCaptor.capture());
        assertEquals("batch:run", savedPermission.getName());
        assertEquals("batch:run", argumentCaptor.getValue().getName());
        assertEquals("Batch run permission", argumentCaptor.getValue().getDescription());
    }

    @Test
    public void createSecurityPermission_fail_permission_exists() throws Exception {
        expectedException.expect(PermissionCreateException.class);
        expectedException.expectMessage("SecurityPermission 'admin:*' already exists");

        when(permissionRepository.countByName("admin:*")).thenReturn(1L);

        PermissionRequest permissionRequest = new PermissionRequest();
        permissionRequest.setName("admin:*");
        permissionRequest.setDescription("All Admin permissions");

        securityAdminService.createSecurityPermission(permissionRequest);

        verify(permissionRepository, times(1)).countByName(eq("admin:*"));
        verify(permissionRepository, never()).save(any(SecurityPermission.class));
    }

    @Test
    public void findAllSecurityPermissions() {
        List<SecurityPermission> securityPermissions = new ArrayList<>();
        SecurityPermission securityPermission1 = new SecurityPermission("document:edit", "Edit a document");
        SecurityPermission securityPermission2 = new SecurityPermission("drawing:delete", "Delete a drawing");
        securityPermissions.add(securityPermission1);
        securityPermissions.add(securityPermission2);
        when(permissionRepository.findAll()).thenReturn(securityPermissions);

        List<SecurityPermission> foundSecurityPermissions = securityAdminService.findAllSecurityPermissions();

        verify(permissionRepository, times(1)).findAll();
        assertEquals(2, foundSecurityPermissions.size());
        assertEquals("document:edit", foundSecurityPermissions.get(0).getName());
        assertEquals("drawing:delete", foundSecurityPermissions.get(1).getName());
    }

    @Test
    public void findSecurityRoleByName() {
        SecurityRole changeManagerRole = new SecurityRole("ChangeManager", "Change manager role");
        when(roleRepository.findByName("ChangeManager")).thenReturn(changeManagerRole);

        SecurityRole foundRole = securityAdminService.findRole("ChangeManager");

        verify(roleRepository, times(1)).findByName("ChangeManager");
        assertEquals("Change manager role", foundRole.getDescription());
    }

    @Test
    public void findAllSecuritySubjects_success() {
        List<SecuritySubject> securitySubjects = new ArrayList<>();
        SecuritySubject subject1 = newSecuritySubject("fran1234", true);
        SecuritySubject subject2 = newSecuritySubject("bess5432", false);
        securitySubjects.add(subject1);
        securitySubjects.add(subject2);
        when(subjectRepository.findAll()).thenReturn(securitySubjects);

        List<SecuritySubject> allSecuritySubjects = securityAdminService.findAllSecuritySubjects();

        verify(subjectRepository, times(1)).findAll();
        assertEquals(2, allSecuritySubjects.size());
        assertEquals("fran1234", allSecuritySubjects.get(0).getUsername());
        assertEquals("bess5432", allSecuritySubjects.get(1).getUsername());
    }

    @Test
    public void findAllSecuritySubjects_fail_noSubjectsExist() {
        expectedException.expect(SubjectNotFoundException.class);
        expectedException.expectMessage("No securitySubjects found");
        when(subjectRepository.findAll()).thenReturn(Collections.emptyList());

        securityAdminService.findAllSecuritySubjects();

        verify(subjectRepository, times(1)).findAll();
    }

    @Test
    public void findAllUsers_success() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setName("Jim Root");
        User user2 = new User();
        user2.setName("Ace Frehley");
        User user3 = new User();
        user3.setName("Kirk Hammett");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        when(userRepository.findAll()).thenReturn(users);

        List<User> allUsers = securityAdminService.findAllUsers();

        verify(userRepository, times(1)).findAll();
        assertEquals(3, allUsers.size());
        assertEquals("Jim Root", allUsers.get(0).getName());
        assertEquals("Ace Frehley", allUsers.get(1).getName());
        assertEquals("Kirk Hammett", allUsers.get(2).getName());
    }

    @Test
    public void editSecurityRole_success() {
        RoleRequest roleRequest = new RoleRequest();
        String roleId = "67";
        roleRequest.setId(roleId);
        roleRequest.setName("ChangeAdministrator");
        roleRequest.setDescription("Change Manager/Admin role");
        SecurityRole roleToUpdate = new SecurityRole("ChangeManager", "Change Manager role");
        when(roleRepository.findOne(67L)).thenReturn(roleToUpdate);

        SecurityRole securityRole = securityAdminService.editSecurityRole(roleRequest);

        verify(roleRepository, times(1)).findOne(67L);
        verify(roleRepository, times(1)).save(roleToUpdate);
        assertEquals("ChangeAdministrator", securityRole.getName());
        assertEquals("Change Manager/Admin role", securityRole.getDescription());
    }

    @Test
    public void editSecurityRole_fail_SecurityRolePersistenceException() {
        expectedException.expect(RoleCreateException.class);
        RoleRequest roleRequest = new RoleRequest();
        SecurityRole roleToUpdate = new SecurityRole("ChangeManager", "Change Manager role");
        when(roleRepository.findOne(67L)).thenReturn(roleToUpdate);
        when(roleRepository.save(roleToUpdate)).thenThrow(new PersistenceException());

        securityAdminService.editSecurityRole(roleRequest);

        verify(roleRepository, times(1)).findOne(67L);
        verify(roleRepository, times(1)).save(roleToUpdate);
    }

    @Test
    public void editSecurityPermission_success() {
        PermissionRequest permissionRequest = new PermissionRequest();
        String roleId = "48";
        permissionRequest.setId(roleId);
        permissionRequest.setName("document:add");
        permissionRequest.setDescription("Add Document permission");
        SecurityPermission permissionToUpdate = new SecurityPermission("doxqument:addx", "Addx Doxqument permisxion");
        when(permissionRepository.findOne(48L)).thenReturn(permissionToUpdate);

        SecurityPermission securityPermission = securityAdminService.editSecurityPermission(permissionRequest);

        verify(permissionRepository, times(1)).findOne(48L);
        verify(permissionRepository, times(1)).save(permissionToUpdate);
        assertEquals("document:add", securityPermission.getName());
        assertEquals("Add Document permission", securityPermission.getDescription());
    }

    @Test
    public void editSecurityPermission_fail_SecurityPermissionPersistenceException() {
        expectedException.expect(PermissionCreateException.class);
        PermissionRequest permissionRequest = new PermissionRequest();
        SecurityPermission permissionToUpdate = new SecurityPermission("doxqument:addx", "Addx Doxqument permisxion");
        when(permissionRepository.findOne(48L)).thenReturn(permissionToUpdate);
        when(permissionRepository.save(permissionToUpdate)).thenThrow(new PersistenceException());

        securityAdminService.editSecurityPermission(permissionRequest);

        verify(permissionRepository, times(1)).findOne(48L);
        verify(permissionRepository, times(1)).save(permissionToUpdate);
    }

    @Test
    public void createSecuritySubject_success() {
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.setUsername("FANS9900");
        subjectRequest.setPassword("kljshLKJHghcs34jhfgukyg");
        when(subjectRepository.countByUserName("FANS9900")).thenReturn(0L);
        SecuritySubject securitySubject = new SecuritySubject();
        ArgumentCaptor<SecuritySubject> securitySubjectArgumentCaptor = ArgumentCaptor.forClass(SecuritySubject.class);
        when(subjectRepository.save(securitySubjectArgumentCaptor.capture())).thenReturn(securitySubject);

        SecuritySubject createdSecuritySubject = securityAdminService.createSecuritySubject(subjectRequest);

        verify(subjectRepository, times(1)).countByUserName("FANS9900");
        verify(subjectRepository, times(1)).save(securitySubjectArgumentCaptor.capture());
        assertEquals("FANS9900", securitySubjectArgumentCaptor.getValue().getUsername());
        assertEquals("kljshLKJHghcs34jhfgukyg", securitySubjectArgumentCaptor.getValue().getPassword());
        assertSame(securitySubject, createdSecuritySubject);
    }

    @Test
    public void createSecuritySubject_fail_subjectAlreadyExists() {
        expectedException.expect(SubjectCreateException.class);
        expectedException.expectMessage("SecuritySubject 'FANS9900' already exists");
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.setUsername("FANS9900");
        subjectRequest.setPassword("kljshLKJHghcs34jhfgukyg");
        when(subjectRepository.countByUserName("FANS9900")).thenReturn(1L);

        securityAdminService.createSecuritySubject(subjectRequest);

        verify(subjectRepository, times(1)).countByUserName("FANS9900");
        verify(subjectRepository, never()).save(any(SecuritySubject.class));
    }

    @Test
    public void createSecuritySubject_fail_SecuritySubjectPersistenceException() {
        expectedException.expect(SubjectCreateException.class);
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.setUsername("FANS9900");
        subjectRequest.setPassword("kljshLKJHghcs34jhfgukyg");
        when(subjectRepository.countByUserName("FANS9900")).thenReturn(0L);
        when(subjectRepository.save(any(SecuritySubject.class))).thenThrow(new SubjectCreateException("Save failure"));

        securityAdminService.createSecuritySubject(subjectRequest);

        verify(subjectRepository, times(1)).countByUserName("FANS9900");
        verify(subjectRepository, times(1)).save(any(SecuritySubject.class));
    }

    @Test
    public void createUser_success() {
        UserRequest userRequest = new UserRequest();
        userRequest.setPayNumber("334999X");
        userRequest.setName("Phil Fog");
        userRequest.setEmail("pfoggy@email.com");
        userRequest.setDepartment("Mail");
        userRequest.setLocation("Wales");
        userRequest.setJobTile("Mailman");
        when(userRepository.countByPayNumber(userRequest.getPayNumber())).thenReturn(0L);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        User userToCreate = new User();
        when(userRepository.save(userArgumentCaptor.capture())).thenReturn(userToCreate);

        User createdUser = securityAdminService.createUser(userRequest);

        verify(userRepository, times(1)).countByPayNumber(userRequest.getPayNumber());
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        assertEquals("334999X", userArgumentCaptor.getValue().getPayNumber());
        assertEquals("Phil Fog", userArgumentCaptor.getValue().getName());
        assertEquals("pfoggy@email.com", userArgumentCaptor.getValue().getEmail());
        assertEquals("Mail", userArgumentCaptor.getValue().getDepartment());
        assertEquals("Wales", userArgumentCaptor.getValue().getLocation());
        assertEquals("Mailman", userArgumentCaptor.getValue().getJobTitle());
        assertSame(userToCreate, createdUser);
    }

    @Test
    public void createUser_fail_userAlreadyExists() {
        expectedException.expect(UserCreateException.class);
        expectedException.expectMessage("User PayNumber '334999X' already exists");
        UserRequest userRequest = new UserRequest();
        userRequest.setPayNumber("334999X");
        when(userRepository.countByPayNumber("334999X")).thenReturn(1L);

        securityAdminService.createUser(userRequest);

        verify(userRepository, times(1)).countByPayNumber("334999X");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void createUser_fail_UserPersistenceException() {
        expectedException.expect(UserCreateException.class);
        UserRequest userRequest = new UserRequest();
        userRequest.setPayNumber("334999X");
        when(userRepository.countByPayNumber("334999X")).thenReturn(0L);
        when(userRepository.save(any(User.class))).thenThrow(new UserCreateException("Save failure"));

        securityAdminService.createUser(userRequest);

        verify(userRepository, times(1)).countByPayNumber("334999X");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void editUser_success() {
        UserRequest userRequest = new UserRequest();
        userRequest.setId("29");
        userRequest.setPayNumber("334999X");
        userRequest.setName("Phil Fog");
        userRequest.setEmail("pfoggy@email.com");
        userRequest.setDepartment("Mail");
        userRequest.setLocation("Wales");
        userRequest.setJobTile("Mailman");
        User userToEdit = new User();
        when(userRepository.findOne(29L)).thenReturn(userToEdit);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(userArgumentCaptor.capture())).thenReturn(userToEdit);

        User editedUser = securityAdminService.editUser(userRequest);

        verify(userRepository, times(1)).findOne(29L);
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        assertEquals("334999X", userArgumentCaptor.getValue().getPayNumber());
        assertEquals("Phil Fog", userArgumentCaptor.getValue().getName());
        assertEquals("pfoggy@email.com", userArgumentCaptor.getValue().getEmail());
        assertEquals("Mail", userArgumentCaptor.getValue().getDepartment());
        assertEquals("Wales", userArgumentCaptor.getValue().getLocation());
        assertEquals("Mailman", userArgumentCaptor.getValue().getJobTitle());
        assertSame(userToEdit, editedUser);
    }

    @Test
    public void editUser_fail_UserPersistenceException() {
        expectedException.expect(UserCreateException.class);
        UserRequest userRequest = new UserRequest();
        userRequest.setPayNumber("123123H");
        when(userRepository.countByPayNumber("123123H")).thenReturn(0L);
        when(userRepository.save(any(User.class))).thenThrow(new UserCreateException("Save failure"));

        securityAdminService.editUser(userRequest);

        verify(userRepository, times(1)).countByPayNumber("123123H");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void editSecuritySubject_success() {
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.setId("998");
        subjectRequest.setUsername("FANS9900");
        SecuritySubject securitySubjectToEdit = new SecuritySubject();
        securitySubjectToEdit.setUsername("fasN9900");
        when(subjectRepository.findOne(998L)).thenReturn(securitySubjectToEdit);
        ArgumentCaptor<SecuritySubject> securitySubjectArgumentCaptor = ArgumentCaptor.forClass(SecuritySubject.class);
        when(subjectRepository.save(securitySubjectArgumentCaptor.capture())).thenReturn(securitySubjectToEdit);

        SecuritySubject editedSecuritySubject = securityAdminService.editSecuritySubject(subjectRequest);

        verify(subjectRepository, times(1)).findOne(998L);
        verify(subjectRepository, times(1)).save(securitySubjectArgumentCaptor.capture());
        assertEquals("FANS9900", securitySubjectArgumentCaptor.getValue().getUsername());
        assertSame(securitySubjectToEdit, editedSecuritySubject);
    }

    @Test
    public void editSecuritySubject_fail_noSubjectFound() {
        expectedException.expect(SubjectCreateException.class);
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.setId("998");
        when(subjectRepository.findOne(998L)).thenReturn(null);

        securityAdminService.editSecuritySubject(subjectRequest);

        verify(subjectRepository, times(1)).findOne(998L);
        verify(subjectRepository, never()).save(any(SecuritySubject.class));
    }

    private SecuritySubject newSecuritySubject(String username, boolean enabled) {
        SecuritySubject subject1 = new SecuritySubject();
        subject1.setUsername(username);
        subject1.setEnabled(enabled);
        return subject1;
    }

}