package com.deborasroka.banky.controller;

import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.deborasroka.banky.model.Transaction;
import com.deborasroka.banky.service.TransactionsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/banky")
public class TransactionController {
	
	@Autowired
	private TransactionsService repository;
	
	@GetMapping(value="/listAllTransactions")
	public List<Transaction> listAllTransactions(){
		
		return repository.listAllTransactions();
	}
	
	@GetMapping(value="/listAllTransactionsByAccount/{accountID}")
	public List<Transaction> listAllTransactionsByAccount(@PathVariable String accountID){
		
		return repository.listAllTransactionsFromAccount(accountID);
	}
	
	
	@PostMapping(value="/addTransaction")
	public HttpStatus addTransaction(@Valid @RequestBody Transaction transaction) {
		transaction.setTransactionCreationDate(LocalDateTime.now());
		if (repository.processTransaction(transaction)) {
			repository.createTransaction(transaction);
			return HttpStatus.OK;
		} else return HttpStatus.CONFLICT;
	}
	
	@PutMapping(value="/updateTransaction")
	public void updateTransaction(@Valid @RequestBody Map<String, String> body) {
		repository.updateTransaction(body);
		
	}
	
	@DeleteMapping(value="/deleteTransaction/{transactionID}")
	public void deleteTransaction(@PathVariable String transactionID) {
		repository.deleteTransaction(null);
		
	}
	
	

}
