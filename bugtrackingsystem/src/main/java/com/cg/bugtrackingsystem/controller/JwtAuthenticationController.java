package com.cg.bugtrackingsystem.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cg.bugtrackingsystem.config.JwtTokenUtil;
import com.cg.bugtrackingsystem.dto.SystemUserDetails;
import com.cg.bugtrackingsystem.service.JwtUserDetailsService;
import com.cg.onlinewalletwithspringbootrest.model.JwtRequest;
import com.cg.onlinewalletwithspringbootrest.model.JwtResponse;
import com.cg.onlinewalletwithspringbootrest.model.UserDto;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JwtAuthenticationController {
	
	@Autowired
	private SystemUserDetails details;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		
		System.out.println(authenticationRequest.getUsername());

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		
		
        
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		System.out.println(userDetails.getPassword());
		final String token = jwtTokenUtil.generateToken(userDetails);
		System.out.println("done here");

		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			System.out.println("inside authenticate");
			UsernamePasswordAuthenticationToken tok=  new UsernamePasswordAuthenticationToken(username, password,new ArrayList<>());

			authenticationManager.authenticate(tok);
			System.out.println("done");
		} catch (DisabledException e) {
			System.out.println("disabled");
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			System.out.println("bad");
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
