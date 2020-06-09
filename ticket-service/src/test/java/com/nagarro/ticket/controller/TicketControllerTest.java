package com.nagarro.ticket.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.nagarro.ticket.model.TicketDto;
import com.nagarro.ticket.service.TicketServiceImpl;
import com.nagarro.ticket.util.TicketServiceProps;

@SpringBootTest
@AutoConfigureMockMvc
// @WebMvcTest(value = TicketController.class, secure = false)
public class TicketControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private TicketServiceImpl ticketServiceImpl;

	private TicketDto ticketDto;
	private List<TicketDto> ticketDtoList = new ArrayList<>();

	@BeforeEach
	public void setUp() throws Exception {
		ticketDto = new TicketDto("sub", "desc", "two");
		ticketDto.setTicktId("one");
		TicketDto ticketDto1 = new TicketDto("sub", "desc", "two");
		ticketDto1.setTicktId("one");
		TicketDto ticketDto2 = new TicketDto("sub", "desc", "three");
		ticketDto2.setTicktId("two");
		ticketDtoList.add(ticketDto1);
		ticketDtoList.add(ticketDto2);
	}

	@Test // Unit test
	public void message() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/v1/message").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(equalTo("message")));
	}

	@Test // Unit test
	public void getTicketTest() throws Exception {
		Mockito.when(ticketServiceImpl.getTicket(Mockito.anyString())).thenReturn(ticketDto);
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.get("/api/v1/ticket/1", ticketDto).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		// System.out.println(result.getResponse().getContentAsString());
		String expected = "{ticktId:one,ticktSubjct:sub,ticktDesc:desc,empId:two}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test // Unit test
	public void getTicketsTest() throws Exception {
		Mockito.when(ticketServiceImpl.findTickets()).thenReturn(ticketDtoList);
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.get("/api/v1/tickets", ticketDto).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		String expected = "[{ticktId:one,ticktSubjct:sub,ticktDesc:desc,empId:two},{ticktId:two,ticktSubjct:sub,ticktDesc:desc,empId:three}]";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void saveTicketTest() throws Exception {
		ticketDto = new TicketDto("sub", "desc", "two");
		Mockito.when(ticketServiceImpl.saveTicket(Mockito.any(TicketDto.class)))
				.thenReturn(TicketServiceProps.SUCCESS_STATUS);
		String ticketDtoJSON = "{\r\n" + "	\r\n" + "    \"name\": \"name\",\r\n" + "    \"emailId\": \"emailId\",\r\n"
				+ "    \"password\": \"password\",\r\n" + "    \"department\":\"department\"\r\n" + "}";

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/ticket").accept(MediaType.APPLICATION_JSON)
				.content(ticketDtoJSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
		// Assertions.assertEquals("http://localhost/api/v1/ticket/1",
		// response.getHeader(HttpHeaders.LOCATION));

	}

	@Test // Unit test
	public void updateTicketTest() throws Exception {
		ticketDto = new TicketDto("sub", "desc", "two");
		String ticketDtoJSON = "{\r\n" + 
				"    \"ticktId\": \"8\",\r\n" + 
				"    \"ticktSubjct\": \"ticktSubjct\",\r\n" + 
				"    \"ticktDesc\": \"ticktDesc\",\r\n" + 
				"    \"empId\": \"3\"\r\n" + 
				"}";
		
		Mockito.when(ticketServiceImpl.updateTicket(Mockito.any(TicketDto.class))).thenReturn(TicketServiceProps.SUCCESS_STATUS);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/ticket").accept(MediaType.APPLICATION_JSON)
				.content(ticketDtoJSON).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc
				.perform(requestBuilder)
				.andReturn();
		//System.out.println(result.getResponse().getContentAsString());	
		Assertions.assertEquals(TicketServiceProps.SUCCESS_STATUS, result.getResponse().getContentAsString());
		
	}
	
	@Test // Unit test
	public void deleteTicketTest() throws Exception {
		ticketDto = new TicketDto("sub", "desc", "two");
		ticketDto.setTicktId("one");
		Mockito.when(ticketServiceImpl.deleteTicket(Mockito.anyString())).thenReturn(TicketServiceProps.SUCCESS_STATUS);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/ticket/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mvc
				.perform(requestBuilder)
				.andReturn();
		//System.out.println(result.getResponse().getContentAsString());	
		Assertions.assertEquals(TicketServiceProps.SUCCESS_STATUS, result.getResponse().getContentAsString());
		
	}
	

}