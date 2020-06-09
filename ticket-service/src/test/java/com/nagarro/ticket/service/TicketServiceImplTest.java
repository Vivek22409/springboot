package com.nagarro.ticket.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import com.nagarro.ticket.dao.TicketRepository;
import com.nagarro.ticket.entity.Ticket;
import com.nagarro.ticket.exception.TicketNotFoundException;
import com.nagarro.ticket.model.TicketDto;


@SpringBootTest
@SpringJUnitConfig(classes= {TicketServiceImpl.class})
public class TicketServiceImplTest {
	
	@Autowired
	private TicketServiceImpl ticketService;	
	
	@MockBean
	private TicketRepository ticketRepo;
	
	private TicketDto ticktDto;
	private Ticket tickt;
	private Optional<Ticket> optTickt;
	List<TicketDto> ticktDtoList = new ArrayList<>();
	List<Ticket> ticktList = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		ticktDto = new TicketDto("ticktSubjct", "ticktDesc", "100");
		ticktDto.setTicktId("1000");
		tickt = new Ticket ("ticktSubjct", "ticktDesc", "100");
		tickt.setTicktId(Integer.parseInt("1000"));
		optTickt = Optional.of(tickt);		
		ticktDtoList.add(ticktDto);
		ticktList.add(tickt);
	}
	
	@Test
	public void saveTicketTest() {
		Mockito.when(ticketRepo.save(Mockito.any())).thenReturn(tickt);
		String result = ticketService.saveTicket(ticktDto);
		assertThat(result).isEqualTo("Success");
	}
	
	@Test
	public void updateTicketTest() throws TicketNotFoundException {
		Mockito.when(ticketRepo.findById(Mockito.any())).thenReturn(optTickt);
		String result = ticketService.updateTicket(ticktDto);
		assertThat(result).isEqualTo("Success");
	}
	
	@Test
	public void findTicketsTest() throws TicketNotFoundException {
		Mockito.when(ticketRepo.findAll()).thenReturn(ticktList);
		List<TicketDto> ticktDtoList= ticketService.findTickets();
		Assertions.assertEquals(1, ticktDtoList.size(), "list have one element");
	}
	
	@Test
	public void getTicketTest() throws TicketNotFoundException {
		Mockito.when(ticketRepo.findById(Mockito.any())).thenReturn(optTickt);
		TicketDto ticktDtoResult= ticketService.getTicket(ticktDto.getTicktId());
		Assertions.assertEquals("1000", ticktDtoResult.getTicktId(), "Ticket id matched");
	}
	
	@Test
	public void delteTicketTest() throws TicketNotFoundException {
		ticketRepo.deleteById(Mockito.any());
		String result= ticketService.deleteTicket(ticktDto.getTicktId());
		Assertions.assertEquals("Success", result, "Expected ticket deleted");
	}

}
