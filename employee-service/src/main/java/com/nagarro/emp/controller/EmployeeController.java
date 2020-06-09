package com.nagarro.emp.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.nagarro.emp.exception.EmployeeNotFoundException;
import com.nagarro.emp.model.EmployeeDto;
import com.nagarro.emp.model.LoginRequest;
import com.nagarro.emp.model.LoginResponse;
import com.nagarro.emp.model.Response;
import com.nagarro.emp.model.TicketDto;
import com.nagarro.emp.service.EmployeeServiceImpl;

import com.nagarro.emp.util.EmployeeServiceProps;
import com.nagarro.emp.util.JwtUtil;
import com.nagarro.emp.util.SessionContext;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	private final static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeServiceImpl empSrvce;

	@Autowired
	private RestTemplate rstTmplate;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private BCryptPasswordEncoder pwdHasher;

	@GetMapping("/message")
	public String message() {
		return "message";
	}

	@PostMapping("/login")
	public ResponseEntity<Response> authenticateAndCreateToken(@RequestBody LoginRequest loginRequest) {
		logger.info("entered in authenticateAndCreateToken method of Employee Controller");

		Response response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
				"User not authenticated", "empty");
		String encodedPwd = pwdHasher.encode(loginRequest.getPassword());
		loginRequest.setPassword(encodedPwd);
		EmployeeDto empDto = new EmployeeDto();
		try {
			empDto = empSrvce.authenticateEmployee(loginRequest);
			if (empDto != null) {
				response = new Response(EmployeeServiceProps.SUCCESS_CODE, EmployeeServiceProps.SUCCESS_STATUS,
						"User authenticated successfully", empDto.getEmailId());
				final String jwtToken = jwtUtil.generateToken(empDto);
				SessionContext.setSessionToken(jwtToken);
				SessionContext.setLogedInEmpDetails(empDto);
				Map<String, String> emailIdTokenMap = new HashMap<>();
				emailIdTokenMap.put(empDto.getEmailId(), jwtToken);
				SessionContext.setEmailIdTokenMap(emailIdTokenMap);
				final LoginResponse loginResponse = new LoginResponse(jwtToken, empDto);
				response.setData(loginResponse);
			}

		} catch (Exception ex) {
			response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
					"Exception occured during authentication", ex.getMessage());
			logger.info("exception occured in authenticateAndCreateToken method of Employee Controller");
		}
		logger.info("exited in authenticateAndCreateToken method of Employee Controller");
		return ResponseEntity.ok(response);

	}

	@GetMapping("/logout")
	public ResponseEntity<Response> employeeLogout() {
		logger.info("entered in employeeLogout method of Employee Controller");
		SessionContext.clear();
		Response response = new Response(EmployeeServiceProps.SUCCESS_CODE, EmployeeServiceProps.SUCCESS_STATUS,
				"User loged out successfully", " ");

		logger.info("exited in employeeLogout method of Employee Controller");

		return ResponseEntity.ok(response);
	}

	@PostMapping("/employee")
	public ResponseEntity<Response> registerEmployee(@RequestBody EmployeeDto empDto) {

		logger.info("entered in saveEmployee method of Employee Controller");

		String encodedPwd = pwdHasher.encode(empDto.getPassword());
		empDto.setPassword(encodedPwd);
		String status;
		Response response = new Response();
		try {
			status = empSrvce.saveEmployee(empDto);
			response = new Response(EmployeeServiceProps.SUCCESS_CODE, EmployeeServiceProps.SUCCESS_STATUS,
					"Employee record created successfully", status);
			logger.info("employee record created successfully: " + empDto.toString());
		} catch (Exception ex) {
			response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
					"Exception while creating record successfully", ex.getMessage());
			logger.info("exception in employee record creation " + empDto.toString());
		}

		logger.info("exit from saveEmployee method of Employee Controller");

		return ResponseEntity.ok(response);
	}

	@GetMapping("/employee/{empId}")
	public ResponseEntity<Response> getEmployee(@PathVariable("empId") String empId,
			@RequestParam("token") String token) throws NumberFormatException, EmployeeNotFoundException {

		logger.info("entered in getEmployee method of Employee Controller");

		Response response = new Response();
		try {
			String emailId = jwtUtil.extractUsername(token);
			Map<String, String> map = SessionContext.getEmailIdTokenMap();

			if (map.get(emailId) != null && map.get(emailId).equals(SessionContext.getSessionToken())
					&& jwtUtil.validateToken(token, SessionContext.getLogedInEmpDetails())) {

				EmployeeDto empDto = empSrvce.findEmployee(Integer.parseInt(empId));
				response = new Response(EmployeeServiceProps.SUCCESS_CODE, EmployeeServiceProps.SUCCESS_STATUS,
						"Employee record extracted successfully", empDto);
				logger.info("Employee record extracted successfully: " + empDto.toString());
			} else {
				response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
						"Token not valid loged in user", "EmpId: " + empId);
			}

		} catch (EmployeeNotFoundException ex) {
			logger.info("Employee not found with id: " + empId);
			response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
					"exception occured in getEmployee method of Employee Controller ", ex.getMessage());
		} catch (Exception ex) {
			logger.info("Exception in finding employee " + ex.getMessage());
			response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
					"exception occured in getEmployee method of Employee Controller ", ex.getMessage());
		}

		logger.info("exited in getEmployee method of Employee Controller");
		return ResponseEntity.ok(response);
	}

	@PostMapping("/employee/ticket")
	public ResponseEntity<Response> submitTicket(@RequestBody TicketDto ticktDto, @RequestParam("token") String token) {
		logger.info("entered in submitTicket method of Employee Controller");

		Response response = new Response();
		try {
			String emailId = jwtUtil.extractUsername(token);
			Map<String, String> map = SessionContext.getEmailIdTokenMap();

			if (map.get(emailId) != null && map.get(emailId).equals(SessionContext.getSessionToken())
					&& jwtUtil.validateToken(token, SessionContext.getLogedInEmpDetails())) {

				HttpEntity<TicketDto> entity = new HttpEntity<>(ticktDto, new HttpHeaders());
				String result = rstTmplate.postForObject(EmployeeServiceProps.TICKET_SERVICE_URL + "/ticket", entity,
						String.class);
				if (result.equalsIgnoreCase(EmployeeServiceProps.SUCCESS_STATUS)) {
					response = new Response(EmployeeServiceProps.SUCCESS_CODE, EmployeeServiceProps.SUCCESS_STATUS,
							"ticket raised successsfuly for employee with Id: " + ticktDto.getEmpId(), result);
					logger.info("Ticket submitted for employee successfully with empId: " + ticktDto.getEmpId());
				} else {
					response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
							"probelem in ticket submittimg: return status not success for emp with Id: "
									+ ticktDto.getEmpId(),
							ticktDto.toString());
					logger.info("probelem in ticket submittimg: return status not success for emp with Id:"
							+ ticktDto.toString());
				}
			} else {
				response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
						"Token not valid loged in user", "EmpId: " + ticktDto.getEmpId());
			}

		} catch (Exception ex) {
			response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
					"exception occured in submitTicket method of Employee Controller ", ex.getMessage());
			ex.printStackTrace();
			logger.info("exception occured in submitTicket method of Employee Controller: " + ex.getMessage());
		}

		logger.info("exited in submitTicket method of Employee Controller");
		return ResponseEntity.ok(response);
	}

	@PutMapping("/employee/ticket")
	public ResponseEntity<Response> updateEmployeeTicket(@RequestBody TicketDto ticktDto,
			@RequestParam("token") String token) {
		logger.info("entered in updateEmployeeTicket method of Employee Controller");

		Response response = new Response();
		String status = EmployeeServiceProps.FAILED_STATUS;
		try {

			String emailId = jwtUtil.extractUsername(token);
			Map<String, String> map = SessionContext.getEmailIdTokenMap();

			if (map.get(emailId) != null && map.get(emailId).equals(SessionContext.getSessionToken())
					&& jwtUtil.validateToken(token, SessionContext.getLogedInEmpDetails())) {

				HttpEntity<TicketDto> entity = new HttpEntity<>(ticktDto, new HttpHeaders());
				rstTmplate.put(EmployeeServiceProps.TICKET_SERVICE_URL + "/ticket", entity);
				status = EmployeeServiceProps.SUCCESS_STATUS;
				if (status.equalsIgnoreCase(EmployeeServiceProps.SUCCESS_STATUS)) {
					response = new Response(EmployeeServiceProps.SUCCESS_CODE, EmployeeServiceProps.SUCCESS_STATUS,
							"ticket updated successsfuly for employee with Id: " + ticktDto.getEmpId(), status);
					logger.info("Ticket updated for employee successfully with empId: " + ticktDto.getEmpId());
				} else {
					response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
							"probelem in ticket updation: return status not success for emp with Id: "
									+ ticktDto.getEmpId(),
							ticktDto.toString());
					logger.info("probelem in ticket updation: return status not success for emp with Id:"
							+ ticktDto.toString());
				}
			} else {
				response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
						"Token not valid loged in user", "EmpId: " + ticktDto.getEmpId());
			}
		} catch (Exception ex) {
			response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
					"exception occured in updateEmployeeTicket method of Employee Controller ", ex.getMessage());
			ex.printStackTrace();
			logger.info("exception occured in updateEmployeeTicket method of Employee Controller: " + ex.getMessage());
		}

		logger.info("exited in updateEmployeeTicket method of Employee Controller");
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/employee/ticket/{ticktId}")
	public ResponseEntity<Response> deleteEmployeeTicket(@PathVariable("ticktId") String ticktId,
			@RequestParam("token") String token) {
		logger.info("entered in deleteEmployeeTicket method of Employee Controller");

		Response response = new Response();
		String status = EmployeeServiceProps.FAILED_STATUS;
		try {
			String emailId = jwtUtil.extractUsername(token);
			Map<String, String> map = SessionContext.getEmailIdTokenMap();

			if (map.get(emailId) != null && map.get(emailId).equals(SessionContext.getSessionToken())
					&& jwtUtil.validateToken(token, SessionContext.getLogedInEmpDetails())) {
				rstTmplate.delete(EmployeeServiceProps.TICKET_SERVICE_URL + "/ticket/" + ticktId);
				status = EmployeeServiceProps.SUCCESS_STATUS;
				if (status.equalsIgnoreCase(EmployeeServiceProps.SUCCESS_STATUS)) {
					response = new Response(EmployeeServiceProps.SUCCESS_CODE, EmployeeServiceProps.SUCCESS_STATUS,
							"ticket deleted successsfuly for employee with with ticket Id: " + ticktId, status);
					logger.info("Ticket deleted for employee successfully with ticket Id: " + ticktId);
				} else {
					response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
							"probelem in ticket deletion: return status not success for emp with ticket Id: " + ticktId,
							ticktId);
					logger.info(
							"probelem in ticket deletion: return status not success for emp with ticket Id:" + ticktId);
				}
			} else {
				response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
						"Token not valid loged in user", "");
			}
		} catch (Exception ex) {
			response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
					"exception occured in deleteEmployeeTicket method of Employee Controller ", ex.getMessage());
			ex.printStackTrace();
			logger.info("exception occured in deleteEmployeeTicket method of Employee Controller: " + ex.getMessage());
		}

		logger.info("exited in deleteEmployeeTicket method of Employee Controller");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/employee/{empId}/tickets")
	@SuppressWarnings("unchecked")
	@Cacheable(value = "employeeTickets", key = "#empId")
	public ResponseEntity<Response> getEmployeeTickets(@PathVariable("empId") String empId,
			@RequestParam("token") String token) {
		logger.info("entered in getEmployeeTickets method of Employee Controller");

		// System.out.println("*****************************");

		Response response = new Response();
		try {

			String emailId = jwtUtil.extractUsername(token);
			Map<String, String> map = SessionContext.getEmailIdTokenMap();

			if (map.get(emailId) != null && map.get(emailId).equals(SessionContext.getSessionToken())
					&& jwtUtil.validateToken(token, SessionContext.getLogedInEmpDetails())) {

				URI url = URI.create(EmployeeServiceProps.TICKET_SERVICE_URL + "/tickets");
				List<Map<String, String>> ticktDtoList = rstTmplate.getForObject(url, ArrayList.class);

				List<TicketDto> resultTicktDtoList = new ArrayList<>();

				// System.out.println(resultTicktDtoList);
				if (!(ticktDtoList.isEmpty())) {
					for (Map<String, String> ticktDtoMap : ticktDtoList) {
						TicketDto ticktDto = new TicketDto();
						ticktDto.setTicktId(ticktDtoMap.get("ticktId"));
						ticktDto.setTicktSubjct(ticktDtoMap.get("ticktSubjct"));
						ticktDto.setTicktDesc(ticktDtoMap.get("ticktDesc"));
						ticktDto.setEmpId(ticktDtoMap.get("empId"));
						if (ticktDto.getEmpId().equals(empId)) {
							resultTicktDtoList.add(ticktDto);
						}
					}
					response = new Response(EmployeeServiceProps.SUCCESS_CODE, EmployeeServiceProps.SUCCESS_STATUS,
							"tickets extracted successsfuly for employee with Id: " + empId, resultTicktDtoList);
					logger.info("Ticket submitted for employee successfully with empId: " + empId);
				} else {
					response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
							"No tickets are found with empId: " + empId, resultTicktDtoList);
					logger.info("No tickets are found with empId:" + empId);
				}
			} else {
				response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
						"Token not valid loged in user", "");
			}
		} catch (Exception ex) {
			response = new Response(EmployeeServiceProps.ERROR_CODE, EmployeeServiceProps.FAILED_STATUS,
					"exception occured in getEmployeeTickets method of Employee Controller ", ex.getMessage());

			logger.info("exception occured in getEmployeeTickets method of Employee Controller: " + ex.getMessage());
		}

		logger.info("exited in getEmployeeTickets method of Employee Controller");
		return ResponseEntity.ok(response);
	}

	@CacheEvict(value = "employees", allEntries = true)
	@GetMapping("/clearEmployeesCache")
	public String flushEmployeesCache() {
		return EmployeeServiceProps.SUCCESS_STATUS;
	}

	@CacheEvict(value = "employeeTickets", allEntries = true)
	@GetMapping("/clearEmployeesTicketsCache")
	public String flushEmployeeTicketsCache() {
		URI url = URI.create(EmployeeServiceProps.TICKET_SERVICE_URL + "/clearTicketsCache");
		String status = rstTmplate.getForObject(url, String.class);
		return status;
	}

}
