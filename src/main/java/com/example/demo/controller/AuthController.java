package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.User;
import com.example.demo.Service.AuthService;
import com.example.demo.dtos.LogInRequest;
import com.example.demo.dtos.UserResponse;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:3000") // your frontend URL
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/sign-up")
	public ResponseEntity<?> signUp(@RequestBody User user) {

		if (user.getName() == null || user.getName().isBlank()
				|| user.getEmail() == null || user.getEmail().isBlank()
				|| user.getPassword() == null || user.getPassword().isBlank()) {
			return new ResponseEntity<>("Name, email and password are required", HttpStatus.BAD_REQUEST);
		}

		User createdUser = authService.register(user);
		if (createdUser == null) {
			return new ResponseEntity<>("Email already registered", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(new UserResponse(createdUser), HttpStatus.CREATED);
	}

	@PostMapping("/log-in")
	public ResponseEntity<?> logIn(@RequestBody LogInRequest request) {

		User user = authService.login(request.getEmail(), request.getPassword());
		if (user == null) {
			return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>(new UserResponse(user), HttpStatus.OK);
	}
}