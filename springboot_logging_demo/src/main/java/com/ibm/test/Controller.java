package com.ibm.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Controller.class);
	
	@GetMapping("/message")
	public String greetMessage() {
		logger.info("Logger working fine...");
		return "Hello World";
	}

}
