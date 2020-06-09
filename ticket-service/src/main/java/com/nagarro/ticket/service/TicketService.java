package com.nagarro.ticket.service;

import java.util.List;

import com.nagarro.ticket.exception.TicketNotFoundException;
import com.nagarro.ticket.model.TicketDto;

public interface TicketService {

	public String saveTicket(TicketDto ticktDto);

	public List<TicketDto> findTickets() throws TicketNotFoundException;

	public String updateTicket(TicketDto ticktId) throws TicketNotFoundException;

	public TicketDto getTicket(String ticktId) throws TicketNotFoundException;

	public String deleteTicket(String ticktId);

}
