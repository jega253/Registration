package com.registration.service;

import java.util.Map;
import java.util.Optional;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.registration.dto.AuthRequest;
import com.registration.entity.Employee;
import com.registration.jwtutils.JwtTokenUtil;
import com.registration.repo.EmployeeRepo;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
@Component
public class TokenCreation {
	
	@Autowired
	EmployeeRepo employeeRepo;
	@Autowired
	JwtTokenUtil jwtTokenUt;

	public ResponseEntity<?> generateToken(AuthRequest authRequest){
		
		Optional<Employee> emp=employeeRepo.findByEmail(authRequest.getUsername());
		if(emp.isPresent()) {
			
			Employee employee =emp.get();
			if(!BCrypt.checkpw(authRequest.getPassword(), employee.getPassword()))
				return new  ResponseEntity<String>("{Status :password wrong}",HttpStatus.FORBIDDEN);
			
			
			String token=jwtTokenUt.generateToken(employee);
			return ResponseEntity.ok(token);
		}
		
		return new ResponseEntity<String>("{Status : Username not found}",HttpStatus.FORBIDDEN);
	}
	
	public String valitateToken(String requestTokenHeader ){
		String username = null;
		String jwtToken = null;
	
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			System.out.println(jwtToken);
			try {
				username = jwtTokenUt.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				return "{Status :invalid token}";
			} catch (ExpiredJwtException e) {
				return "{Status :token expired}";
			} catch (SignatureException e) {
				return "{Status :token not found}";
			} catch (Exception e) {
				return "{status : invalid token}";
			}
			
		} else {
			return "{Status :token does not match}";
		}
		if (username != null ) {
			System.out.println(username);
			Optional<Employee> userDetails = employeeRepo.findByEmail(username);

			if(userDetails.isEmpty())
				return "{Status :no user found}";
			
			if (jwtTokenUt.validateToken(jwtToken, userDetails.get()))
		       return "";
			
			
	}
		return "{status : no subject}";
	}
}
