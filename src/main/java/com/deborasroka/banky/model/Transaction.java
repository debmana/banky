package com.deborasroka.banky.model;

import java.util.Date;

public abstract class Transaction {
	int ID;
	Account account;
	String transactionType;
	String description;
	double value;
	String status;
	Date transactionCreationDate;
	
}
