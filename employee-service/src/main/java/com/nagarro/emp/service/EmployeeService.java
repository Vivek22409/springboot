package com.nagarro.emp.service;

import com.nagarro.emp.exception.EmployeeNotFoundException;
import com.nagarro.emp.model.EmployeeDto;
import com.nagarro.emp.model.LoginRequest;

public interface EmployeeService {
	
	public String saveEmployee(EmployeeDto empDto) throws Exception;
	public EmployeeDto findEmployee(Integer empId) throws EmployeeNotFoundException;
	public EmployeeDto authenticateEmployee(LoginRequest loginRequest) throws Exception;
	
}
