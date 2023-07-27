package com.deborasroka.banky.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="transactions")
public class Transaction {
	@Id
	private String ID;
	private String accountID;
	private String transactionType;
	private String description;
	private double value;
	private String status;
	private LocalDateTime transactionCreationDate;
	
	
	public Transaction() {

	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getTransactionCreationDate() {
		return transactionCreationDate;
	}
	public void setTransactionCreationDate(LocalDateTime transactionCreationDate) {
		this.transactionCreationDate = transactionCreationDate;
	}
	
	
	
	
	
	
	
	
}
