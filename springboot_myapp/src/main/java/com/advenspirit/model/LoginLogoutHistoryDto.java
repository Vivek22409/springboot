package com.advenspirit.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

public class LoginLogoutHistoryDto {

	private Long id;
	private String emailId;
	private String location;
	private String empType;
	private Instant loginTime;
	private Instant logoutTime;
	private EmployeeDto employee;

	public LoginLogoutHistoryDto(Long id, String emailId, String location, String empType, Instant loginTime,
			Instant logoutTime, EmployeeDto employee) {
		super();
		this.id = id;
		this.emailId = emailId;
		this.location = location;
		this.empType = empType;
		this.loginTime = loginTime;
		this.logoutTime = logoutTime;
		this.employee = employee;
	}

	public LoginLogoutHistoryDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public Instant getLoginTime() {
		return loginTime;
	}

	public Instant getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Instant logoutTime) {
		this.logoutTime = logoutTime;
	}

	public EmployeeDto getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDto employee) {
		this.employee = employee;
	}

	public void setLoginTime(Instant loginTime) {
		this.loginTime = loginTime;
	}

}
