package com.deborasroka.banky.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.annotation.Id;

@Document(value="roles")
public class Role {
	
	@Id
	String Id;
	
	@Field(name = "role", targetType = FieldType.STRING)
	private Roles role;
	
	

	public Role() {

	}

	public Role(String id, Roles role) {
		Id = id;
		this.role = role;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Role [Id=" + Id + ", role=" + role + "]";
	}
	
	
	

}
