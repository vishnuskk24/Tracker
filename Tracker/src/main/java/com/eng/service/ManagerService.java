package com.eng.service;

import java.util.List;

//import javax.validation.Valid;
//import javax.validation.constraints.Min;

import com.eng.dto.ActivityDTO;
import com.eng.dto.EmployeeDTO;
import com.eng.entity.ActivityStatus;
import com.eng.exception.InfyTrackerException;

public interface ManagerService {

    public void addUser( EmployeeDTO userDetails) throws Exception;

    public String addActivity(Integer empId, ActivityDTO activity) throws InfyTrackerException;

    public String changeRole(EmployeeDTO employeeDetails) throws InfyTrackerException;

    public String removeActivity(Integer activityId, Integer empId) throws InfyTrackerException;

    public String reassignTask(Integer activityId, Integer newEmpId, Integer empId) throws InfyTrackerException;

    public String deactivateEmployee(EmployeeDTO employee) throws InfyTrackerException;

    public List<EmployeeDTO> getAllTeamMemberTask() throws InfyTrackerException;

    public List<EmployeeDTO> getAllTeamMemberTaskByStatus(ActivityStatus activityStatus) throws InfyTrackerException;

    public List<EmployeeDTO> getAllTeamMemberTaskByStatusById(Integer employeeId, ActivityStatus activityStatus)throws InfyTrackerException;

    public List<EmployeeDTO> getAlluserDetails() throws InfyTrackerException;
    public EmployeeDTO getuserDetailsById(Integer empId) throws InfyTrackerException;

    public EmployeeDTO getTaskDetailsByActivityId(Integer activityId) throws InfyTrackerException;

}
