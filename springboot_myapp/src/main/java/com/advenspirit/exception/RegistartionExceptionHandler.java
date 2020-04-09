package com.advenspirit.exception;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RegistartionExceptionHandler {	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public String handleException(ResourceNotFoundException ex){		
		return ex.getMessage();
	}
	
	@ExceptionHandler(SQLException.class)
	public String handleException(SQLException ex){		
		return ex.getMessage();
	}

}
