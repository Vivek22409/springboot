package com.advenspirit.service;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.advenspirit.entity.User;
import com.advenspirit.exception.ResourceNotFoundException;
import com.advenspirit.model.Response;
import com.advenspirit.model.UserDto;
import com.advenspirit.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	public UserDto findUser(Long userId) throws ResourceNotFoundException {
		
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :" + userId));
		UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmailId());
		return userDto;
	}

	public Response addUser(User user) throws SQLException {
		
		Response repsonse = new Response(900, "USER_NOT_SAVED", "User not saved");
		userRepo.save(user);
		repsonse.setCode(200);
		repsonse.setMessage("USER_SAVED");
		repsonse.setDescription("User saved");

		return repsonse;
	}

}
