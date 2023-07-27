package com.deborasroka.banky.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(value="accounts")
@TypeAlias(value="CHECKING")
public class CheckingAccount extends Account{

	@Field(name = "overdraftLimit")
	private double overdraftLimit;

	public CheckingAccount(double overdraftLimit) {
		super();
		this.overdraftLimit = overdraftLimit;
	}

	public CheckingAccount() {

	}

	@Override
	public String toString() {
		return "CheckingAccount [overdraftLimit=" + overdraftLimit + ", toString()="
				+ super.toString() + ", getID()=" + getID() + ", getCurrentBalance()=" + getCurrentBalance()
				+ ", getAvailableBalance()=" + getAvailableBalance() + ", getUser()=" + getUserID()
				+ ", getAccountType()=" + getAccountType() + ", getAccountCreationDate()=" + getAccountCreationDate()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
		
	public double getOverdraftLimit() {
		return overdraftLimit;
	}

	public void setOverdraftLimit(double overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}
	
	
	
	
	
	
}
