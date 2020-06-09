package com.nagarro.emp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.nagarro.emp.controller.EmployeeController;
import com.nagarro.emp.dao.EmployeeRepository;
import com.nagarro.emp.entity.Employee;
import com.nagarro.emp.exception.EmployeeNotFoundException;
import com.nagarro.emp.model.EmployeeDto;
import com.nagarro.emp.model.LoginRequest;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeRepository empRepo;

	@Override
	public String saveEmployee(EmployeeDto empDto) throws Exception {
		logger.info("entered in saveEmployee method of Employee Service");

		String status = "success";
		Employee emp = new Employee(empDto.getName(), empDto.getEmailId(), empDto.getPassword(),
				empDto.getDepartment());

		empRepo.save(emp);

		logger.info("employee saved successfully with name: " + emp.getName());
		logger.info("exited in saveEmployee method of Employee Service");

		return status;
	}

	@Override
	@Cacheable(value = "employees", key = "#empId", condition = "#empId>0")
	public EmployeeDto findEmployee(Integer empId) throws EmployeeNotFoundException {
		logger.info("findEmployee in saveEmployee method of Employee Service");

		System.out.println("########################");

		Employee emp = empRepo.findById(empId).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
		EmployeeDto empDto = new EmployeeDto(emp.getEmpId().toString(), emp.getName(), emp.getEmailId(),
				emp.getPassword(), emp.getDepartment());
		
		
		return empDto;
	}

	@Override
	public EmployeeDto authenticateEmployee(LoginRequest loginRequest) throws Exception {
		
		logger.info("authenticateEmployee in saveEmployee method of Employee Service");
		
		boolean authStatus = false;
		EmployeeDto empDto = new EmployeeDto();

		Employee emp = empRepo.findEmployeeByEmailId(loginRequest.getEmailId())
				.orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
		empDto = new EmployeeDto(emp.getEmpId().toString(),emp.getName(),emp.getEmailId(),emp.getPassword(),emp.getDepartment());
		
		if (loginRequest.getEmailId().equals(empDto.getEmailId())
				&& loginRequest.getPassword().substring(0, 3).equals(empDto.getPassword().substring(0, 3))) {
			authStatus = true;
		}

		if (authStatus == true && empDto != null) {
			return empDto;
		}

		return null;
	}

}
