package com.deborasroka.banky.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deborasroka.banky.model.CheckingAccount;
import com.deborasroka.banky.repo.CheckingAccountRepository;

@Service
@Transactional
public class CheckingAccountService {
	
	@Autowired
	private CheckingAccountRepository rep;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	public List<CheckingAccount> listAllAccounts() {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("AccountType").is("CHECKING"));
		List<CheckingAccount> accounts =  mongoTemplate.find(query, CheckingAccount.class);
		return accounts;
	}
	
	public void save(CheckingAccount checkingAccount) {
		
		rep.save(checkingAccount);

	}
	
	public void delete(CheckingAccount checkingAccount) {

		rep.delete(checkingAccount);
	}
	
	public CheckingAccount findCheckingAccountByID(String id){

		return rep.findById(id).get();

	}
	
	public List<CheckingAccount> findCheckingAccountsByUser(String id){

		Query query = new Query();
		query.addCriteria(Criteria.where("userID").is(id).and("AccountType").is("CHECKING"));
		return mongoTemplate.find(query, CheckingAccount.class);

	}
	
	public void updateAccount(Map <String, String> updates) {
		String id = null;

		if (updates.containsKey("ID")){

			id = updates.get("ID");
			Query query = new Query();
			query.addCriteria(Criteria.where("ID").is(id));
			CheckingAccount toUpdate = mongoTemplate.findOne(query, CheckingAccount.class);
			System.out.println("This is the update for checking account "+toUpdate);

			if (toUpdate != null) {

				try {
					if (updates.containsKey("availableBalance")) {
						System.out.println("Hello I am here in the try block ....");
						toUpdate.setAvailableBalance(Double.parseDouble(updates.get("availableBalance")));
					}
				} catch(Exception e) {
					System.out.println("Tried to update account available balance and got error: "+e);
				}

				try {
					if (updates.containsKey("currentBalance")) {
						System.out.println("Hello I am here in the try block 2 ....");
						toUpdate.setCurrentBalance(Double.parseDouble(updates.get("currentBalance")));
					}
				} catch (Exception e) {
					System.out.println("Error trying to update current value in account service "+e);
				}
				
				try {
					if (updates.containsKey("overdraftLimit")) {
						System.out.println("Hello I am here in the try block 3 ....");
						toUpdate.setOverdraftLimit(Double.parseDouble(updates.get("overdraftLimit")));
					}
				} catch (Exception e) {
					System.out.println("Error trying to update current value in account service "+e);
				}

				rep.save(toUpdate);

			} else {
				System.out.println("The account you are trying to updade doesnt exist ");

			}
		}
	}
	
	
	
	
	

}
