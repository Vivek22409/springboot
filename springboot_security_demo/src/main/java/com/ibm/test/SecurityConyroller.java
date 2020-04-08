package com.ibm.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityConyroller {
	
	
	@GetMapping("/message")
	public String greetMessage() {
		return "Hello";
	}

}
