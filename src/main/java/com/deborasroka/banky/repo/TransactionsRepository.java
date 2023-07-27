package com.deborasroka.banky.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.deborasroka.banky.model.Transaction;

public interface TransactionsRepository extends MongoRepository<Transaction, String> {
	
}
