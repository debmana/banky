package com.deborasroka.banky.model;

import java.util.Date;

public abstract class Account implements AccountInterface {
	
	int ID;
	double balance;
	User user;
	String accountType;
	Date accountCreationDate;

}
