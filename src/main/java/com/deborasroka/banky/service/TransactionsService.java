package com.deborasroka.banky.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deborasroka.banky.model.AccountType;
import com.deborasroka.banky.model.CheckingAccount;
import com.deborasroka.banky.model.Transaction;
import com.deborasroka.banky.repo.TransactionsRepository;

@Service
@Transactional
public class TransactionsService {
	
	
	private CheckingAccount genericAccount= new CheckingAccount();
	
	@Autowired
	private CheckingAccountService checkServ;

	@Autowired
	private TransactionsRepository repository;

	@Autowired
	private MongoTemplate mongoTemplate;


	
	
	public List<Transaction> listAllTransactions() {
		return repository.findAll();
	}
	
	
	public List<Transaction> listAllTransactionsFromAccount(String accountID) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("accountID").is(accountID));
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
				
				if (processTransaction(toUpdate)) {
					repository.save(toUpdate);
				} else return "failed to uodate";
		}
		
		return "changes saved successfully";
		
	}
	
	public boolean processTransaction (Transaction transaction) {
		Double result = 0.0;
		Map<String, String> update= new HashMap<>();
		boolean canSubmit=false;
		genericAccount = checkServ.findCheckingAccountByID(transaction.getAccountID());
		
		if (genericAccount!=null) {
			result = Double.sum(transaction.getValue(), genericAccount.getAvailableBalance());
		} else return false; 
		
		
		if (genericAccount.getAccountType().equals(AccountType.SAVINGS)) {
			
			if (result <0) {
			
				return false;
			
			} else {
				
				update.put("availableBalance", Double.toString(result));
				update.put("ID", transaction.getAccountID());
				checkServ.updateAccount(update);
				canSubmit=true;
			}
			
		} else {
			
			if (genericAccount.getAccountType().equals(AccountType.CHECKING)) {
				
				if (result>=0) {
					//newBalance = Double.toString((genericAccount.getAvailableBalance()) + (transaction.getValue()));
					//System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++    Hello I am the new Balance "+newBalance);
					update.put("availableBalance", Double.toString(result));
					update.put("ID", transaction.getAccountID());
					checkServ.updateAccount(update);
					canSubmit=true;
				
				} else {

					
					Double overdraftResult = Double.sum(transaction.getValue(), genericAccount.getOverdraftLimit());
					
					if (overdraftResult<0) {
						return false;
					} else {
						//newBalance = Double.toString(genericAccount.getOverdraftLimit()- transaction.getValue());
						update.put("overdraftLimit", Double.toString(overdraftResult));
						update.put("ID", transaction.getAccountID());
						checkServ.updateAccount(update);
						canSubmit=true;
					}
				}
				
			}
			
		}
		
		return canSubmit;
	}
	
}
