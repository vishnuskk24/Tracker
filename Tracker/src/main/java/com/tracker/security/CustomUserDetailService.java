package com.tracker.security;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tracker.dao.LoginRepository;
import com.tracker.dto.EmployeeDTO;
import com.tracker.entity.Employee;
import com.tracker.entity.Status;
import com.tracker.service.LoginService;

@Service
public class CustomUserDetailService implements UserDetailsService{
	
	@Autowired
	LoginRepository loginRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<Employee> optional = loginRepository.findByUsername(username);
	    Employee employeeDetails = optional.orElseThrow(()->new UsernameNotFoundException("Service.Invalid_Data")); // validating username
	    if(employeeDetails.getStatus().equals(Status.Deactive)) new AccessDeniedException("Access_Denied"); // validation status
	    
	    EmployeeDTO eDTO =  new EmployeeDTO(employeeDetails);
//	    
	    eDTO.setPassword(employeeDetails.getPassword());
	    UserDetails userDetails =  new CustomUserDetails(eDTO);
//	    userDetails
		
		return userDetails;
	}

}
