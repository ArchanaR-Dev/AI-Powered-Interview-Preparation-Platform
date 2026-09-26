package com.example.demo.dtos;

import com.example.demo.Entity.User;

public class UserResponse {

	private Long id;
	private String name;
	private String email;
	private String token;
	
	 public UserResponse(User user) {
	        this.id = user.getId();
	        this.name = user.getName();
	        this.email = user.getEmail();
	 }

	public UserResponse(String token,User user) {
		this.token=token;
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
	}
	
	public String getToken() { return token;}
	public Long getId() { return id; }
	public String getName() { return name; }
	public String getEmail() { return email; }
}