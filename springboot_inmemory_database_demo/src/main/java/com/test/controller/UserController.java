package com.test.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.test.entity.User;
import com.test.repository.UserRepository;

@RestController
public class UserController {	
	
	@Autowired
	private UserRepository userRepo;
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@GetMapping("/message")
	public String getMessage() {
		return "Hello";
	}
	
	@GetMapping("/users")
	public List<User> getUsers() {
		
		log.info("Extracting users...");		
		List<User> users = userRepo.findAll();		
		return  users;		
	}

}
