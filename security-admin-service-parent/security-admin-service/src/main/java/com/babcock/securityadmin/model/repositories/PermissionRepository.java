package com.babcock.securityadmin.model.repositories;

import com.babcock.securityadmin.model.domain.SecurityPermission;
import com.babcock.securityadmin.model.domain.SecurityRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<SecurityPermission, Long> {

    List<SecurityPermission> findBySecurityRoles(List<SecurityRole> securityRoles);

    Long countByName(String name);
}
