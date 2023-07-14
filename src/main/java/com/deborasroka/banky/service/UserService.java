package com.deborasroka.banky.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deborasroka.banky.model.User;
import com.deborasroka.banky.repo.UserRepository;



@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
    public List<User> listAllUsers() {
        return repository.findAll();
    }
    

    public boolean saveUser(User user) {
    	boolean accepted = false;
    	Query query = new Query();
    	query.addCriteria(Criteria.where("email").is(user.getEmail()));
    	List<User> users = mongoTemplate.find(query, User.class);
    	
    	if (users.size() > 0) {
    		throw new IllegalStateException("This email is already being used "+user.getEmail());
    	}
    	
    	if (users.isEmpty()) {
    		accepted = true;
    		repository.save(user);
    	}
    	
    	return accepted;
    }

}
