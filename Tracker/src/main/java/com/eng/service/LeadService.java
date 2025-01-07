package com.eng.service;

import java.util.List;

import com.eng.dto.ActivityDTO;
import com.eng.dto.EmployeeDTO;
import com.eng.entity.ActivityStatus;
import com.eng.exception.InfyTrackerException;

public interface LeadService {

    public String removeActivity(Integer activityId, Integer empId) throws InfyTrackerException;
    public String reassignTask(Integer activityId,Integer newEmpId) throws InfyTrackerException;
    public List<EmployeeDTO> getUserActivityByUserIdAndStatus(Integer empId, ActivityStatus activityStatus)throws InfyTrackerException;
    public String addActivity(Integer employeeId, ActivityDTO activityDTO) throws InfyTrackerException;
}