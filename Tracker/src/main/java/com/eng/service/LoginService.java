package com.eng.service;

import java.nio.file.AccessDeniedException;

import org.springframework.security.core.userdetails.UserDetailsService;

//import org.springframework.security.core.userdetails.UserDetailsService;

import com.eng.dto.EmployeeDTO;
import com.eng.exception.InfyTrackerException;

public interface LoginService {

    public String login(EmployeeDTO employeeDetails) throws InfyTrackerException, AccessDeniedException, Exception;
}
