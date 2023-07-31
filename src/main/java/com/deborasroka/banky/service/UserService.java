package com.deborasroka.banky.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.deborasroka.banky.model.Account;
import com.deborasroka.banky.model.CheckingAccount;
import com.deborasroka.banky.model.User;
import com.deborasroka.banky.repo.UserRepository;



@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private CheckingAccountService checkRepo;
	
	@Autowired
	private AccountService accountRepo;
	
	
	//private TransactionsService transactionRepo;

	
    public List<User> listAllUsers() {
        return repository.findAll();
    }
    
    public Optional<User> findUserByEmail(String email){
    	
    	return repository.findUserByEmail(email);
    	
    }
    
    public Optional<User> findUserByID(String id){
    	
    	return repository.findUserByID(id);
    	
    }
    
    public User findUserByIDNoOptional(String id){
    	
    	try {
    		return repository.findById(id).get();
    	} catch (Exception e) {
    		return null;
    		
    	}
    }
    
    
    
    public Optional<User> findUser(Map<String, String> params){
    	
    	Optional<User> result=null;

       	
       	if (params.containsKey("email")) {
       		result= findUserByEmail(params.get("email"));
       	} 
       		
    	if (params.containsKey("ID")) {
    		result= findUserByID(params.get("ID"));
    	}

    	return result;
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
    
    public boolean updateUser(User user) {
    	boolean updateSuccess = false;
    	Query query = new Query();
    	query.addCriteria(Criteria.where("ID").is(user.getID()));
    	User toUpdate = mongoTemplate.findOne(query, User.class);
    	
    	if(user.getName()!=null){
    		toUpdate.setName(user.getName());
    	}

    	if(user.getEmail()!=null) {
    		toUpdate.setEmail(user.getEmail());
    	}
    	
    	if(user.getPassword()!=null) {
    		toUpdate.setPassword(user.getPassword());
    	}
    	
    	if(user.getUserType()!=null) {
    		toUpdate.setUserType(user.getUserType());
    	}
    	
    	try {
    		mongoTemplate.save(toUpdate);
    		updateSuccess = true;
    	} catch(Exception e) {
    		updateSuccess = false;
    		System.out.println("Could not update user "+e);
    	}
    	
    	return updateSuccess;
    	
    }
    
    public boolean deleteUser(String ID) {
    	boolean done = false;
    	List<Account> accounts;
    	List<CheckingAccount> checkingAccounts;
    	
    	try {
    		repository.deleteById(ID);
    		done = true;
    		
    		accounts = accountRepo.findAccountsByUser(ID);
    		checkingAccounts = checkRepo.findCheckingAccountsByUser(ID);
    		
    		accounts.forEach(account -> accountRepo.delete(account) );
    		checkingAccounts.forEach(account -> accountRepo.delete(account) );
    		
    		
    	} catch (Exception e) {
    		System.out.println("Exception trying to delete user" + e);
    		done = false;
    	}
    	
    	return done;
    }
    
    
    
    
    
    
    
    
    
    
    

}
