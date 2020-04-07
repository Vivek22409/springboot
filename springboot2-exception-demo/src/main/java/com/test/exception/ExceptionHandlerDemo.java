package com.test.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlerDemo {
	
	@ExceptionHandler(Exception.class)
	public Exception handleException(Exception ex){		
		return ex;
	}
}
