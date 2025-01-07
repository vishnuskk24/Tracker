package com.eng.service;

import java.util.List;

import com.eng.dto.ActivityDTO;
import com.eng.dto.EmployeeDTO;
import com.eng.entity.ActivityStatus;
import com.eng.exception.InfyTrackerException;

public interface ActivityService {

    public String addActivity(Integer employeeId,ActivityDTO activityDTO) throws InfyTrackerException;

    public ActivityDTO getActivity(Integer activityId)throws InfyTrackerException;

    public List<EmployeeDTO> getAllActivity() throws InfyTrackerException;

    public EmployeeDTO getUserAllInCompleteActivity(Integer employeeId) throws InfyTrackerException;

    public EmployeeDTO getActivityByStatus(Integer employeeId, ActivityDTO activityDTO) throws InfyTrackerException;

    public String updateActivity(ActivityDTO activityDTO) throws InfyTrackerException;

    

    

    

}