package com.babcock.securityadmin.model.domain;

import org.hibernate.annotations.Check;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "pay_number")
    private String payNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "ID")
    private User managerUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_subject", referencedColumnName = "ID")
    private SecuritySubject createdBySubject;

    @Column(name = "creation_date")
    private Date creationDate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private SecuritySubject securitySubject;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "department")
    private String department;

    @Column(name = "address")
    private String address;

    @Column(name = "location")
    private String location;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "licence")
    private String licenceDetails;

    @NotNull
    @Check(constraints = "IS_MANAGER IN ('Y', 'N')")
    @Column(name = "is_manager")
    private String isManager = "N";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getManagerUser() {
        return managerUser;
    }

    public void setManagerUser(User managerUser) {
        this.managerUser = managerUser;
    }

    public SecuritySubject getCreatedBySubject() {
        return createdBySubject;
    }

    public void setCreatedBySubject(SecuritySubject createdBySubject) {
        this.createdBySubject = createdBySubject;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public SecuritySubject getSecuritySubject() {
        return securitySubject;
    }

    public void setSecuritySubject(SecuritySubject securitySubject) {
        this.securitySubject = securitySubject;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLicenceDetails() {
        return licenceDetails;
    }

    public void setLicenceDetails(String licenceDetails) {
        this.licenceDetails = licenceDetails;
    }

    public String getIsManager() {
        return isManager;
    }

    public void setIsManager(String isManager) {
        this.isManager = isManager;
    }
}
