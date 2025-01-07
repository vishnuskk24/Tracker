package com.tracker.service;

import java.util.List;

import com.tracker.dto.ActivityDTO;
import com.tracker.dto.EmployeeDTO;
import com.tracker.entity.ActivityStatus;
import com.tracker.exception.InfyTrackerException;

public interface EmployeeService {
    
    public EmployeeDTO getEmployee(Integer empId) throws InfyTrackerException;

    public String addEmployee(EmployeeDTO employee) throws InfyTrackerException;
    public String updateEmployee(EmployeeDTO employee) throws InfyTrackerException;

    public String deactivateEmployee(EmployeeDTO employee) throws InfyTrackerException;

    public List<EmployeeDTO> getAllEmployee() throws InfyTrackerException;

    

    

}