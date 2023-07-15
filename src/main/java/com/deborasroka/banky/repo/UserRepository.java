package com.deborasroka.banky.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.deborasroka.banky.model.User;


public interface UserRepository  extends MongoRepository<User, String>{
	
	Optional<User> findUserByEmail(String email);
	Optional<User> findUserByID(String ID);

}
