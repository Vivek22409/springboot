package com.nagarro.ticket.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class) ;
	
	@ExceptionHandler(NumberFormatException.class)
	public void handleNumberFormatException(NumberFormatException ex) {
		logger.info(ex.getMessage());
	}
	
	@ExceptionHandler(TicketNotFoundException.class)
	public void handleNumberFormatException(TicketNotFoundException ex) {
		logger.info(ex.getMessage());
	}
}
