package com.deborasroka.banky.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.deborasroka.banky.model.User;


public interface UserRepository  extends MongoRepository<User, String>{
	

}
