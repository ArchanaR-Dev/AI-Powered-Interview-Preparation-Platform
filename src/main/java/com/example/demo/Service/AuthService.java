package com.example.demo.Service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// returns null if the email is already registered
	public User register(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			return null;
		}

		user.setId(null); // always create a new row
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return userRepository.save(user);
	}

	// returns null if email or password is wrong
	public User login(String email, String password) {
		Optional<User> optionalUser = userRepository.findByEmail(email);

		// matches() compares the typed password with the BCrypt hash
		if (optionalUser.isPresent()
				&& passwordEncoder.matches(password, optionalUser.get().getPassword())) {
			return optionalUser.get();
		}
		return null;
	}
}