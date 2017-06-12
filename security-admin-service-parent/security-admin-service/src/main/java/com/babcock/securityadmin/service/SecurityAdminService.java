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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecurityAdminService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    public List<SecurityRole> findAllSecurityRoles() {
        List<SecurityRole> securityRoles = roleRepository.findAll();
        if (securityRoles.isEmpty())
            throw new RoleNotFoundException("No securityRoles found");
        else {
            return securityRoles;
        }
    }

    public List<SecurityPermission> findAllSecurityPermissions() {
        return permissionRepository.findAll();
    }

    public SecurityRole createSecurityRole(RoleRequest roleRequest) throws RoleCreateException {
        SecurityRole savedSecurityRole;
        try {
            Long foundSecurityRoleCount = roleRepository.countByName(roleRequest.getName());

            if (foundSecurityRoleCount == 0) {
                SecurityRole securityRole = new SecurityRole(roleRequest.getName(), roleRequest.getDescription());
                savedSecurityRole = roleRepository.save(securityRole);
            } else {
                throw new RoleCreateException("SecurityRole '" + roleRequest.getName() + "' already exists");
            }
        } catch (Exception e) {
            throw new RoleCreateException(e);
        }

        return savedSecurityRole;
    }

    public SecurityRole editSecurityRole(RoleRequest roleRequest) {
        SecurityRole securityRole;
        try {
            securityRole = roleRepository.findOne(Long.valueOf(roleRequest.getId()));

            securityRole.setName(roleRequest.getName());
            securityRole.setDescription(roleRequest.getDescription());
            //TODO: Need to Handled the updating of permissions...

            roleRepository.save(securityRole);

        } catch (Exception e) {
            throw new RoleCreateException(e);
        }

        return securityRole;
    }

    public List<SecurityRole> findSecurityRolesForSecuritySubject(SecuritySubject securitySubject) {
        List<SecuritySubject> securitySubjects = new ArrayList<>(1);
        securitySubjects.add(securitySubject);
        return roleRepository.findBySecuritySubjects(securitySubjects);
    }


    public List<SecurityPermission> findSecurityPermissionsForRole(SecurityRole securityRole) {
        List<SecurityRole> securityRoles = new ArrayList<>(1);
        securityRoles.add(securityRole);
        return permissionRepository.findBySecurityRoles(securityRoles);
    }

    public SecurityRole findRole(String name) {
        return roleRepository.findByName(name);
    }

    public SecurityPermission createSecurityPermission(PermissionRequest permissionRequest) {
        SecurityPermission savedSecurityPermission;
        try {
            Long foundSecurityPermissionCount = permissionRepository.countByName(permissionRequest.getName());

            if (foundSecurityPermissionCount == 0) {
                SecurityPermission securityPermission = new SecurityPermission(permissionRequest.getName(), permissionRequest.getDescription());
                savedSecurityPermission = permissionRepository.save(securityPermission);
            } else {
                throw new PermissionCreateException("SecurityPermission '" + permissionRequest.getName() + "' already exists");
            }
        } catch (Exception e) {
            throw new PermissionCreateException(e);
        }

        return savedSecurityPermission;
    }

    public SecurityPermission editSecurityPermission(PermissionRequest permissionRequest) {
        SecurityPermission editSecurityPermission;
        try {
            editSecurityPermission = permissionRepository.findOne(Long.valueOf(permissionRequest.getId()));
            editSecurityPermission.setName(permissionRequest.getName());
            editSecurityPermission.setDescription(permissionRequest.getDescription());

            permissionRepository.save(editSecurityPermission);

        } catch (Exception e) {
            throw new PermissionCreateException(e);
        }

        return editSecurityPermission;
    }

    public List<SecuritySubject> findAllSecuritySubjects() {
        List<SecuritySubject> allSecuritySubjects = subjectRepository.findAll();
        if (allSecuritySubjects.isEmpty())
            throw new SubjectNotFoundException("No securitySubjects found");
        else {
            return allSecuritySubjects;
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    public SecuritySubject createSecuritySubject(SubjectRequest subjectRequest) {
        SecuritySubject savedSecuritySubject;
        try {
            Long foundSecuritySubjectCount = subjectRepository.countByUserName(subjectRequest.getUsername());

            if (foundSecuritySubjectCount == 0) {
                SecuritySubject securitySubject = new SecuritySubject();
                securitySubject.setUsername(subjectRequest.getUsername());
                securitySubject.setPassword(subjectRequest.getPassword());

                savedSecuritySubject = subjectRepository.save(securitySubject);
            } else {
                throw new SubjectCreateException("SecuritySubject '" + subjectRequest.getUsername() + "' already exists");
            }
        } catch (Exception e) {
            throw new SubjectCreateException(e);
        }

        return savedSecuritySubject;
    }

    public User createUser(UserRequest userRequest) {
        User savedUser;
        try {
            Long foundUserCount = userRepository.countByPayNumber(userRequest.getPayNumber());

            if (foundUserCount == 0) {
                User user = new User();
                user.setPayNumber(userRequest.getPayNumber());
                user.setName(userRequest.getName());
                user.setEmail(userRequest.getEmail());
                user.setJobTitle(userRequest.getJobTile());
                user.setDepartment(userRequest.getDepartment());
                user.setLocation(userRequest.getLocation());

                savedUser = userRepository.save(user);
            } else {
                throw new UserCreateException("User PayNumber '" + userRequest.getPayNumber() + "' already exists");
            }
        } catch (Exception e) {
            throw new UserCreateException(e);
        }

        return savedUser;
    }

    public User editUser(UserRequest userRequest) {
        User editUser;
        try {
            editUser = userRepository.findOne(Long.valueOf(userRequest.getId()));
            editUser.setPayNumber(userRequest.getPayNumber());
            editUser.setName(userRequest.getName());
            editUser.setEmail(userRequest.getEmail());
            editUser.setJobTitle(userRequest.getJobTile());
            editUser.setDepartment(userRequest.getDepartment());
            editUser.setLocation(userRequest.getLocation());

            userRepository.save(editUser);

        } catch (Exception e) {
            throw new UserCreateException(e);
        }

        return editUser;
    }

    public SecuritySubject editSecuritySubject(SubjectRequest subjectRequest) {
        SecuritySubject editSubject;
        try {
            editSubject = subjectRepository.findOne(Long.valueOf(subjectRequest.getId()));
            editSubject.setUsername(subjectRequest.getUsername());
            //TODO: Need to Handle Updating of Roles

            subjectRepository.save(editSubject);

        } catch (Exception e) {
            throw new SubjectCreateException(e);
        }

        return editSubject;
    }
}
