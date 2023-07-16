package com.deborasroka.banky.model;

import java.time.LocalDateTime;

public abstract class Account implements AccountInterface {
	
	int ID;
	double currentBalance;
	double availableBalance;
	User user;
	AccountType accountType;
	LocalDateTime accountCreationDate;

}
