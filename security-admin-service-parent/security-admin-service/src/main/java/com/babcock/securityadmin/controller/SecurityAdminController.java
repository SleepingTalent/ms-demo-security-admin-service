package com.babcock.securityadmin.controller;

import com.babcock.securityadmin.controller.request.PermissionRequest;
import com.babcock.securityadmin.controller.request.RoleRequest;
import com.babcock.securityadmin.controller.request.SubjectRequest;
import com.babcock.securityadmin.controller.request.UserRequest;
import com.babcock.securityadmin.model.domain.SecurityPermission;
import com.babcock.securityadmin.model.domain.SecurityRole;
import com.babcock.securityadmin.model.domain.SecuritySubject;
import com.babcock.securityadmin.model.domain.User;
import com.babcock.securityadmin.service.SecurityAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequestMapping("/securityadmin")
public class SecurityAdminController {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    SecurityAdminService securityAdminService;

    @RequestMapping(value = "/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SecurityRole> findAllRoles() {

        logger.info("findAllRoles() invoked");

        List<SecurityRole> securityRoles = securityAdminService.findAllSecurityRoles();

        logger.info("findAllRoles() found: " + securityRoles.size() + " securityRoles");

        return securityRoles;
    }

    @RequestMapping(value = "/roles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SecurityRole createSecurityRole(@RequestBody RoleRequest roleRequest) {

        logger.info("createSecurityRole(" + roleRequest + ") invoked");

        return securityAdminService.createSecurityRole(roleRequest);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SecurityRole editSecurityRole(@RequestBody RoleRequest roleRequest) {

        logger.info("editSecurityRole(" + roleRequest + ") invoked");

        return securityAdminService.editSecurityRole(roleRequest);
    }

    @RequestMapping(value = "/permissions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SecurityPermission> findAllPermissions() {

        logger.info("findAllPermissions() invoked");

        List<SecurityPermission> securityPermissions = securityAdminService.findAllSecurityPermissions();

        logger.info("findAllPermissions() found: " + securityPermissions.size() + " securityRoles");

        return securityPermissions;
    }

    @RequestMapping(value = "/permissions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SecurityPermission createSecurityPermission(@RequestBody PermissionRequest permissionRequest) {

        logger.info("createSecurityPermission(" + permissionRequest + ") invoked");

        return securityAdminService.createSecurityPermission(permissionRequest);
    }

    @RequestMapping(value = "/permissions", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SecurityPermission editSecurityPermission(@RequestBody PermissionRequest permissionRequest) {

        logger.info("editSecurityPermission(" + permissionRequest + ") invoked");

        return securityAdminService.editSecurityPermission(permissionRequest);
    }

    @RequestMapping(value = "/subjects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SecuritySubject> findAllSecuritySubjects() {

        logger.info("findAllSecuritySubjects() invoked");

        return securityAdminService.findAllSecuritySubjects();
    }

    @RequestMapping(value = "/subjects", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SecuritySubject createSecuritySubject(@RequestBody SubjectRequest subjectRequest) {

        logger.info("createSecuritySubject(" + subjectRequest + ") invoked");

        return securityAdminService.createSecuritySubject(subjectRequest);
    }

    @RequestMapping(value = "/subjects", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SecuritySubject editSecuritySubject(@RequestBody SubjectRequest subjectRequest) {

        logger.info("editSecuritySubject(" + subjectRequest + ") invoked");

        return securityAdminService.editSecuritySubject(subjectRequest);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> findAllUsers() {

        logger.info("findAllUsers() invoked");

        return securityAdminService.findAllUsers();
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody UserRequest userRequest) {

        logger.info("createUser(" + userRequest + ") invoked");

        return securityAdminService.createUser(userRequest);
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User editUser(@RequestBody UserRequest userRequest) {

        logger.info("editUser(" + userRequest + ") invoked");

        return securityAdminService.editUser(userRequest);
    }
}
