package com.advenspirit.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.advenspirit.entity.Employee;
import com.advenspirit.exception.ResourceNotFoundException;
import com.advenspirit.model.EmployeeDto;
import com.advenspirit.model.Response;
import com.advenspirit.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository empRepo;

	public EmployeeDto findEmployee(String empId) throws ResourceNotFoundException {

		Employee emp = empRepo.findByEmpId(empId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :" + empId));
		EmployeeDto empDto = new EmployeeDto(emp.getEmpId(), emp.getFirstName(), emp.getLastName(), emp.getEmailId(),
				emp.getEmpType(), emp.getRegTime());
		empDto.setPassword("empty");
		return empDto;
	}

	public Response addEmployee(EmployeeDto empDto) {

		Response response = new Response(900, "EMPLOYEE_NOT_SAVED", "Not able to save employee successfully");

		Employee emp = new Employee(empDto.getEmpId(), empDto.getFirstName(), empDto.getLastName(), empDto.getEmailId(),
				empDto.getPassword(), empDto.getEmpType());

		try {
			empRepo.save(emp);
		} catch (DataIntegrityViolationException ex) {
			response.setCode(901);
			response.setMessage("DATAINTEGRITY_VIOLATED");
			response.setDescription("EmailId field already exist in db");
			return response;
		}

		response.setCode(200);
		response.setMessage("EMPLOYEE_SAVED");
		response.setDescription("Able to save employee successfully");

		return response;
	}

}
