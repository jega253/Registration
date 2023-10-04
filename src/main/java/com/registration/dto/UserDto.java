package com.registration.dto;

public class UserDto {

	private int id;
	private String name;
	private String email;
	private int role_id;
	private String role_name;
	private int status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public UserDto(int id, String name, String email,int role_id, String role_name,int status) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.role_name = role_name;
		this.role_id = role_id;
		this.status=status;
	}
	public UserDto() {
		super();
	}
	
}

