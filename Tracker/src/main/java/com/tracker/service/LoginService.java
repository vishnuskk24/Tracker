package com.tracker.service;

import java.nio.file.AccessDeniedException;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tracker.dto.EmployeeDTO;
import com.tracker.dto.LoginDTO;
import com.tracker.exception.InfyTrackerException;

public interface LoginService {

    public String login(LoginDTO employeeDetails) throws InfyTrackerException, AccessDeniedException, Exception;
}
