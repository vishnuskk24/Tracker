package com.tracker.service;

import java.util.List;

import com.tracker.dto.ActivityDTO;
import com.tracker.dto.EmployeeDTO;
import com.tracker.entity.ActivityStatus;
import com.tracker.exception.InfyTrackerException;

public interface LeadService {

    public String removeActivity(Integer activityId, Integer empId) throws InfyTrackerException;
    public String reassignTask(Integer activityId,Integer newEmpId) throws InfyTrackerException;
    public List<EmployeeDTO> getUserActivityByUserIdAndStatus(Integer empId, ActivityStatus activityStatus)throws InfyTrackerException;
    public String addActivity(Integer employeeId, ActivityDTO activityDTO) throws InfyTrackerException;
}