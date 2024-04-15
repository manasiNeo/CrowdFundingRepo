package com.example.learnSecurity.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learnSecurity.Repository.UserRepository;
import com.example.learnSecurity.auth.request.LoginRequest;
import com.example.learnSecurity.auth.response.AuthenticationResponse;
import com.example.learnSecurity.config.JwtProvider;
import com.example.learnSecurity.domain.Role;
import com.example.learnSecurity.exception.UserException;
import com.example.learnSecurity.model.User;
import com.example.learnSecurity.service.implementation.DonorUserServiceImplementation;
import com.example.learnSecurity.service.interfaces.UserService;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    
    private  UserRepository userRepository;
	private  PasswordEncoder passwordEncoder;
	private  JwtProvider jwtProvider;
	private  DonorUserServiceImplementation donorUserDetails;
	
	
    // private PasswordResetTokenService passwordResetTokenService;

    private UserService userService;

	// Constructor for dependency injection
	public AuthenticationController(UserRepository userRepository, 
			PasswordEncoder passwordEncoder, 
			JwtProvider jwtProvider,
			DonorUserServiceImplementation donorUserDetails,
			// PasswordResetTokenService passwordResetTokenService,
			UserService userService
			) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
		this.donorUserDetails = donorUserDetails;
		
		this.userService=userService;

	}

	 // POST endpoint for user signup
	@PostMapping("/signup")
	public ResponseEntity<AuthenticationResponse> createUserHandler(@Validated @RequestBody User user) throws UserException {

		// Extract user details from the request
		String email = user.getEmail();
		String password = user.getPassword();
		String firstname = user.getFirstname();
        String lastname = user.getLastname();
		Role role=user.getRole();

		// Check if the email is already registered
		User isEmailExist = userRepository.findByEmail(email);

		if (isEmailExist!=null) {

			throw new UserException("Email Is Already Used With Another Account");
		}

		// Create new user
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setFirstname(firstname);
        createdUser.setLastname(lastname);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setRole(role);

		User savedUser = userRepository.save(createdUser);
		

// Authenticate the user after successful signup
		List<GrantedAuthority> authorities=new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority(role.toString()));


		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password,authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		// Prepare the response
		AuthenticationResponse authResponse = new AuthenticationResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Register Success");
		authResponse.setRole(savedUser.getRole());

		return new ResponseEntity<>(authResponse, HttpStatus.OK);

	}

	// POST endpoint for user signin
	@PostMapping("/signin")
	public ResponseEntity<AuthenticationResponse> signin(@RequestBody LoginRequest loginRequest) {

		// Extract username and password from the login request
		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();

		System.out.println(username + " ----- " + password);

		// Authenticate the user
		Authentication authentication = authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtProvider.generateToken(authentication);

		// Prepare the response
		AuthenticationResponse authResponse = new AuthenticationResponse();

		authResponse.setMessage("Login Success");
		authResponse.setJwt(token);
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();


		String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();


		authResponse.setRole(Role.valueOf(roleName));

		return new ResponseEntity<AuthenticationResponse>(authResponse, HttpStatus.OK);
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = donorUserDetails.loadUserByUsername(username);

		System.out.println("sign in userDetails - " + userDetails);

		if (userDetails == null) {
			System.out.println("sign in userDetails - null " + userDetails);
			throw new BadCredentialsException("Invalid username or password");
		}
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			System.out.println("sign in userDetails - password not match " + userDetails);
			throw new BadCredentialsException("Invalid username or password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}	    
}
