package com.nagarro.emp.controller;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.nagarro.emp.model.EmployeeDto;
import com.nagarro.emp.service.EmployeeServiceImpl;
import com.nagarro.emp.util.EmployeeServiceProps;
import com.nagarro.emp.util.JwtUtil;



@SpringBootTest
@AutoConfigureMockMvc
//@SpringJUnitConfig(classes= {EmployeeController.class, JwtUtil.class,RestTemplate.class,BCryptPasswordEncoder.class})
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private EmployeeServiceImpl employeeServiceImpl;
	
	@MockBean
	private RestTemplate rstTmplate;
	
	private EmployeeDto empDto;
	
	
	@BeforeEach
	public void setUp() throws Exception {
		empDto = new EmployeeDto("name", "emailId", "password","department");		
	}

	

	@Test  //Unit test
	public void message() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/v1/message").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("message")));
	}
	
	@Test
	public void registerEmployeeTest() throws Exception {		
		Mockito.when(employeeServiceImpl.saveEmployee(Mockito.any(EmployeeDto.class)))
				.thenReturn(EmployeeServiceProps.SUCCESS_STATUS);
		String employeeDtoJSON = "{\r\n" + 
				"    \"name\": \"name\",\r\n" + 
				"    \"emailId\": \"emailId\",\r\n" + 
				"    \"password\": \"password\",\r\n" + 
				"    \"department\":\"department\"\r\n" + 
				"}";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/employee").accept(MediaType.APPLICATION_JSON)
				.content(employeeDtoJSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());	

	}
	
	@Test // Unit test
	public void getEmployeeTest() throws Exception {
		empDto.setEmpId("one");		
		Mockito.when(employeeServiceImpl.findEmployee(Mockito.any(Integer.class))).thenReturn(empDto);
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.get("/api/v1/employee/5", empDto).accept(MediaType.APPLICATION_JSON))
				.andReturn();	
		System.out.println("&&&&"+result.getResponse().getContentAsString());
		String expected = "{\"code\":\"900\",\"status\":\"Success\",\"msg\":\"Employee record extracted successfully\",\"data\":{\"empId\":\"one\",\"name\":\"name\",\"emailId\":\"emailId\",\"password\":\"password\",\"department\":\"department\"}}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test // Unit test
	public void getEmployeeTicketsTest() throws Exception {
		
		List<Map<String, String>> ticktDtoList = new ArrayList<>();
		Map<String, String> map1 = new HashMap<>();
		map1.put("ticktId", "8");
		map1.put("ticktSubjct", "ticktSubjct");
		map1.put("ticktDesc", "ticktDesc");
		map1.put("empId", "3");
		Map<String, String> map2 = new HashMap<>();
		map2.put("ticktId", "9");
		map2.put("ticktSubjct", "ticktSubjct");
		map2.put("ticktDesc", "ticktDesc");
		map2.put("empId", "4");
		ticktDtoList.add(map1);
		ticktDtoList.add(map2);
		 
		//Mockito.when(rstTmplate.getForObject(Mockito.any(String.class), ArrayList.class)).thenReturn((ArrayList) ticktDtoList);
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.get ("/api/v1/employee/1/tickets").accept(MediaType.APPLICATION_JSON))
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = "{\"code\":\"700\",\"status\":\"Fail\",\"msg\":\"exception occured in getEmployeeTickets method of Employee Controller \",\"data\":null}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

}