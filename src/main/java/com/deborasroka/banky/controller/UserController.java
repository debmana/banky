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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deborasroka.banky.model.Role;
import com.deborasroka.banky.model.Roles;
import com.deborasroka.banky.model.User;
import com.deborasroka.banky.service.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/banky")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(value="/allUsers", produces = {"application/json"})
	@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TESTER')")
	public List<User> list() {
		return userService.listAllUsers();

	}


	@GetMapping(value="/findUser")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TESTER')")
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

	@PostMapping(value = "/addBankUser")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_TESTER')")
	public ResponseEntity<?> addUser(@Valid @RequestBody User user){
		Set<Role> roleSet = new HashSet<Role>();
		Role userRoleSetReady = new Role() ; 
		userRoleSetReady.setRole(Roles.ROLE_USER);
		roleSet.add(userRoleSetReady);

		user.setUserType(roleSet);
		user.setUserCreationDate(LocalDateTime.now());

		try {

			if (userService.saveUser(user)) {
				return new ResponseEntity<>(userService.findUserByEmail(user.getEmail()), HttpStatus.OK);
			} else return new ResponseEntity<>("User already exists ", HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>("Not able to save the user ", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value="/updateUser/{ID}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> updateUser(@PathVariable String ID, @RequestBody User user){

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



	@DeleteMapping(value="/deleteUser/{ID}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public void deleteUser(@PathVariable String ID){

		userService.deleteUser(ID);

	}
}

