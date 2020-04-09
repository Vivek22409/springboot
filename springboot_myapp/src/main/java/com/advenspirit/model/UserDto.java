package com.advenspirit.model;

public class UserDto {

	private long id;
	private String name;
	private String emailId;

	public UserDto(long id, String name, String emailId) {
		super();
		this.id = id;
		this.name = name;
		this.emailId = emailId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
