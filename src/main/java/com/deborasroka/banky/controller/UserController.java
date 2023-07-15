package com.deborasroka.banky.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	

	@PostMapping(value = "/addUser")
	public String addUser(@Valid @RequestBody User user){
		try {
			user.setUserCreationDate(LocalDateTime.now());
			System.out.println("This is the user " + user);
				return ("Saved Successfully " + HttpStatus.CREATED);
		} catch (Exception e) {
			return ("Failed to save, please check fields " + e +"  "+ HttpStatus.BAD_REQUEST);
		}
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
}
