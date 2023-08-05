package com.deborasroka.banky.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

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
	//private String userType;
	@Field(name = "role", targetType = FieldType.STRING)
	private Set<Role> userType = new HashSet<>();

	private LocalDateTime userCreationDate;
	
	//private Set<Role> roles = new HashSet<>();


	@Override
	public String toString() {
		return "User [ID=" + ID + ", name=" + name + ", email=" + email + ", Password=" + password + ", userType="
				+ userType.toString() + ", userCreationDate=" + userCreationDate + "]";
	}

	public User(String name, String email, String password, Set<Role> userType) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.userType = userType;
	}




	public User() {
		// TODO Auto-generated constructor stub
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
	public Set<Role> getUserType() {
		return userType;
	}
	public void setUserType(Set<Role> userType) {
		this.userType = userType;
	}
	public LocalDateTime getUserCreationDate() {
		return userCreationDate;
	}
	public void setUserCreationDate(LocalDateTime userCreationDate) {
		this.userCreationDate = userCreationDate;
	}


	
}
