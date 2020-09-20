package com.springboot.java.repository;

import com.springboot.java.entity.Role;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@EntityScan(basePackages = {"com.springboot.java.entity"})
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String role);

}
