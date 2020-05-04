package com.advenspirit.entity;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

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

@Entity
@Table
public class LoginLogoutHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "mailId", nullable = false)
	private String emailId;
	private String location;
	private String empType;
	private String loginTime;
	private String logoutTime;
	private String token;
	

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
	

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime() {
		
		//LocalDateTime localDateTime = LocalDateTime.now(Clock.offset(Clock. systemUTC(),Duration.ofMinutes(330)));
		LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("Asia/Kolkata")));
		//LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of(TimeZone.getDefault().getID())));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");
		this.loginTime = formatter.format(localDateTime);
	}

	public String getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime() {
		//LocalDateTime localDateTime = LocalDateTime.now(Clock.offset(Clock.systemUTC(),Duration.ofMinutes(330)));
		//LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of(TimeZone.getDefault().getID())));
		LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("Asia/Kolkata")));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss");
		this.logoutTime = formatter.format(localDateTime);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
