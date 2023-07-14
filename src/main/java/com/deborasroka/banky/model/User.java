package com.deborasroka.banky.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(value="users")
public class User {
	
	@Id
	private String ID;

	private String name;
	private String email;
	private String password;
	private String userType;
	private Date userCreationDate;
	
	
	@Override
	public String toString() {
		return "User [ID=" + ID + ", name=" + name + ", email=" + email + ", Password=" + password + ", userType="
				+ userType + ", userCreationDate=" + userCreationDate + "]";
	}
	
	
	public User(String name, String email, String password, String userType) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.userType = userType;
	}




	public String getID() {
		return this.ID;
	}
	public void setID(String iD) {
		this.ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Date getUserCreationDate() {
		return userCreationDate;
	}
	public void setUserCreationDate(Date userCreationDate) {
		this.userCreationDate = userCreationDate;
	}
}
