package com.deborasroka.banky.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/banky")
public class UserController {
	//private UserRepository repository;
	
	@Autowired
    UserService userService;
	
	@GetMapping(value="/allUsers", produces = {"application/json" })
    public List<User> list() {


        return userService.listAllUsers();
    }
	

	@PostMapping(value = "/addUser")
	public String addUser(@RequestBody User user){
		//user.getUserCreationDate().getTime();
		System.out.println("USer obj:" + user + "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
	userService.saveUser(user);
		//repository.insert(user);
	        return "User created successfully";
	    }
	
}
