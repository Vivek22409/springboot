package com.advenspirit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.advenspirit.entity.User;
import com.advenspirit.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class RegistrationController {

	@Autowired
	private UserRepository userRepo;

	@GetMapping("/message")
	public String getMessage() {
		return "hello";
	}

	@GetMapping("/getUser")
	public User getUser() {		
		return null;
	}

	@PostMapping("/user")
	public String addUser(@RequestBody User user){
		userRepo.save(user);
		return "success"; 

	}

}
