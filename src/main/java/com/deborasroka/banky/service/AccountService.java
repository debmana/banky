package com.deborasroka.banky.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deborasroka.banky.model.Account;
import com.deborasroka.banky.model.Transaction;
import com.deborasroka.banky.repo.AccountRepository;


@Service
@Transactional
public class AccountService {

	@Autowired
	private AccountRepository repository;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Lazy
	@Autowired
	private TransactionsService transactionRepo;
	


	public List<Account> listAllAccounts() {
		return repository.findAll();
	}

	public void save(Account account) {

		repository.save(account);

	}

	public void delete(Account account) {
		
		List<Transaction> transactions;
		
		transactions=(transactionRepo.listAllTransactionsFromAccount(account.getID()));
		transactions.forEach(transaction -> transactionRepo.deleteTransaction(transaction));
		

		repository.delete(account);

	}

	public Account findAccountByID(String id){

		return repository.findById(id).get();

	}

	public List<Account> findSavingAccountsByUser(String id){

		Query query = new Query();
		query.addCriteria(Criteria.where("userID").is(id).and("AccountType").is("SAVINGS"));
		return mongoTemplate.find(query, Account.class);

	}

	public List<Account> findAccountsByUser(String id){

		Query query = new Query();
		query.addCriteria(Criteria.where("userID").is(id).and("AccountType").is("SAVINGS"));
		return mongoTemplate.find(query, Account.class);

	}

	
	public void updateAccount(Map <String, String> updates) {
		String id = null;

		if (updates.containsKey("ID")){

			id = updates.get("ID");
			Query query = new Query();
			query.addCriteria(Criteria.where("ID").is(id));
			Account toUpdate = mongoTemplate.findOne(query, Account.class);
			System.out.println("This is the update for account "+toUpdate);

			if (toUpdate != null) {

				try {
					if (updates.containsKey("availableBalance")) {
						toUpdate.setAvailableBalance(Double.parseDouble(updates.get("availableBalance")));
					}
				} catch(Exception e) {
					System.out.println("Tried to update account available balance and got error: "+e);
				}

				try {
					if (updates.containsKey("currentBalance")) {
						toUpdate.setCurrentBalance(Double.parseDouble(updates.get("currentBalance")));
					}
				} catch (Exception e) {
					System.out.println("Error trying to update current value in account service "+e);
				}

				repository.save(toUpdate);

			} else {
				System.out.println("The account you are trying to updade doesnt exist ");

			}
		}



	}










}
