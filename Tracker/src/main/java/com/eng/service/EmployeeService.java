package com.eng.service;

import java.util.List;

import com.eng.dto.ActivityDTO;
import com.eng.dto.EmployeeDTO;
import com.eng.entity.ActivityStatus;
import com.eng.exception.InfyTrackerException;

public interface EmployeeService {
    
    public EmployeeDTO getEmployee(Integer empId) throws InfyTrackerException;

    public String addEmployee(EmployeeDTO employee) throws InfyTrackerException;
    public String updateEmployee(EmployeeDTO employee) throws InfyTrackerException;

    public String deactivateEmployee(EmployeeDTO employee) throws InfyTrackerException;

    public List<EmployeeDTO> getAllEmployee() throws InfyTrackerException;

    

    

}