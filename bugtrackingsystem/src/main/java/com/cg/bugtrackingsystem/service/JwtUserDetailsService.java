package com.cg.bugtrackingsystem.service;


import com.cg.bugtrackingsystem.dto.Employee;
import com.cg.bugtrackingsystem.dto.Manager;
import com.cg.bugtrackingsystem.dto.SystemUserDetails;
import com.cg.bugtrackingsystem.exception.BtsException;
import com.cg.bugtrackingsystem.model.UserDto;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	EmployeeService service;
	@Autowired
	private SystemUserDetails userDetails;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
		
		    Optional<Employee> user;
			System.out.println(loginName);
			user = Optional.of(service.getUser(loginName));
			System.out.println(user.get().getPhoneNumber());
			user.orElseThrow(() -> new UsernameNotFoundException("Not Found: "+loginName));
			return user.map(SystemUserDetails::new).get();

	}

}
