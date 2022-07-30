package com.kamran.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kamran.blog.exceptions.ApiException;
import com.kamran.blog.payloads.JwtAuthRequest;
import com.kamran.blog.payloads.UserDto;
import com.kamran.blog.security.CustomUserDetailService;
import com.kamran.blog.security.JWTTokenHelper;
import com.kamran.blog.security.JwtAuthResponse;
import com.kamran.blog.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
				@RequestBody JwtAuthRequest request 
			){
		
		this.authenticate(request.getEmail(), request.getPassword());
		
		UserDetails UserDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
	
		String generatedToken = this.jwtTokenHelper.generateToken(UserDetails);
		
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setToken(generatedToken);

		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerNewUser(
				@RequestBody UserDto request 
			){
		
		UserDto newRegisteredUser = this.userService.registerNewUser(request);

		return new ResponseEntity<UserDto>(newRegisteredUser, HttpStatus.CREATED);
	}
	
	

	private void authenticate(String username, String password) {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		this.authenticationManager.authenticate(authenticationToken);
		
		try {
			
		} catch (DisabledException e) {
			throw new ApiException("USER IS DISABLED CURRENTLY");
		}
		
	}

}
