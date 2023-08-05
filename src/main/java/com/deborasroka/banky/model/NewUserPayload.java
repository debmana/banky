package com.deborasroka.banky.model;

import java.util.Set;

import jakarta.validation.constraints.*;

public class NewUserPayload {
	
	  @NotBlank

	  @NotBlank
	  @Size(max = 50)
	  @Email
	  private String email;

	  private Set<String> role;

	  @NotBlank
	  @Size(min = 6, max = 40)
	  private String password;

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

	  public Set<String> getRole() {
	    return this.role;
	  }

	  public void setRole(Set<String> role) {
	    this.role = role;
	  }

	@Override
	public String toString() {
		return "NewUserPayload [email=" + email + ", role=" + role + ", password=" + password
				+ "]";
	}
	
	
	  
	  

}
