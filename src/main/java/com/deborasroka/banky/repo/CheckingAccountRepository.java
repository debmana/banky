package com.deborasroka.banky.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.deborasroka.banky.model.CheckingAccount;

public interface CheckingAccountRepository extends MongoRepository<CheckingAccount, String>{

}
