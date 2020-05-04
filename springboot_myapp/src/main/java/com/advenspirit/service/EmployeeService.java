package com.advenspirit.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.advenspirit.entity.Employee;
import com.advenspirit.entity.LoginLogoutHistory;
import com.advenspirit.exception.ResourceNotFoundException;
import com.advenspirit.model.EmployeeDto;
import com.advenspirit.model.LoginLogoutHistoryDto;
import com.advenspirit.model.LoginRequest;
import com.advenspirit.model.Response;
import com.advenspirit.repository.EmployeeRepository;
import com.advenspirit.repository.LoginAndLogoutRepository;

@Service
public class EmployeeService/* implements UserDetailsService */ {

	@Autowired
	private EmployeeRepository empRepo;

	@Autowired
	LoginAndLogoutRepository linLoutRepo;

	public EmployeeDto authenticateEmployee(LoginRequest loginRequest) {
		boolean authStatus = false;
		EmployeeDto empDto = null;

		try {
			empDto = this.findEmployee(loginRequest.getEmailId());
			if (loginRequest.getEmailId().equals(empDto.getEmailId())
					&& loginRequest.getPassword().equals(empDto.getPassword())) {
				authStatus = true;
			}
		} catch (ResourceNotFoundException ex) {
			ex.printStackTrace();
		}

		if (authStatus == true && empDto != null) {
			return empDto;
		}

		return null;
	}

	public void writeLoginAndLogoutDetails(EmployeeDto empDto, String location, String token) {

		LoginLogoutHistory linLoutHis = new LoginLogoutHistory();
		linLoutHis.setEmailId(empDto.getEmailId());
		linLoutHis.setEmpType(empDto.getEmpType());
		linLoutHis.setLocation(location);
		linLoutHis.setLoginTime();
		linLoutHis.setToken(token);
		linLoutRepo.save(linLoutHis);

	}

	public void recordLogOutHistory(String tokenId) throws ResourceNotFoundException {
		LoginLogoutHistory linLoutHis = linLoutRepo.findLoginLogoutHistoryBytoken(tokenId).orElseThrow(
				() -> new ResourceNotFoundException("LoginLogoutHistory not found for this id :" + tokenId));
		linLoutHis.setLogoutTime();
		linLoutRepo.save(linLoutHis);
	}	

	public EmployeeDto findEmployee(String empEmailId) throws ResourceNotFoundException {

		Employee emp = empRepo.findEmployeeByEmailId(empEmailId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :" + empEmailId));
		EmployeeDto empDto = new EmployeeDto(emp.getEmpId(), emp.getFirstName(), emp.getLastName(), emp.getEmailId(),
				emp.getEmpType(), emp.getRegTime());
		empDto.setPassword(emp.getPassword());
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

	public List<LoginLogoutHistoryDto> getLoginLogoutHistory(/* String emailId */) throws ResourceNotFoundException {
		
		final List<LoginLogoutHistoryDto> loginLogoutHistoryDtoList = new ArrayList<>();
		List<LoginLogoutHistory> loginLogoutHistoryList = linLoutRepo.findAll();

		for (LoginLogoutHistory linLoutHis : loginLogoutHistoryList) {
			if (!(linLoutHis.getEmpType().equalsIgnoreCase("admin"))) {
				Employee emp = empRepo.findEmployeeByEmailId(linLoutHis.getEmailId()).orElseThrow(
						() -> new ResourceNotFoundException("Employee not found for this id :" + linLoutHis.getId()));
				EmployeeDto empDTo = new EmployeeDto(emp.getFirstName(), emp.getLastName());
				LoginLogoutHistoryDto llh = new LoginLogoutHistoryDto(linLoutHis.getId(), linLoutHis.getEmailId(),
						linLoutHis.getLocation(), linLoutHis.getEmpType(), linLoutHis.getLoginTime(),
						linLoutHis.getLogoutTime(), empDTo);
				loginLogoutHistoryDtoList.add(llh);
			}
		}

		return loginLogoutHistoryDtoList;
	}

}
