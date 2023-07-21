package com.deborasroka.banky.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(value="accounts")
@TypeAlias(value="PERSONAL_LOAN")
public class PersonalLoanAccount extends LoanAccount{

	public PersonalLoanAccount() {
		
	}
	

}
