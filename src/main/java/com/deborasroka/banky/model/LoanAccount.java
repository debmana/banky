package com.deborasroka.banky.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="accounts")
@TypeAlias(value="LOAN")
public abstract class LoanAccount extends Account {

	double outstandingBalance;
	double availableCredit;
	double limit;
	
	
	public LoanAccount() {
		
	}
	

	public LoanAccount(double outstandingBalance, double availableCredit, double limit) {
		super();
		this.outstandingBalance = outstandingBalance;
		this.availableCredit = availableCredit;
		this.limit = limit;
	}



	public double getLimit() {
		return limit;
	}



	public void setLimit(double limit) {
		this.limit = limit;
	}



	public double getOutstandingBalance() {
		return outstandingBalance;
	}

	public void setOutstandingBalance(double outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}

	public double getAvailableCredit() {
		return availableCredit;
	}

	public void setAvailableCredit(double availableCredit) {
		this.availableCredit = availableCredit;
	}

	@Override
	public String toString() {
		return "LoanAccount [outstandingBalance=" + outstandingBalance + ", availableCredit=" + availableCredit + "]";
	}
	
	
	
	
}
