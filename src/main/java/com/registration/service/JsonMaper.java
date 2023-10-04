package com.registration.service;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.registration.entity.Employee;
import com.registration.repo.RoleRepo;


@Service
public class JsonMaper {
	
	@Autowired
	RoleRepo repo;
	
	@SuppressWarnings("unchecked")
	public JSONObject maker(Employee employee) {
		String role=repo.findById(employee.getRole_id()).get().getRole_name();
		JSONObject json = new JSONObject();
		json.put("empid", employee.getId());
		json.put("username", employee.getName());
		json.put("created", employee.getCreated());
		json.put("modified",employee.getModified());
		json.put("role_id", employee.getRole_id());
		json.put("role", List.of(role));
		return json;
	}

}
