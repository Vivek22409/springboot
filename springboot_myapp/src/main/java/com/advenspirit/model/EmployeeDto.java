package com.advenspirit.model;

import java.time.Instant;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

public class EmployeeDto {

	private String empId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String password;
	private String empType;
	private Instant regTime;

	public EmployeeDto() {
	}

	public EmployeeDto(String empId, String firstName, String lastName, String emailId, String empType,
			Instant regTime) {
		super();
		this.empId = empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;		
		this.empType = empType;
		this.regTime = regTime;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public Instant getRegTime() {
		return regTime;
	}

	public void setRegTime(Instant regTime) {
		this.regTime = regTime;
	}

}
