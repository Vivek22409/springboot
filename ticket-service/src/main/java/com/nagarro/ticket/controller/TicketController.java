package com.nagarro.ticket.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nagarro.ticket.exception.TicketNotFoundException;
import com.nagarro.ticket.model.TicketDto;
import com.nagarro.ticket.service.TicketServiceImpl;
import com.nagarro.ticket.util.TicketServiceProps;

@RestController
@RequestMapping("/api/v1")
public class TicketController {

	private final static Logger logger = LoggerFactory.getLogger(TicketController.class);

	@Autowired
	private TicketServiceImpl ticktSrvce;

	@GetMapping("/message")
	public String message() {
		return "message";
	}

	@PostMapping(path="/ticket")
	public String saveTicket(@RequestBody TicketDto ticktDto) {

		logger.info("entered in saveTicket method of Ticket Controller");

		String status = ticktSrvce.saveTicket(ticktDto);		
		
		logger.info("ticket record created successfully: "+ ticktDto.toString());
		logger.info("exit from saveTicket method of Ticket Controller");

		return status;
	}
	
	@PutMapping(path="/ticket")
	public String updateTicket(@RequestBody TicketDto ticktDto) {

		logger.info("entered in updateTicket method of ticket Controller");
		
		String status = "";
		try {
			status = ticktSrvce.updateTicket(ticktDto);
		} catch (Exception ex) {	
			logger.info("Exception occured in updateTicket method of ticket Controller with message: "+ex.getMessage());
		}			
		logger.info("ticket record created for employee successfully: "+ ticktDto.toString());
		logger.info("exit from updateTicket method of Ticket Controller");

		return status;
	}
	
	@GetMapping(path="/ticket/{ticktId}")
	public TicketDto getTicket(@PathVariable("ticktId") String ticktId) {

		logger.info("entered in getTicket method of Ticket Controller");

		TicketDto ticktDto = new TicketDto() ;
		try {
			ticktDto = ticktSrvce.getTicket(ticktId);
		} catch (Exception e) {			
			logger.info("exception occured while extracting ticket with ticktId: "+ticktId);
		}
		
		logger.info("ticket extracted successfully with record: "+ ticktDto.toString());
		logger.info("exit from getTicket method of ticket Controller");

		return ticktDto;
	}
	
	@DeleteMapping(path="/ticket/{ticktId}")
	public String deleteTicket(@PathVariable("ticktId") String ticktId) {

		logger.info("entered in deleteTicket method of Ticket Controller");
		
		String status = "";
		try {
			status = ticktSrvce.deleteTicket(ticktId);
		} catch (Exception e) {			
			logger.info("exception occured while deleting ticket with ticktId: "+ticktId);
		}
		
		logger.info("ticket deleted successfully with ticktId: "+ ticktId);
		logger.info("exit from deleteTicket method of ticket Controller");

		return status;
	}


	@GetMapping("/tickets")
	public List<TicketDto> getTickets()	throws NumberFormatException, TicketNotFoundException {

		logger.info("entered in getTickets method of ticket controller");
		
		List<TicketDto> ticktDtoList = new ArrayList<>();
		try {

			ticktDtoList = ticktSrvce.findTickets();
			if(ticktDtoList.isEmpty())
				throw new TicketNotFoundException("Ticket records not found");
			
			logger.info("tickets extracted successfully: " + ticktDtoList);

		} catch (TicketNotFoundException ex) {
			logger.info("TicketNotFoundException with message:"+ex.getMessage());			
		} catch (Exception ex) {
			logger.info("Exception with message " + ex.getMessage());			
		}

		logger.info("exited in getTickets method of ticket Controller");

		return ticktDtoList;
	}
	
	@CacheEvict(value="tickets", allEntries=true)
	@GetMapping("/clearTicketsCache")
	public String flushTicketsCache() {
		return TicketServiceProps.SUCCESS_STATUS;
	}

}
