package com.advenspirit.entity;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "mailId"))
public class Employee {
	@Id
	private String empId;
	private String firstName;
	private String lastName;
	@Column(name = "mailId", nullable = false)
	private String emailId;
	private String password;
	private String empType;
	private final Instant regTime = Instant.now();
	
	 /*@OneToOne(fetch = FetchType.LAZY,cascade =  CascadeType.ALL,mappedBy = "emailId")
	 private LoginLogoutHistory loginLogoutHistory;*/

	public Employee() {
	}

	public Employee(String empId, String firstName, String lastName, String emailId, String password, String empType) {
		super();
		this.empId = empId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.password = password;
		this.empType = empType;
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


}
