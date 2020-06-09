package com.nagarro.emp.model;

public class LoginResponse {

	private String token;
	private EmployeeDto empDto;

	public LoginResponse() {
		super();
	}

	public LoginResponse(String token, EmployeeDto empDto) {
		super();
		this.token = token;
		this.empDto = empDto;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public EmployeeDto getEmpDto() {
		return empDto;
	}

	public void setEmpDto(EmployeeDto empDto) {
		this.empDto = empDto;
	}

}
