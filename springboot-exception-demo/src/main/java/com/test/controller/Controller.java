package com.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@GetMapping("/message")
	public String getMessage() throws Exception {
		
		boolean b=true;
		if(b)
		throw new Exception("Custom Exception");
		
		return "Hello";
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception ex){
		
		return new ResponseEntity<>(ex,HttpStatus.EXPECTATION_FAILED);
	}

}
