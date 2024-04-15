package com.example.learnSecurity.auth.response;

import com.example.learnSecurity.domain.Role;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String message;
	private String jwt;
	private Role role;
}
