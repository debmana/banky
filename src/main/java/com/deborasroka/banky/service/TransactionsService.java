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
	
	
	public List<Transaction> listAllTransactionsFromAccount(String accountID) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("ID").is(accountID));
		return mongoTemplate.find(query, Transaction.class);

	}
	
	public Optional<Transaction> findTransaction(String transactionID) {
		
		return repository.findById(transactionID);
	}
	
	
	public String createTransaction(Transaction transaction) {
		repository.save(transaction);
		
		return "Transaction created";
		
	}
	
	public String deleteTransaction(Transaction transaction) {
		repository.delete(transaction);
		return "Transaction deleted";
		
	}
	
	public String updateTransaction(Map<String, String> transaction) {
		
		Transaction toUpdate;
		
		
		if (transaction.containsKey("ID")) {
			toUpdate = findTransaction(transaction.get("ID")).get();
			if (toUpdate==null) {
				return "transaction not found";
			} else
				//toUpdate.setAccountID(transaction.get("ID"));
				if(transaction.containsKey("transactionType")) {
					toUpdate.setTransactionType(transaction.get("transactionType"));
				}
				
				if (transaction.containsKey("description")) {
					toUpdate.setDescription(transaction.get("description"));
				}
				
				if (transaction.containsKey("value")) {
					try {
						toUpdate.setValue(Double.parseDouble(transaction.get("value")));
					} catch(Exception e) {
						System.out.println("error while parsing transaction value "+e);
						return "transaction value invalid";
					}
				}
				
				if (transaction.containsKey("status")) {
					toUpdate.setStatus(transaction.get("status"));
				}
				
				repository.save(toUpdate);
		}
		
		return "changes saved successfully";
		
	}
	
}
