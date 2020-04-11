package com.advenspirit.controller;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.advenspirit.entity.Employee;
import com.advenspirit.exception.ResourceNotFoundException;
import com.advenspirit.model.EmployeeDto;
import com.advenspirit.model.Response;
import com.advenspirit.service.EmployeeService;

@RestController
@RequestMapping("/api/v1")
public class RegistrationController {

	@Autowired
	private EmployeeService empService;

	@GetMapping("/message")
	public String greetMessage() {
		return "hello";
	}

	@GetMapping("/employee/{empId}")
	public ResponseEntity<Object> getUser(@PathVariable("empId") String empId){
		EmployeeDto empDto;
		try {
			empDto = empService.findEmployee(empId);
		} catch (ResourceNotFoundException ex) {
			Response response = new Response(901,"RESOURCE_NOT_FOUND",ex.getMessage());
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.ok(empDto);
	}

	@PostMapping("/employee")
	public ResponseEntity<Response> addEmployee(@RequestBody EmployeeDto empDto) throws SQLException{
		Response response = empService.addEmployee(empDto);
		return ResponseEntity.ok(response); 

	}

}
