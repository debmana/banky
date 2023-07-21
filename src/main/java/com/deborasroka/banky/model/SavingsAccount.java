package com.deborasroka.banky.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="accounts")
@TypeAlias(value="SAVINGS")
public class SavingsAccount extends Account{
	
	public SavingsAccount() {
		
	}

	
}
