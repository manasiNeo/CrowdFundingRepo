package com.example.learnSecurity.service.implementation;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.learnSecurity.Repository.UserRepository;
import com.example.learnSecurity.config.JwtProvider;
import com.example.learnSecurity.exception.UserException;
import com.example.learnSecurity.model.User;
import com.example.learnSecurity.service.interfaces.UserService;


@Service
public class UserServiceImplementation implements UserService{
    
    private UserRepository userRepository;
	private JwtProvider jwtProvider;
	private PasswordEncoder passwordEncoder;
	
	public UserServiceImplementation(
			UserRepository userRepository,
			JwtProvider jwtProvider,
			PasswordEncoder passwordEncoder) {
		
		this.userRepository=userRepository;
		this.jwtProvider=jwtProvider;
		this.passwordEncoder=passwordEncoder;
		
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email=jwtProvider.getEmailFromJwtToken(jwt);
		
		
		User user = userRepository.findByEmail(email);
		
		if(user==null) {
			throw new UserException("user not exist with email "+email);
		}
		return user;
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
	public User findUserByEmail(String username) throws UserException {
		User user=userRepository.findByEmail(username);
		if(user!=null) {
			return user;
		}
		throw new UserException("user not exist with username "+username);
	}
}
