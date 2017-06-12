package com.babcock.securityadmin.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "SECURITY_ROLES")
public class SecurityRole {

    public SecurityRole() {
    }

    public SecurityRole(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "SECURITY_ROLE_PERMISSIONS",
            joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID", referencedColumnName = "ID"))
    private List<SecurityPermission> securityPermissions;

    @ManyToMany(mappedBy = "securityRoles")
    @JsonIgnore
    private List<SecuritySubject> securitySubjects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SecurityPermission> getSecurityPermissions() {
        return securityPermissions;
    }

    public void setSecurityPermissions(List<SecurityPermission> securityPermissions) {
        this.securityPermissions = securityPermissions;
    }

    public List<SecuritySubject> getSecuritySubjects() {
        return securitySubjects;
    }

    public void setSecuritySubjects(List<SecuritySubject> securitySubjects) {
        this.securitySubjects = securitySubjects;
    }
}
