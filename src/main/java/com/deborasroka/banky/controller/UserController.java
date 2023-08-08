package com.deborasroka.banky.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.deborasroka.banky.model.User;
import com.deborasroka.banky.service.UserService;


@RestController
@RequestMapping("/api/v1/banky")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(value="/allUsers", produces = {"application/json" })
	public List<User> list() {
		return userService.listAllUsers();
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

