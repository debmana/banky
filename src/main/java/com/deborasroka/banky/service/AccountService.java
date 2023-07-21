package com.deborasroka.banky.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deborasroka.banky.model.Account;
import com.deborasroka.banky.repo.AccountRepository;


@Service
@Transactional
public class AccountService {
	
	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
    public List<Account> listAllAccounts() {
        return repository.findAll();
    }
	
    public void save(Account account) {
    	
    	repository.save(account);
    	
    }
    
    public void delete(Account account) {
    	
    	repository.delete(account);
    	
    }
    
    public Account findAccountByID(String id){
    	
    	return repository.findById(id).get();
    	
    }
    
    public Account findAccountByUser(String id){
    	
    	Query query = new Query();
    	query.addCriteria(Criteria.where("ID").is(id));
    	return mongoTemplate.findOne(query, Account.class);
    	
    	
    }
    
    
    
    
    

}
