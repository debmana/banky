package com.deborasroka.banky.controller;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deborasroka.banky.model.NewUserPayload;
import com.deborasroka.banky.model.Role;
import com.deborasroka.banky.model.Roles;
import com.deborasroka.banky.model.User;
import com.deborasroka.banky.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/banky")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(value="/allUsers", produces = {"application/json" })
	public List<User> list() {
		return userService.listAllUsers();
	}
	
	@Autowired
	PasswordEncoder encoder;

	@PostMapping(value = "/addUser")
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
			 
			//user.setUserCreationDate(LocalDateTime.now());
			////System.out.println("This is the user " + user);
			//user.getUserType();

			
		//System.out.println("Heloooooooooooooooooooo############### "+newUserPayload.toString());
		
		
		
		//return "tada" ;
	}

	@GetMapping("/findUser")
	public Optional<User>  getUser(@RequestParam Map<String, String> params) {

		params.forEach((k,v) -> System.out.println("Key = "
				+ k + ", Value = " + v));
		try {	
			return userService.findUser((params));
		} catch (NoSuchElementException e) {
			System.out.println(e);
			return null;
		}
	}

	@PutMapping("/updateUser/{ID}")
	public ResponseEntity<String> updateUser(@PathVariable String ID, @RequestBody User user){

		if (!ID.isEmpty()) {
			user.setID(ID);
			try {
				userService.updateUser(user);
				return new ResponseEntity<>("Updated sucessfully", HttpStatus.OK);
			} catch(Exception e) {
				System.out.println("User controller could not update request "+ e);
				return new ResponseEntity<>("Update Failed", HttpStatus.CONFLICT);
			}
		}
		return new ResponseEntity<>("Update Failed", HttpStatus.CONFLICT);
	}



	@DeleteMapping("/deleteUser/{ID}")
	public void deleteUser(@PathVariable String ID){

		userService.deleteUser(ID);

	}
}

