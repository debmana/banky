package com.deborasroka.banky.model;

import java.util.Date;

public abstract class Account implements AccountInterface {
	
	int ID;
	double currentBalance;
	double availableBalance;
	User user;
	AccountType accountType;
	Date accountCreationDate;

}
