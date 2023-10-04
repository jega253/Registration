package com.registration.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.registration.dto.UserDto;
import com.registration.entity.Employee;
import com.registration.repo.EmployeeRepo;







@Service
public class UserService {
	@Autowired
	private EmployeeRepo rep;

	public List<UserDto> getUsersWithRoles() {
		return rep.getUsersWithRoles();
	}
	

	public List<UserDto> getUsersWithName(String searchTerm) {
		return rep.getUserssearch( searchTerm + "%");
	}

	public List<UserDto> getUsersWithPageable(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return rep.getUserspageable(pageable);

    }
//	public List<Employee> findBysorting(int pageno, int count) {
//		Pageable pageable = PageRequest.of(pageno, count);
//		List<Employee> lu = (List<Employee>) rep.findAll(pageable).get().toList();
//		List<Employee> collect = lu.stream().sorted(Comparator.comparing(Employee::getName)).toList();
//		return collect;
//		}

	public void del(Employee user) {
		rep.deleteById(user.getId());

	}
   
	public Optional<Employee> findusingID(Integer id) {
        return rep.findById(id);
    }
	
}
