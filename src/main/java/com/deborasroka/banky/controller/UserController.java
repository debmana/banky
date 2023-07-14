package com.deborasroka.banky.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deborasroka.banky.model.User;
import com.deborasroka.banky.service.UserService;



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
		System.out.println("USer obj:" + user + "$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
	userService.saveUser(user);
	        return "User created successfully";
	    }
	
}
