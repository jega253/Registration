package com.registration.controller;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.Provider.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.registration.comp.Compnents;
import com.registration.dto.AuthRequest;
import com.registration.dto.UserDto;
import com.registration.entity.Employee;
import com.registration.jwtutils.JwtTokenUtil;
import com.registration.repo.EmployeeRepo;
import com.registration.repo.RoleRepo;
import com.registration.service.JsonMaper;
import com.registration.service.TokenCreation;
import com.registration.service.UserService;

@RestController
@RequestMapping("employees")
public class DemoController {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private Compnents compnents;

	@Autowired
	private JsonMaper mapper;

	@Autowired
	private TokenCreation creation;

	@Autowired
	private UserService ser;

	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();

	@PostMapping("register")
	public ResponseEntity<Object> putEmp(@RequestBody Employee employee) {

		Optional<Employee> emp = employeeRepo.findByEmail(employee.getEmail());
		Map<String, Object> map;

		if (!emp.isEmpty()) {
			map = new LinkedHashMap<>();
			map.put("responce", "user already exists");
			map.put("empid", emp.get().getId());
			return new ResponseEntity<>(map, HttpStatus.ALREADY_REPORTED);
		}

		employee.setCreated(dtf.format(now));
		employee.setModified(dtf.format(now));

		String hashedpass = compnents.encode(employee.getPassword());
		employee.setPassword(hashedpass);
		employeeRepo.save(employee);

		return new ResponseEntity<>(mapper.maker(employee), HttpStatus.OK);
	}

	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
		return creation.generateToken(authRequest);
	}

	@GetMapping("hello")
	public ResponseEntity<?> Secure(@RequestHeader(name = "Authorization") String token) {

		String result = creation.valitateToken(token);
		if (result.equals("") || result.isBlank())
			return new ResponseEntity<String>("{hello}", HttpStatus.OK);

		return new ResponseEntity<String>(result, HttpStatus.FORBIDDEN);
	}

	@GetMapping("/list")
	public ResponseEntity<Object> getUsers(@RequestHeader(name = "Authorization") String token,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "searchTerm", required = false) String searchTerm) {

		String result = creation.valitateToken(token);
		if (result.equals("") || result.isBlank()) {
			if (searchTerm != null) {
				List<UserDto> users = ser.getUsersWithName(searchTerm);
				return ResponseEntity.ok(users);
			} else if (name != null) {
				List<UserDto> user = ser.getUsersWithName(name);
				if (user != null) {
					return ResponseEntity.ok(user);
				} else {
					return ResponseEntity.notFound().build();
				}

			} else {
				if (page != null) {
					List<UserDto> users = ser.getUsersWithPageable(page, size);
					return ResponseEntity.ok(users);
				} else {
					List<UserDto> users = ser.getUsersWithRoles();
					return ResponseEntity.ok(users);
				}
			}

		}
		return new ResponseEntity<Object>(result, HttpStatus.FORBIDDEN);
	}
//	 @GetMapping("/lists")
//	    public List<Employee> getUsersBySorting(
//	        @RequestParam("pageno") int pageno,
//	        @RequestParam("count") int count,
//	        @RequestParam("sort") String sort
//	    ) {
//	        return ser.findBysorting(pageno, count);
//	    }

	@RequestMapping("/delete")
	public String del(@RequestParam(value = "id") int id, Employee user) {
		ser.del(user);
		return "success";
	}

	@GetMapping("/find")
	public Optional<Employee> findid(@RequestParam(value = "id") int id) {
		return ser.findusingID(id);
	}

}
