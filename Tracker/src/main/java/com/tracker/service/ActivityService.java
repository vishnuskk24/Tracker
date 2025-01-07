package com.tracker.service;

import java.util.List;

import com.tracker.dto.ActivityDTO;
import com.tracker.dto.EmployeeDTO;
import com.tracker.entity.ActivityStatus;
import com.tracker.exception.InfyTrackerException;

public interface ActivityService {

    public String addActivity(Integer employeeId,ActivityDTO activityDTO) throws InfyTrackerException;

    public ActivityDTO getActivity(Integer activityId)throws InfyTrackerException;

    public List<EmployeeDTO> getAllActivity() throws InfyTrackerException;

    public EmployeeDTO getUserAllInCompleteActivity(Integer employeeId) throws InfyTrackerException;

    public EmployeeDTO getActivityByStatus(Integer employeeId, ActivityDTO activityDTO) throws InfyTrackerException;

    public String updateActivity(ActivityDTO activityDTO) throws InfyTrackerException;

    

    

    

}