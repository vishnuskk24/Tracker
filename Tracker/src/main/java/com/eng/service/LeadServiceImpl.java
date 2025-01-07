package com.eng.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eng.dao.ActivityRepository;
import com.eng.dao.EmployeeDAO;
import com.eng.dao.UserRepository;
import com.eng.dto.ActivityDTO;
import com.eng.dto.EmployeeDTO;
import com.eng.entity.Activity;
import com.eng.entity.ActivityStatus;
import com.eng.entity.Employee;
import com.eng.entity.Role;
import com.eng.entity.Status;
import com.eng.exception.InfyTrackerException;

@Service
public class LeadServiceImpl implements LeadService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	ActivityRepository activityDAO;

	@Autowired
	EmployeeDAO employeeDAO;
	@Autowired
	UserService userService;

	@Override
	public String addActivity(Integer employeeId, ActivityDTO activityDTO) throws InfyTrackerException {

		Optional<Employee> optional = employeeDAO.findById(employeeId);
		Employee employee = optional.orElseThrow(() -> new InfyTrackerException("Service.User_UnAvailable"));

		if (employee.getStatus().equals(Status.Deactive)) {
			throw new InfyTrackerException("Service.Employee_Status_Deactivate_Addactivity");
		}
		// check wheather these task is added by Lead or manager
		System.out.println(activityDTO.getAssignedBy() + " assignee user name in lead service add task");
		Employee e = userRepository.findByUsername(activityDTO.getAssignedBy());
		if (e == null) {
			throw new InfyTrackerException("Service.Activity_Assignee_Not_Found");
//         throw new InfyTrackerException("Service.Activity_Needs_To_Be_Added_By_Lead_Or_Manager");
		}
		System.out.println("checking the assignrd by employee status ");
		userService.checkEmployee(e);
		if (activityDTO.getEndDate().isBefore(activityDTO.getStateDate()))
			throw new InfyTrackerException("Service.EndDate_Needs_To_Be_After_Start_Date");

		if (e.getRole().equals(Role.User))
			throw new InfyTrackerException("Service.Activity_Needs_To_Be_Added_By_Lead_Or_Manager");

		if (employee.getRole().equals(Role.Manager))
			throw new InfyTrackerException("Service.Lead_Can't_Be_Added_Activity_To_Manager");

		Activity activity = new Activity(activityDTO);
		activity.setActivityStatus(ActivityStatus.Not_Yet_Started);
		activity.setRemarks("");
		activity.setNoOfTimes("0");
		if (employee.getActivities() == null) {// no activity provided to employee
			List<Activity> activities = new ArrayList<>();

			employee.setActivities(activities);
		}
		employee.getActivities().add(activity);
		Employee e1 = employeeDAO.save(employee); // getting last activity Id
		String resp = " Activity added succesfully to the employee with activity Id : "
				+ e1.getActivities().get(e1.getActivities().size() - 1).getActivityId();

		return resp;
	}

	@Override
	public String removeActivity(Integer activityId, Integer empId) throws InfyTrackerException {
		// TODO Auto-generated method stub
		Optional<Employee> optional = employeeDAO.findById(empId);
		// validate Emplid
//        validate activity Id
		Employee employee = optional.orElseThrow(() -> new InfyTrackerException("Service.User_UnAvailable"));

		Optional<Activity> optionalActivity = activityDAO.findById(activityId);
		Activity activity = optionalActivity.orElseThrow(() -> new InfyTrackerException("Service.Activity_Not_Found"));
//        
//        if(activity.getActivityStatus().equals(ActivityStatus.Remove)) throw new InfyTrackerException("Service.Activity_Already_In_Remove_State");
//        
		if (!employee.getUsername().equalsIgnoreCase(activity.getAssignedBy())) {
			throw new InfyTrackerException("Service.Activity_Can't_Remove_Not_Correct_Assignee");
		}
//        activity.de(ActivityStatus.Remove);
		activityDAO.delete(activity);

		return "Service.Activity_Removed_Successfull";
	}

	@Override
	public String reassignTask(Integer activityId, Integer newEmpId) throws InfyTrackerException {
//        System.out.println("reassign line 91");
		Optional<Employee> optional = userRepository.findById(newEmpId);
//        System.out.println("reassign line 93");
		Employee employee = optional
				.orElseThrow(() -> new InfyTrackerException("Service.Reassign_New_User_UnAvailable"));
//        System.out.println("reassign line 95");
		if (employee.getRole().equals(Role.Manager))
			throw new InfyTrackerException("Service.Cant_Reassign_To_Manager");
		if (employee.getStatus().equals(Status.Deactive))
			throw new InfyTrackerException("Service.Reassign_New_User_Deactivate");
		Optional<Activity> optionalActivity = activityDAO.findById(activityId);
		Activity activity = optionalActivity.orElseThrow(() -> new InfyTrackerException("Service.Activity_Not_Found"));
		activity.setActivityStatus(ActivityStatus.Not_Yet_Started);
		if (activity.getActivityStatus().equals(ActivityStatus.Completed))
			throw new InfyTrackerException("Service.Cant_Reassign_Task_Its_Completed");
//        System.out.println("reassign line 98");
//        Optional<Employee> optionalPreviousAssignee = userRepository.findById(oldEmpId);
//        Employee previousAssignee = optionalPreviousAssignee.orElseThrow(()-> new InfyTrackerException("Service.Can't_Found_Prevoius_Assignee"));
//        System.out.println("reassign line 101");
//        int index = 0;
//        System.out.println("reassign line 103");
//        List<Activity> Activities = previousAssignee.getActivities();
//        System.out.println("reassign line 105");
//        boolean flag=false;

//        if(flag) {
//        previousAssignee.getActivities().remove(index); //  removing the task from the previous employee
//        }
		if (employee.getActivities() == null || employee.getActivities().size() == 0) {
			employee.setActivities(new ArrayList<>());
		}
		employee.getActivities().add(activity); // reassign the task to the new user

//        userRepository.save(previousAssignee);
		userRepository.save(employee); // commiting the changes
		return "Service.Activity_Reassign_Success"; // returning the message once the process done

	}

	@Override
	public List<EmployeeDTO> getUserActivityByUserIdAndStatus(Integer empId, ActivityStatus activityStatus)
			throws InfyTrackerException {
		// TODO Auto-generated method stub
		Optional<Employee> optional = userRepository.findById(empId);
		Employee employee = optional.orElseThrow(() -> new InfyTrackerException("Service.User_UnAvailable"));

		if (employee.getActivities() == null || employee.getActivities().isEmpty()) {// if no activity found for given
																						// employee
			throw new InfyTrackerException("Service.No_Activity_" + activityStatus);

		}
		List<EmployeeDTO> result = new ArrayList<>();

		for (Activity activity : employee.getActivities()) {
			EmployeeDTO emp = new EmployeeDTO().getEmployeeOnly(employee);// initilize new object when you adding
																			// activity
			if (activityStatus.equals(activity.getActivityStatus())) {
				ActivityDTO activityDTO = new ActivityDTO(activity);
				emp.setActivities(new ArrayList<>()); // every time initilize new list
				emp.getActivities().add(activityDTO);
				result.add(emp);
			}
		}

		return result;

	}
}
