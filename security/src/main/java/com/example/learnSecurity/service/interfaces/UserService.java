package com.example.learnSecurity.service.interfaces;

import java.util.List;

import com.example.learnSecurity.exception.UserException;
import com.example.learnSecurity.model.User;

public interface UserService {

    public User findUserProfileByJwt(String jwt) throws UserException;
	
	public User findUserByEmail(String email) throws UserException;

	public List<User> findAllUsers();

}
