package com.registration.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.registration.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
