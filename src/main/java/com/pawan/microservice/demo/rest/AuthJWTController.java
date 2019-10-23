package com.pawan.microservice.demo.rest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pawan.microservice.demo.config.ApplicationContextHolder;
import com.pawan.microservice.demo.rest.dto.UserDTO;
import com.pawan.microservice.demo.security.CustomeUserdetails;
import com.pawan.microservice.demo.security.TokenProvider;
import com.pawan.microservice.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthJWTController {


	private final TokenProvider tokenProvider;
	
	private final UserService userService;
	

	
	public AuthJWTController(TokenProvider tokenProvider,UserService userService) {
		this.tokenProvider = tokenProvider;
		this.userService = userService;
	}
	
	
	@RequestMapping(path = "/login",method = RequestMethod.POST)
	public String login(@RequestBody UserDTO dto) {
		System.out.println("Login Request received.....");
		UsernamePasswordAuthenticationToken authenticationToken =
	            new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
		 Authentication authentication = ApplicationContextHolder.getContext().getBean(AuthenticationManager.class).authenticate(authenticationToken);
         SecurityContextHolder.getContext().setAuthentication(authentication);
         String jwt = tokenProvider.createToken(dto);
         
		return jwt;
		
	}
	
	@RequestMapping(path = "/account",method = RequestMethod.GET)
	public UserDTO account() {
		UserDTO dto=new UserDTO();
		CustomeUserdetails customeUserdetails= (CustomeUserdetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		dto.setUsername(customeUserdetails.getUsername());
		return dto;
		
	}
	
	@RequestMapping(path = "/account",method = RequestMethod.PUT)
	public void accountUpdate(@RequestBody UserDTO dto) {
		userService.updateUser(dto);
		
	}
	
}
