package com.deborasroka.banky.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Document(value="users")
public class User {
	
	@Id
	private String ID;
	
	@NotBlank
	private String name;
	
	@Email  @NotBlank
	@Indexed(unique = true)
	private String email;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String userType;
	
	private LocalDateTime userCreationDate;
	
	
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
	public LocalDateTime getUserCreationDate() {
		return userCreationDate;
	}
	public void setUserCreationDate(LocalDateTime userCreationDate) {
		this.userCreationDate = userCreationDate;
	}
}
