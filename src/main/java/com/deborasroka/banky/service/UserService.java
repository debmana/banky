package com.deborasroka.banky.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deborasroka.banky.model.User;
import com.deborasroka.banky.repo.UserRepository;



@Service
@Transactional
public class UserService {
	
	@Autowired
	UserRepository repository;

    public List<User> listAllUsers() {
        return repository.findAll();
    }
    

    public void saveUser(User user) {
    	repository.save(user);
    }

}
