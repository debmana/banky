package com.deborasroka.banky.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.deborasroka.banky.model.CreditCardAccount;


public interface CreditCardAccountRepository extends MongoRepository<CreditCardAccount, String>{

}
