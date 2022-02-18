package com.learning.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.dto.EROLE;
import com.learning.dto.Role;
import com.learning.dto.User;
import com.learning.exception.AlreadyExistsException;
import com.learning.exception.IdNotFoundException;
import com.learning.payload.request.LoginRequest;
import com.learning.payload.request.SignupRequest;
import com.learning.payload.response.JwtResponse;
import com.learning.payload.response.MessageResponse;
import com.learning.repository.RoleRepository;
import com.learning.repository.UserRepository;
import com.learning.security.jwt.JwtUtils;
import com.learning.security.services.UserDetailsImpl;
import com.learning.service.UserService;
@CrossOrigin("*")
@RestController 
@RequestMapping("/api/auth")
public class UserController {
	
	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	AuthenticationManager authenticationManager;  
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateToken(authentication);
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
		
		List<String> roles = userDetailsImpl.getAuthorities().stream().map(i->i.getAuthority()).collect(Collectors.toList());
		
		
		
		return ResponseEntity.ok(new JwtResponse(jwt, userDetailsImpl.getId(), userDetailsImpl.getUsername(), userDetailsImpl.getEmail(), roles));
	}
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest ){
		
		
		if(userRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity
			          .badRequest()
			          .body(new MessageResponse("Error: Username already exists"));
		}
		
		  if (userRepository.existsByEmail(signupRequest.getEmail())) {
		      return ResponseEntity
		          .badRequest()
		          .body(new MessageResponse("Error: Email already exists"));
		    }
		  
		  
		  User user  = new User(signupRequest.getUsername(), 
				  
				  signupRequest.getEmail(), 
				  signupRequest.getName(),
				  passwordEncoder.encode(signupRequest.getPassword()),
				  signupRequest.getAddress());
		
		  
		  Set<String> strRoles= signupRequest.getRole();
		  
		  Set<Role> roles = new HashSet<>();
		  
		  if(strRoles ==null) {
			  Role userRole = roleRepository.findByRoleName(EROLE.ROLE_USER)
					  .orElseThrow(
							  ()->new RuntimeException("Error:role not found")
							  );
			  roles.add(userRole);
		  }
		  
		  else {
			  strRoles.forEach(e->{
				  switch (e) {
				case "admin":
					Role roleAdmin	= roleRepository.findByRoleName(EROLE.ROLE_ADMIN)
							  .orElseThrow(
									  ()->new RuntimeException("Error:role not found")
									  );
					roles.add(roleAdmin);
					break;
					
						

				default:
					 Role userRole = roleRepository.findByRoleName(EROLE.ROLE_USER)
					  .orElseThrow(
							  ()->new RuntimeException("Error:role not found")
							  );
					 roles.add(userRole);
				}
			  });
			  
			  
			  
		  }
		  user.setRoles(roles);
		  userRepository.save(user);
		return ResponseEntity.status(201).body(new MessageResponse("user created successfully"));
		  
		  
		  
		
	}

	
	
	@PostMapping("/addUser")
	public ResponseEntity<?> addUser(@Valid @RequestBody User register) throws AlreadyExistsException {
		
		
		User result = userService.addUser(register);
		System.out.println(result);
		return ResponseEntity.status(201).body(result);
		
		}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") int id) throws IdNotFoundException{
		User result = userService.getUserById(id);
		return ResponseEntity.ok(result);	
		
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllUserDetails(){
		Optional<List<User>> optional = userService.getAllUserDetails();
		if(optional.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("no record found"));
		}
		return ResponseEntity.ok(optional.get());	
		
	}
	

}