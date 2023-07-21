package com.deborasroka.banky.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="accounts")
@TypeAlias(value="CHECKING")
public class CheckingAccount extends Account{


	double overdraftLimitoverdraftLimit;
	
	public CheckingAccount() {
		
	}
	


	@Override
	public String toString() {
		return "CheckingAccount [overdraftLimitoverdraftLimit=" + overdraftLimitoverdraftLimit + ", toString()="
				+ super.toString() + ", getID()=" + getID() + ", getCurrentBalance()=" + getCurrentBalance()
				+ ", getAvailableBalance()=" + getAvailableBalance() + ", getUser()=" + getUserID()
				+ ", getAccountType()=" + getAccountType() + ", getAccountCreationDate()=" + getAccountCreationDate()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
	
	
	public double getOverdraftLimitoverdraftLimit() {
		return overdraftLimitoverdraftLimit;
	}

	public void setOverdraftLimitoverdraftLimit(double overdraftLimitoverdraftLimit) {
		this.overdraftLimitoverdraftLimit = overdraftLimitoverdraftLimit;
	}
	
	
	
	
}
