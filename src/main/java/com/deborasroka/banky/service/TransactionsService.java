package com.deborasroka.banky.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deborasroka.banky.model.Transaction;
import com.deborasroka.banky.repo.TransactionsRepository;

@Service
@Transactional
public class TransactionsService {
	

	@Autowired
	private TransactionsRepository repository;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	public List<Transaction> listAllTransactions() {
		return repository.findAll();
	}
	
	
	public List<Transaction> listAllTransactionsFromAccount() {
		return repository.findAll();
	}
	
	public List<Transaction> listAllTransactionsFromUser() {
		return repository.findAll();
	}
	
	public String createTransaction(Transaction transaction) {
		
		return "hello";
		
	}
	
	public String deleteTransaction(Transaction transaction) {
		
		return "hello";
		
	}
	
	public String updateTransaction(Map<String, String> transaction) {
		
		return "hello";
		
	}
	
}
