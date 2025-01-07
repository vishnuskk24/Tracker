package com.eng.service;

import java.time.LocalDate;
import java.util.List;

//import javax.validation.Valid;

import com.eng.dto.ActivityDTO;
import com.eng.dto.ChangePasswordDTO;
import com.eng.dto.EmployeeDTO;
import com.eng.entity.ActivityStatus;
import com.eng.entity.Employee;
import com.eng.exception.InfyTrackerException;

public interface UserService {

    public EmployeeDTO getMyDetail(Integer employeeId) throws InfyTrackerException;
    
    public EmployeeDTO getMyTaskByStatus(Integer employeeId, ActivityStatus status) throws Exception;

//    public EmployeeDTO getMyAllTask(Integer employeeId, LocalDate stateDate, LocalDate endDate) throws InfyTrackerException;

    public String updateMyTaskDetail(ActivityDTO activityDTO) throws InfyTrackerException;

    public void changePassword( ChangePasswordDTO passwordDetails) throws InfyTrackerException;

    public void checkEmployee(Employee e) throws InfyTrackerException;
}