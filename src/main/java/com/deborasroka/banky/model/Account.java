package com.deborasroka.banky.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


@Document(value="accounts")
public class Account implements AccountInterface {
	@Id
	private String ID;
	private Double currentBalance;
	private Double availableBalance;
	private String userID;
	@Field(name = "AccountType", targetType = FieldType.STRING)
	private AccountType accountType;
	private LocalDateTime accountCreationDate;
	
	public Account() {
	}
	
	public Account(double currentBalance, double availableBalance, String userID, AccountType accountType,
			LocalDateTime accountCreationDate) {
		super();
		this.currentBalance = currentBalance;
		this.availableBalance = availableBalance;
		this.userID = userID;
		this.accountType = accountType;
		this.accountCreationDate = accountCreationDate;
	}
	
	

	@Override
	public String toString() {
		return "Account [ID=" + ID + ", currentBalance=" + currentBalance + ", availableBalance=" + availableBalance
				+ ", user ID=" + userID + ", accountType=" + accountType + ", accountCreationDate=" + accountCreationDate
				+ "]";
	}

	public String getID() {
		return ID;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	public double getCurrentBalance() {
		return currentBalance;
	}
	
	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}
	
	public double getAvailableBalance() {
		return availableBalance;
	}
	
	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUser(String userID) {
		this.userID = userID;
	}
	
	public AccountType getAccountType() {
		return accountType;
	}
	
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	
	public LocalDateTime getAccountCreationDate() {
		return accountCreationDate;
	}
	
	public void setAccountCreationDate(LocalDateTime accountCreationDate) {
		this.accountCreationDate = accountCreationDate;
	}
	
	
	

}
