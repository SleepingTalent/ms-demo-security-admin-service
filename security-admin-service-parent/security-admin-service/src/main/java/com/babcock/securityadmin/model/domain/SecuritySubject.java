package com.babcock.securityadmin.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "SECURITY_SUBJECTS")
public class SecuritySubject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "USERNAME")
    private String username;

    @NotNull
    @JsonIgnore
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @JsonIgnore
    @Column(name = "SALT")
    private String salt;

    @NotNull
    @Column(name = "ENABLED")
    private boolean enabled;

    @OneToOne
    @JoinColumn(name = "USER_ID", unique = true)
    @JsonIgnore
    private User user;

    @ManyToMany
    @JoinTable(name = "SECURITY_SUBJECT_ROLES",
            joinColumns = @JoinColumn(name = "SUBJECT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
    private List<SecurityRole> securityRoles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<SecurityRole> getSecurityRoles() {
        return securityRoles;
    }

    public void setSecurityRoles(List<SecurityRole> securityRoles) {
        this.securityRoles = securityRoles;
    }
}
