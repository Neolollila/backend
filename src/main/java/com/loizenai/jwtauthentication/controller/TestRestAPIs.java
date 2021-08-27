package com.loizenai.jwtauthentication.controller;

import com.loizenai.jwtauthentication.model.Like;
import com.loizenai.jwtauthentication.model.Role;
import com.loizenai.jwtauthentication.model.RoleName;
import com.loizenai.jwtauthentication.model.User;
import com.loizenai.jwtauthentication.repository.RoleRepository;
import com.loizenai.jwtauthentication.repository.UserRepository;
import com.loizenai.jwtauthentication.security.services.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Convert;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TestRestAPIs {

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public RoleRepository roleRepository;
	
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

	@PostMapping("/api/addActive/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public  ResponseEntity<List<User>> addActive(@PathVariable(value = "id") Long id){
		User user = userRepository.findById(id).get();
		user.setActive(!user.getActive());
		userRepository.save(user);
		return ResponseEntity.ok(this.userRepository.findAllByOrderByIdAsc());
	}

	@DeleteMapping("/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> removeUser(@PathVariable(value = "id") Long id){
		this.userRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/api/addAdmin/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public  ResponseEntity<List<User>> addAdmin(@PathVariable(value = "id") Long id){
		User user = userRepository.findById(id).get();
		Role role = roleRepository.findById(Long.valueOf(1)).get();
		Set<Role> roles = new HashSet<>();
		if (user.getRoles().contains(role)) {
			Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN).get();
			roles.add(userRole);

		} else {
			Role userRole = roleRepository.findByName(RoleName.ROLE_USER).get();
			roles.add(userRole);
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(
				this.userRepository.findAllByOrderByIdAsc()
		);
	}
}