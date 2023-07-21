package com.deborasroka.banky.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.deborasroka.banky.model.PersonalLoanAccount;

public interface PersonalLoanAccountRepository extends MongoRepository<PersonalLoanAccount, String> {

}
