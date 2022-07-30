package com.kamran.blog.services;

import java.util.List;

//import com.kamran.blog.entities.User;
import com.kamran.blog.payloads.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
	
	UserDto registerNewUser(UserDto user);

}
