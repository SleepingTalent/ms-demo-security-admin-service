package com.babcock.securityadmin.model.repositories;

import com.babcock.securityadmin.model.domain.SecurityRole;
import com.babcock.securityadmin.model.domain.SecuritySubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<SecurityRole, Long> {

    List<SecurityRole> findBySecuritySubjects(List<SecuritySubject> securitySubjects);

    Long countByName(String name);

    SecurityRole findByName(String name);
}
