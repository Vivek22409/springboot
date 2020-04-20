package com.advenspirit.model;

public class LoginRequest {

	private String emailId;
	private String password;
	private String location;
	
	public LoginRequest() {
		
	}

	public LoginRequest(String emailId, String password, String location) {
		super();
		this.emailId = emailId;
		this.password = password;
		this.location = location;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
