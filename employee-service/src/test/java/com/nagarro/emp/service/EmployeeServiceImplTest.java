package com.nagarro.emp.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.nagarro.emp.dao.EmployeeRepository;
import com.nagarro.emp.entity.Employee;
import com.nagarro.emp.exception.EmployeeNotFoundException;
import com.nagarro.emp.model.EmployeeDto;
import com.nagarro.emp.model.LoginRequest;


@SpringBootTest
@SpringJUnitConfig(classes= {EmployeeServiceImpl.class})
public class EmployeeServiceImplTest {
	
	/*@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
  
        @Bean
        public EmployeeService employeeService() {
            return new EmployeeServiceImpl();
        }
    } */
	
	@Autowired
	private EmployeeServiceImpl employeeService;	
	
	@MockBean
	private EmployeeRepository empRepo;
	
	private EmployeeDto empDto;
	private Employee emp;
	private Optional<Employee> optEmp;
	private LoginRequest loginRequest;
	private LoginRequest loginRequestDiff;
	
	@BeforeEach
	public void setUp() {
		empDto = new EmployeeDto("100", "John", "John@gmail.com", "john", "IT");
		emp = new Employee("John", "john@gmail.com", "john", "IT");
		emp.setEmpId(Integer.parseInt(empDto.getEmpId()));
		optEmp = Optional.of(emp);
		loginRequest = new LoginRequest("john@gmail.com", "john");
		loginRequestDiff = new LoginRequest("test@gmail.com", "test");
		
	}	

	@Test
	public void saveEmployeeTest() throws Exception {		
		Mockito.when(empRepo.save(Mockito.any())).thenReturn(emp);
		String result = employeeService.saveEmployee(empDto);
		assertThat(result).isEqualTo("success");
		
	}
	
	@Test
	public void findEmployeeTest() throws Exception {
		Mockito.when(empRepo.findById(Mockito.any())).thenReturn(optEmp) ;
		EmployeeDto empDtoResult = employeeService.findEmployee(Integer.parseInt(empDto.getEmpId()));
		assertThat(empDtoResult.getName()).isEqualTo("John");
		
		
	}
	
	@Test
	public void authenticateEmployeeTest() throws Exception {
		Mockito.when(empRepo.findEmployeeByEmailId(loginRequest.getEmailId())).thenReturn(optEmp) ;
		EmployeeDto empDtoResult = employeeService.authenticateEmployee(loginRequest);
		assertThat(empDtoResult.getEmailId()).isEqualTo("john@gmail.com");		
		
	}
	
	@Test
	public void authenticateEmployeeSecondTest() throws Exception {
		Mockito.when(empRepo.findEmployeeByEmailId(loginRequest.getEmailId())).thenReturn(optEmp) ;
		Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
			employeeService.authenticateEmployee(loginRequestDiff);
		  });
	}
	
}
