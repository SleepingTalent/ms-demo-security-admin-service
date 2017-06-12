package com.babcock.securityadmin.model.repositories;

import com.babcock.securityadmin.model.domain.SecuritySubject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<SecuritySubject, Long> {

    Long countByUserName(String username);
}
