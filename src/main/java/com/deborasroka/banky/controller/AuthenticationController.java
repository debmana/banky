package com.deborasroka.banky.controller;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deborasroka.banky.model.JwtResponse;
import com.deborasroka.banky.model.LoginRequestPayload;
import com.deborasroka.banky.model.NewUserPayload;
import com.deborasroka.banky.model.Role;
import com.deborasroka.banky.model.Roles;
import com.deborasroka.banky.model.User;
import com.deborasroka.banky.security.jwt.JwtUtils;
import com.deborasroka.banky.security.services.UserDetailsImpl;
import com.deborasroka.banky.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	@Autowired
	PasswordEncoder encoder;


	@Autowired
	UserService userService;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	AuthenticationManager authenticationManager;


	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestPayload loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();    
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
				userDetails.getId(), 
				userDetails.getEmail(), 
				roles));
	}

	@PostMapping(value = "/signup")
	public String addUser(@Valid @RequestBody NewUserPayload newUserPayload){
		User user = new User();
		Set<String> userRoleSet = new HashSet<String>(newUserPayload.getRole());
		Role testRoleSetReady = new Role() ; 
		Role adminRoleSetReady = new Role() ; 
		Role userRoleSetReady = new Role() ; 
		Set<Role> roleSet = new HashSet<Role>();

		System.out.println("Hello world ###################################### "+ newUserPayload.getRole());



		user.setEmail(newUserPayload.getEmail());
		user.setPassword(encoder.encode(newUserPayload.getPassword()));
		user.setUserCreationDate(LocalDateTime.now());


		if (!userRoleSet.isEmpty()) {

			if (userRoleSet.contains("ROLE_USER")) {

				userRoleSetReady.setRole(Roles.ROLE_USER);

				roleSet.add(userRoleSetReady);
				for (Role role : roleSet) {
					System.out.println("###############################  This is role set first block "+role.getRole() );
				}

				// System.out.println(("###############################  Final set 1 " +roleSet));

			} 

			if (userRoleSet.contains("ROLE_ADMIN")) {
				adminRoleSetReady.setRole(Roles.ROLE_ADMIN);
				roleSet.add(adminRoleSetReady);

				for (Role role : roleSet) {
					System.out.println("##############################  This is role set second block "+role.getRole() );
				}

				//System.out.println(("###############################  Final set 2 " +roleSet));
			} 

			if (userRoleSet.contains("ROLE_TESTER")) {
				testRoleSetReady.setRole(Roles.ROLE_TESTER);
				roleSet.add(testRoleSetReady);
				for (Role role : roleSet) {
					System.out.println("#############################  This is role set third block "+role.getRole() );
				}



			} 


			for (Role role : roleSet) {
				System.out.println("#############################  TOut of the IF block "+role.getRole() );
			}
			//System.out.println(("###############################  Final set 3 " +roleSet));

			user.setUserType(roleSet);


		} else return "Roles used are invalid";

		System.out.println("#################################### this is the user to be saved " +user);

		try { 
			userService.saveUser(user);
			return "User saved successfully!" + HttpStatus.CREATED;
		} catch(Exception e) {

			return "Error while saving user, email already in use " + HttpStatus.CONFLICT;
		}

	}


}
