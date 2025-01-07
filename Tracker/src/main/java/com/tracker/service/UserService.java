package com.tracker.service;

import java.time.LocalDate;
import java.util.List;

import com.tracker.dto.ActivityDTO;
import com.tracker.dto.ChangePasswordDTO;
import com.tracker.dto.EmployeeDTO;
import com.tracker.entity.ActivityStatus;
import com.tracker.entity.Employee;
import com.tracker.exception.InfyTrackerException;

public interface UserService {

    public EmployeeDTO getMyDetail(Integer employeeId) throws InfyTrackerException;
    
    public EmployeeDTO getMyTaskByStatus(Integer employeeId, ActivityStatus status) throws Exception;

//    public EmployeeDTO getMyAllTask(Integer employeeId, LocalDate stateDate, LocalDate endDate) throws InfyTrackerException;

    public String updateMyTaskDetail(ActivityDTO activityDTO) throws InfyTrackerException;

    public void changePassword( ChangePasswordDTO passwordDetails) throws InfyTrackerException;

    public void checkEmployee(Employee e) throws InfyTrackerException;
}