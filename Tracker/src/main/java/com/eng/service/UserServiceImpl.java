package com.eng.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.eng.dao.ActivityRepository;
import com.eng.dao.UserRepository;
import com.eng.dto.ActivityDTO;
import com.eng.dto.ChangePasswordDTO;
import com.eng.dto.EmployeeDTO;
import com.eng.entity.Activity;
import com.eng.entity.ActivityStatus;
import com.eng.entity.Employee;
import com.eng.entity.Status;
import com.eng.exception.InfyTrackerException;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ActivityRepository activityRepository;

	@Override
	public EmployeeDTO getMyDetail(Integer employeeId) throws InfyTrackerException {
		// TODO Auto-generated method stub
		EmployeeDTO e = getEmployee(employeeId);
		if (e.getStatus().equals(Status.Deactive)) {
			throw new InfyTrackerException("Service.User_Deactivate");
		}
		return e;
	}

	@Override
	public EmployeeDTO getMyTaskByStatus(Integer employeeId, ActivityStatus status) throws Exception {
		// TODO Auto-generated method stub
		EmployeeDTO employeeDTO = getEmployee(employeeId);// it will check and get the employee detail according to the
															// Id
//            next  we need to get the list of activity according to the status 
		Employee employee = userRepository.getEmployeeByEmployeeId(employeeId);
		if (employee.getStatus().equals(Status.Deactive)) {
			throw new InfyTrackerException("Service.User_Deactivate");
		}
//            /transfer  entity to DTO
//            if there  is no activity Throw as Exception as an response
		if (employee.getActivities() == null || employee.getActivities().size() == 0
				|| employee.getActivities().isEmpty()) {
			// according to the user input it will through the exception

			throw new InfyTrackerException("Service.No_Activities_" + status);

		}

		// if activity is there
		List<ActivityDTO> activities = new ArrayList<>();
		for (Activity a : employee.getActivities()) {

			if (a.getActivityStatus().equals(status)) {
				System.out.println("activity adding line 61  +++++++++++++");
				activities.add(new ActivityDTO(a));
			}

		}

		if (activities.isEmpty()) {
			throw new InfyTrackerException("Service.No_Activities_" + status);
		}

		employeeDTO.setActivities(activities);

		return employeeDTO;

	}

// these method will check and get the employee detail according to the Id
	public EmployeeDTO getEmployee(Integer employeeId) throws InfyTrackerException {
		if (employeeId == null) {
			throw new InfyTrackerException("Service.Provide_Employee_Id");

		}
		Optional<Employee> optional = userRepository.findById(employeeId);
		Employee employee = optional.orElseThrow(() -> new InfyTrackerException("Service.User_UnAvailable"));
		EmployeeDTO employeeDTO = employee.getEmployeeOnly(employee);
		return employeeDTO;

	}

//  @Override
//  public EmployeeDTO getMyAllTask(Integer employeeId, LocalDate stateDate, LocalDate endDate) throws InfyTrackerException {
//      EmployeeDTO employeeDTO= getEmployee(employeeId);
//      
//      Employee employee = userRepository.getAllTaskAccordingToDate( employeeId,  stateDate,  endDate);
//      
//      if(employee.getActivities()==null || employee.getActivities().size()==0 ) {
//          // according to the user input it will through the exception
//          
//          throw new InfyTrackerException("Service.No_Activities_Date");
//          
//      }
//      
//      
//      employeeDTO.setActivities(getActivityDTOFromEmployee(employee));
//      
//      
//      return employeeDTO;
//      
//      
//  }
	@Override
	public void checkEmployee(Employee e) throws InfyTrackerException {

		if (e != null && e.getStatus().equals(Status.Deactive)) {
			throw new AccessDeniedException("Service.Assignee_Deactive");
		}
	}

	public List<ActivityDTO> getActivityDTOFromEmployee(Employee employee) {

		List<ActivityDTO> activities = new ArrayList<>();
		for (Activity activity : employee.getActivities()) {
			ActivityDTO activityDTO = new ActivityDTO(activity);
			activities.add(activityDTO);

		}
		return activities;
	}

	@Override
	public String updateMyTaskDetail(ActivityDTO activityDTO) throws InfyTrackerException {

		Optional<Activity> optional = activityRepository.findById(activityDTO.getActivityId());
		Activity activity = optional.orElseThrow(() -> new InfyTrackerException("Service.Activity_Not_Found"));
		// need to implement check activitystatus
//  if(activityDTO.getActivityStatus())
		checkActivityStatus(activity.getActivityStatus(), activityDTO.getActivityStatus());
		String msg = "Service.Activity_Update_Success";
//  Activity activity = new Activity(activityDTO);
		activity.setActivityName(msg);
		activity.setActivityStatus(activityDTO.getActivityStatus());
		activity.setNoOfTimes((Integer.parseInt(activity.getNoOfTimes() + 1)) + "");
		activity.setRemarks(activity.getRemarks() + "\n" + activityDTO.getRemarks());
		activityRepository.save(activity);
		return msg;

	}

	public void checkActivityStatus(ActivityStatus currentStatus, ActivityStatus updatedStatus)
			throws InfyTrackerException {

//      if(currentStatus.equals(updatedStatus)) {// completed  only we can complpeted 
//          throw new InfyTrackerException("Service.Updated_Current_Activity_Same");
//          
//      }
		if (currentStatus.equals(ActivityStatus.Not_Yet_Started) && updatedStatus.equals(ActivityStatus.In_Progress)) { // only
																														// yet_to_started
																														// activity
																														// needs
																														// to
																														// be
																														// inprogress
			return;
		}
		if (currentStatus.equals(ActivityStatus.In_Progress) && updatedStatus.equals(ActivityStatus.Completed)) { // only
																													// inprogress
																													// activity
																													// needs
																													// to
																													// be
																													// completed
			return;
		}
		throw new InfyTrackerException("Service." + currentStatus + "_Status_can't_be_changed_to_" + updatedStatus);

//      Service.Not_Yet_Started_Status_can't_be_changed_to_Completed
//      Service.In_Progress_Status_can't_be_changed_to_Not_Yet_Started

	}

	@Override
	public void changePassword(ChangePasswordDTO passwordDetails) throws InfyTrackerException {
		// TODO Auto-generated method stub
		Optional<Employee> optional = userRepository.findById(passwordDetails.getEmployeeId());

		Employee emp = optional.orElseThrow(() -> new InfyTrackerException("Service.User_UnAvailable"));
		if (emp.getPassword().equals(passwordDetails.getCurrentPassword())) {
			System.out
					.println("if block in change password\n setting new password " + passwordDetails.getNewPassword());
			emp.setPassword(passwordDetails.getNewPassword());
			userRepository.save(emp);

		} else {
			System.out.println("else block in change password");
			throw new InfyTrackerException("Service.Current_Password_And_Orginal_password_Mismatch");
		}
	}

}
