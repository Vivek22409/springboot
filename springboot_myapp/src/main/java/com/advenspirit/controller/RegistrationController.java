package com.advenspirit.controller;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.advenspirit.entity.User;
import com.advenspirit.exception.ResourceNotFoundException;
import com.advenspirit.model.Response;
import com.advenspirit.model.UserDto;
import com.advenspirit.service.UserService;

@RestController
@RequestMapping("/api")
public class RegistrationController {

	@Autowired
	private UserService userService;

	@GetMapping("/message")
	public String greetMessage() {
		return "hello";
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<Object> getUser(@PathVariable("id") long id){
		UserDto userDetails;
		try {
			userDetails = userService.findUser(id);
		} catch (ResourceNotFoundException e) {
			Response response = new Response(901,"RESOURCE_NOT_FOUND","Resource not found");
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.ok(userDetails);
	}

	@PostMapping("/user")
	public ResponseEntity<Response> addUser(@RequestBody User user) throws SQLException{
		Response response = userService.addUser(user);
		return ResponseEntity.ok(response); 

	}

}
