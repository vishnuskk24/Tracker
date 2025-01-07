package com.tracker.service;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracker.dao.LoginRepository;
import com.tracker.dto.EmployeeDTO;
import com.tracker.entity.Employee;
import com.tracker.jwtvalidation.JwtService;
@Service
@Transactional
public class LoginServiceImpl implements LoginService{

		@Autowired
		AuthenticationManager authenticationManager;
		@Autowired
		JwtService jwtService;
		@Autowired
		LoginRepository loginRepository;
	@Override
	public String login(EmployeeDTO employeeDetails) throws Exception {
										// asking the authmanager to validate the username and password we are not goin to use equals
//		   authenication manager then he will as authenicationprovider  aasked USerDetail Service to get the actual data and check both value by DAOAuthenication manager using bcrypt
		
		 
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(employeeDetails.getUsername(), employeeDetails.getPassword()));
		String token ="";
		if(authentication.isAuthenticated()) {
			// valid  so genrate token
			Optional<Employee>  optional= loginRepository.findByUsername(employeeDetails.getUsername());
		Employee emp =	optional.orElseThrow(()-> new AccessDeniedException("Access_Denied"));
		
			token =jwtService.generateToken(new EmployeeDTO(emp));
//		token = jwtService.generateToken(emp.getUsername());
			
		}
		else {
//			send bad response
			throw new AccessDeniedException("Access_Denied");
		}
		return token;
		
	}

}
