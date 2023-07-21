package com.deborasroka.banky.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.deborasroka.banky.model.Account;

public interface AccountRepository extends MongoRepository<Account, String> {
    
}
