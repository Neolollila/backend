package com.loizenai.jwtauthentication.controller;

import com.loizenai.jwtauthentication.model.User;
import com.loizenai.jwtauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TestRestAPIs {

	@Autowired
	public UserRepository userRepository;
	
	@GetMapping("/api/test/user")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String userAccess() {
		return ">>> User Contents!";
	}
	
	@GetMapping("/api/test/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> adminAccess() {
		return ResponseEntity.ok(
				this.userRepository.findAll()
		);
	}

	@GetMapping("/profile/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity <User> profileAccess(@PathVariable(value = "id") Long id) {

		return ResponseEntity.ok(this.userRepository.findById(id).get());
	}

	@DeleteMapping("/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> removeUser(@PathVariable(value = "id") Long id){
		this.userRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}