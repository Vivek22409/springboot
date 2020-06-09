package com.nagarro.ticket.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.nagarro.ticket.dao.TicketRepository;
import com.nagarro.ticket.entity.Ticket;
import com.nagarro.ticket.exception.TicketNotFoundException;
import com.nagarro.ticket.model.TicketDto;
import com.nagarro.ticket.util.TicketServiceProps;

@Service
public class TicketServiceImpl implements TicketService {

	private final static Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);

	@Autowired
	private TicketRepository ticktRepo;

	@Override
	public String saveTicket(TicketDto ticktDto) {
		logger.info("entered in saveTicket method of ticketServiceImpl service");

		String status = TicketServiceProps.FAILED_STATUS;
		Ticket tickt = new Ticket(ticktDto.getTicktSubjct(), ticktDto.getTicktDesc(), ticktDto.getEmpId());
		tickt = ticktRepo.save(tickt);
		status = TicketServiceProps.SUCCESS_STATUS;
		logger.info("ticket saved successfully with id: " + tickt.getTicktId());
		logger.info("exited in saveTicket method of ticketServiceImpl service");

		return status;
	}

	@Override
	@CachePut(value="tickets",key="#ticktDto.getTicktId()")
	public String updateTicket(TicketDto ticktDto) throws TicketNotFoundException {
		logger.info("entered in updateTicket method of ticketServiceImpl service");

		String status = TicketServiceProps.FAILED_STATUS;
		Ticket tickttemp = ticktRepo.findById(Integer.valueOf(ticktDto.getTicktId()))
				.orElseThrow((() -> new TicketNotFoundException("Ticket not found")));
		tickttemp.setEmpId(ticktDto.getEmpId());
		tickttemp.setTicktId(Integer.parseInt(ticktDto.getTicktId()));
		tickttemp.setTicktSubjct(ticktDto.getTicktSubjct());
		tickttemp.setTicktDesc(ticktDto.getTicktDesc());
		//tickttemp = new Ticket(ticktDto.getTicktSubjct(), ticktDto.getTicktDesc(), ticktDto.getEmpId());
		ticktRepo.save(tickttemp);
		status = TicketServiceProps.SUCCESS_STATUS;

		logger.info("ticket saved successfully with id: " + ticktDto.getTicktId());
		logger.info("exited in updateTicket method of ticketServiceImpl service");

		return status;
	}

	@Override
	@Cacheable(value="tickets")

	public List<TicketDto> findTickets() throws TicketNotFoundException {
		logger.info("entered in findTickets method of ticketServiceImpl service");

		List<Ticket> ticktList = ticktRepo.findAll();
		List<TicketDto> ticktDtoList = new ArrayList<>();

		for (Ticket ticket : ticktList) {

			TicketDto ticktDto = new TicketDto(ticket.getTicktSubjct(), ticket.getTicktDesc(), ticket.getEmpId());
			ticktDto.setTicktId(String.valueOf(ticket.getTicktId()));
			ticktDtoList.add(ticktDto);

		}

		logger.info("exited from findTickets method of ticketServiceImpl service");
		return ticktDtoList;
	}

	@Override
	@Cacheable(value="tickets",key="#ticktId",condition="#ticktId>0")
	public TicketDto getTicket(String ticktId) throws NumberFormatException, TicketNotFoundException {
		logger.info("entered in getTicket method of ticketServiceImpl service");

		Ticket tickt = ticktRepo.findById(Integer.parseInt(ticktId))
				.orElseThrow((() -> new TicketNotFoundException("Ticket not found")));
		TicketDto ticktDto = new TicketDto(tickt.getTicktSubjct(), tickt.getTicktDesc(), tickt.getEmpId());
		ticktDto.setTicktId(String.valueOf(tickt.getTicktId()));

		logger.info("exited in getTicket method of ticketServiceImpl service");

		return ticktDto;

	}

	@Override
	public String deleteTicket(String ticktId) {
		logger.info("entered in getTicket method of ticketServiceImpl service");

		String status = TicketServiceProps.FAILED_STATUS;
		ticktRepo.deleteById(Integer.parseInt(ticktId));
		status = TicketServiceProps.SUCCESS_STATUS;
		
		logger.info("exited in getTicket method of ticketServiceImpl service");
		
		return status;
	}

}
