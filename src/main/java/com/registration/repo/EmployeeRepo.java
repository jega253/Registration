package com.registration.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.registration.dto.UserDto;
import com.registration.entity.Employee;

@Repository
public interface EmployeeRepo  extends JpaRepository<Employee, Integer>{
	
	Optional<Employee> findByEmail(String name);
	
	
	@Query("SELECT new com.registration.dto.UserDto(u.id, u.name, u.email,r.role_id, r.role_name,u.status) " +
			"FROM Employee u left JOIN Role r ON u.role_id = r.role_id")
	List<UserDto> getUsersWithRoles();

	
	@Query("SELECT new  com.registration.dto.UserDto(u.id, u.name, u.email, r.role_id, r.role_name, u.status) " +
			"FROM Employee u left JOIN Role r ON u.role_id = r.role_id " + "WHERE u.name LIKE :searchTerm%")
	List<UserDto> getUserssearch(@Param("searchTerm") String searchTerm);

	
	@Query("SELECT new  com.registration.dto.UserDto(u.id, u.name, u.email, r.role_id, r.role_name, u.status) " +
			"FROM Employee u left JOIN Role r ON u.role_id = r.role_id")
	List<UserDto> getUserspageable(org.springframework.data.domain.Pageable pageable);
	

}
