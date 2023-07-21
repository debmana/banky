package com.deborasroka.banky.model;


import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="accounts")
@TypeAlias(value="CREDITCARD")

public class CreditCardAccount extends LoanAccount{
	
	public CreditCardAccount() {
	}
	
	
	
}
