package com.deborasroka.banky.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



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
