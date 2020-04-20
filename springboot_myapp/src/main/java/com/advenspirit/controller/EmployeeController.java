package com.advenspirit.controller;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.advenspirit.exception.ResourceNotFoundException;
import com.advenspirit.model.EmployeeDto;
import com.advenspirit.model.LoginLogoutHistoryDto;
import com.advenspirit.model.LoginRequest;
import com.advenspirit.model.LoginResponse;
import com.advenspirit.model.Response;
import com.advenspirit.service.EmployeeService;
import com.advenspirit.util.JwtUtil;

//@CrossOrigin(origins ="http://192.168.1.108:4200",allowCredentials ="true")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private EmployeeService empService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/message")
	public String greetMessage() {
		return "hello";
	}

	@CrossOrigin(origins = "http://192.168.1.108:4200", allowCredentials = "true")
	@PostMapping("/login")
	public ResponseEntity<Response> authenticateAndCreateToken(@RequestBody LoginRequest loginRequest) {

		final EmployeeDto empDto = empService.authenticateEmployee(loginRequest);
		Response response = new Response(902, "USER_NOT AUTHETICATED", "User not authenticated");
		response.setObject("empty");

		if (empDto != null) {
			response = new Response(200, "USER_AUTHENTICATED", "User authenticated successfully");
			final String jwtToken = jwtUtil.generateToken(empDto);
			empService.writeLoginAndLogoutDetails(empDto, loginRequest.getLocation(), jwtToken);
			final LoginResponse loginResponse = new LoginResponse(jwtToken, empDto);
			response.setObject(loginResponse);
		}

		return ResponseEntity.ok(response);

	}

	@GetMapping("/logout/{token}")
	public ResponseEntity<Response> recordLogOutHistory(@PathVariable("token") String token) {
		Response response = new Response(904, "PROBLEM_IN_LOGOUT_HISTORY_RECORDED", "Logout time  not captured");
		try {
			empService.recordLogOutHistory(token);
			response = new Response(200, "LOGOUT_HISTORY_RECORDED", "Logout time captured");
		} catch (ResourceNotFoundException ex) {
			logger.error("Problem while recording logout history");
		}
		return ResponseEntity.ok(response);
	}

	@GetMapping("/loginLogoutHistory")
	public ResponseEntity<Response> getLoginLogoutHistory() {
		Response response = new Response(905, "PROBLEM_IN_EXTRACTING_LOGOUT_HISTORY_RECORDED",
				"Login and Logout history not able to extract");
		List<LoginLogoutHistoryDto> linLoutHisDtoList = null;
		
		try {
			linLoutHisDtoList = empService.getLoginLogoutHistory(/* emailId */);
			response = new Response(200, "EXTRACTED_LOGOUT_HISTORY_RECORDED",
					"Login and Logout history able to extract");
		} catch (ResourceNotFoundException e) {
			logger.error("Problem while extracting login and logout history");
		}
		
		response.setObject(linLoutHisDtoList);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/employee/token/{tokenid}")
	public ResponseEntity<Response> findEmployeeByToken(@PathVariable("tokenid") String tokenid) {

		String emailId = jwtUtil.extractUsername(tokenid);
		Response response = new Response(906, "TOKEN_INVALID", "Invalid token");
		EmployeeDto empDto = null;
		
		try {
			empDto = empService.findEmployee(emailId);
		} catch (ResourceNotFoundException e) {
			response = new Response(906, "TOKEN_INVALID_FOR_USER", "Token not associated with any valid user");
		}

		if (empDto != null) {
			if (jwtUtil.validateToken(tokenid, empDto)) {
				response = new Response(200, "TOKEN_VALID", "Valid token");
				response.setObject(empDto);
			}
		}

		return ResponseEntity.ok(response);
	}

	@GetMapping("/employee/{empEmailId}")
	public ResponseEntity<Object> getEmployee(@PathVariable("empEmailId") String empEmailId) {
		logger.info("getUser method of registartion controller started");

		EmployeeDto empDto;
		try {
			empDto = empService.findEmployee(empEmailId);
			logger.info("Employee with Id: " + empEmailId + " extracted successfully");
		} catch (ResourceNotFoundException ex) {
			Response response = new Response(901, "RESOURCE_NOT_FOUND", ex.getMessage());
			return ResponseEntity.ok(response);
		}

		logger.info("getUser method of registartion controller ended");
		return ResponseEntity.ok(empDto);
	}
	
	@PostMapping("/employee")
	public ResponseEntity<Response> addEmployee(@RequestBody EmployeeDto empDto) throws SQLException {
		logger.info("addEmployee method of registartion controller started");
		
		Response response = empService.addEmployee(empDto);
		logger.info("Employee with Id: " + empDto.getEmpId() + " saved successfully");
		logger.info("addEmployee method of registartion controller ended");
		
		return ResponseEntity.ok(response);
	}

}
