package com.eng.api;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eng.dto.ActivityDTO;
import com.eng.dto.EmployeeDTO;
import com.eng.entity.Employee;
import com.eng.exception.InfyTrackerException;
//import com.eng.security.MyUserDetails;
import com.eng.service.ManagerService;

@RestController
@RequestMapping(value = "manager/")
@Validated
@CrossOrigin("*")
public class ManagerAPI {
//  addUser -- >  implemented 1 done
//    addTask  -- > implemented 2 done
//    change role --- > implemented 3 done
//    remove task -- > implemented 4  done
//    reassign the task to someone - > implemented 5
//    deactivate the user  - > implemented 6 done

//    getAllTeamMemberTask -- > Implemented 7 done
//    getAllTeamMemberTask according to the  status -- > implemented 8 done
//    getTeamMemberTaskAccording to the user Id and status individually -- > implemented 9 done
//    getAlluserDetails - >implemented 10 done
//    getuserDetails according to user Id - > implemented 11
//    getTaskdetails according to the Id -- > implemented 12

//    update userDetail -->
//    forgetpassword
//    get task according between the given dates

	@Autowired
	ManagerService managerService;
	@Autowired
	Environment environment;

	// add user
//     localhost:8765/manager/addUser/
	@PostMapping(value = "addUser/")
	@PreAuthorize("hasAuthority('Manager')")
	@Secured("Manager")
	public ResponseEntity<String> addUser(@Valid @RequestBody EmployeeDTO userDetails) throws Exception {

		managerService.addUser(userDetails);
		String msg = "user added successfully";
		return new ResponseEntity<String>(msg, HttpStatus.CREATED);
	}

	// addtask
//    localhost:8765/manager/addtask/1171564
	@PostMapping(value = "addtask/{empId}")
	@PreAuthorize("hasAuthority('Manager')")
	@Secured("Manager")
	public ResponseEntity<String> addTask(@PathVariable Integer empId, @Valid @RequestBody ActivityDTO activity)
			throws Exception {

		String msg = managerService.addActivity(empId, activity);

		return new ResponseEntity<>(msg, HttpStatus.CREATED);

	}

	@PutMapping(value = "/changeRole")
	@PreAuthorize("hasAuthority('Manager')")
	@Secured("Manager")
	public ResponseEntity<String> changeRole(@RequestBody EmployeeDTO employeeDetails) throws Exception {

		String msg = environment.getProperty(managerService.changeRole(employeeDetails));

		return new ResponseEntity<String>(msg, HttpStatus.OK);

	}

	@PutMapping(value = "/removeTask/")
	@PreAuthorize("hasAuthority('Manager')")
	@Secured("Manager")
	public ResponseEntity<String> removeTask(@RequestBody EmployeeDTO employeeDetails) throws InfyTrackerException {

		String msg = managerService.removeActivity(employeeDetails.getActivities().get(0).getActivityId(),
				employeeDetails.getEmployeeId());

		return new ResponseEntity<>(environment.getProperty(msg), HttpStatus.OK);
	}

	@PutMapping(value = "/reassignTask/{newEmpId}/")
	@PreAuthorize("hasAuthority('Manager')")
	@Secured("Manager")
	public ResponseEntity<String> reassignTask(@RequestBody EmployeeDTO employeeDTO, @PathVariable Integer newEmpId)
			throws InfyTrackerException {

		String msg = managerService.reassignTask(employeeDTO.getActivities().get(0).getActivityId(), newEmpId,
				employeeDTO.getEmployeeId());

		return new ResponseEntity<>(environment.getProperty(msg), HttpStatus.OK);
	}

//    localhost:8765/manager/deactivateEmployee
	@PutMapping(value = "/deactivateEmployee")
	@PreAuthorize("hasAuthority('Manager')")
	@Secured("Manager")
	public ResponseEntity<String> updateEmployee(@RequestBody EmployeeDTO employee) throws InfyTrackerException {
		// these method basically update the employee status

		String msg = managerService.deactivateEmployee(employee);
		ResponseEntity<String> e = new ResponseEntity<String>(environment.getProperty(msg), HttpStatus.OK);
		return e;

	}

	@GetMapping(value = "/getAllTeamMemberTask")
	@PreAuthorize("hasAuthority('Manager')")
	@Secured("Manager")
	public ResponseEntity<List<EmployeeDTO>> getAllTeamMemberTask() throws Exception {
		List<EmployeeDTO> employes = managerService.getAllTeamMemberTask();

		return new ResponseEntity<List<EmployeeDTO>>(employes, HttpStatus.OK);
	}

	@GetMapping(value = "/getAllTeamMemberTaskByStatus")
	@PreAuthorize("hasAuthority('Manager')")
	@Secured("Manager")
	public ResponseEntity<List<EmployeeDTO>> getAllTeamMemberTaskByStatus(@RequestBody ActivityDTO activity)
			throws Exception {
		List<EmployeeDTO> employes = managerService.getAllTeamMemberTaskByStatus(activity.getActivityStatus());

		return new ResponseEntity<List<EmployeeDTO>>(employes, HttpStatus.OK);
	}

	@GetMapping(value = "/getAllTeamMemberTaskByStatus/{employeeId}")
	@PreAuthorize("hasAuthority('Manager')")
	@Secured("Manager")
	public ResponseEntity<List<EmployeeDTO>> getAllTeamMemberTaskByStatusById(
			@NotNull(message = "{employee.employeeid.absent}") @PathVariable Integer employeeId,
			@RequestBody ActivityDTO activity) throws Exception {
		List<EmployeeDTO> employes = managerService.getAllTeamMemberTaskByStatusById(employeeId,
				activity.getActivityStatus());

		return new ResponseEntity<List<EmployeeDTO>>(employes, HttpStatus.OK);
	}

	@GetMapping(value = "/getAlluserDetails")
	@PreAuthorize("hasAuthority('Manager')")
	@Secured("Manager")

	public ResponseEntity<List<EmployeeDTO>> getAlluserDetails() throws Exception {
		List<EmployeeDTO> employes = managerService.getAlluserDetails();

		return new ResponseEntity<List<EmployeeDTO>>(employes, HttpStatus.OK);
	}

	@GetMapping(value = "/getUserDetail/{empId}") //
	@PreAuthorize("hasAuthority('Manager')")
	@Secured("Manager")
	public ResponseEntity<EmployeeDTO> getUserDetails(
			@PathVariable @Min(value = 1, message = "{employee id must be greater than 0}") Integer empId)
			throws Exception {
		EmployeeDTO employes = managerService.getuserDetailsById(empId);

		return new ResponseEntity<EmployeeDTO>(employes, HttpStatus.OK);
	}
//    @Secured("Manager")

	@GetMapping(value = "/getUserActivityDetails/{activityId}")
	@PreAuthorize("hasAuthority('Manager')")
	@Secured("Manager")
	public ResponseEntity<EmployeeDTO> getTaskDetailsByTaskId(
			@PathVariable @Min(value = 1, message = "{activity id must be greater than 0}") Integer activityId)
			throws Exception {
		EmployeeDTO employes = managerService.getTaskDetailsByActivityId(activityId);

		return new ResponseEntity<EmployeeDTO>(employes, HttpStatus.OK);
	}
//    @PutMapping(value ="/updateDetails/{activityId}")
//    public ResponseEntity<String> updateEmployee(@RequestBody EmployeeDTO employeeDTO) throws Exception{
//        EmployeeDTO employes = managerService.getTaskDetailsByActivityId(employeeDTO);
//        
//        return new ResponseEntity<EmployeeDTO>(employes, HttpStatus.OK);
//    }
}

/*
 * localhost:8765/manager/
 */
/*
 * 1. localhost:8765/manager/addUser/ { "employeeId": 699720, "employeeName":
 * "Rex Augustin Raj", "username": "rex_raj01", "mailId":
 * "rex_raj01@infosys.com", "status": "Active", "password": "rex_raj01", "role":
 * "User" }
 * 
 * 2.localhost:8765/manager/addtask/(1171564) empid { "activityName":
 * "Java Certification scaleup", "stateDate": "2023-08-01", "endDate":
 * "2023-10-01", "assignedBy": "balasundari.v" } 3.
 * localhost:8765/manager/changeRole { "employeeId": 699720, "role": "User" }
 * 
 * 4. localhost:8765/manager/removeTask { "employeeId":8037, "activities":[ {
 * "activityId":22 } ] } 5. localhost:8765/manager/reassignTask/
 * 
 * { "employeeId":1223805, "activities":[ { "activityId":25 } ] }
 * 
 * 6. localhost:8765/manager/deactivateEmployee { "employeeId":1171564,
 * "status":Active }
 * 
 * 7. localhost:8765/manager/getAllTeamMemberTask
 * 
 * 8. localhost:8765/manager/getAllTeamMemberTaskByStatus { "activityStatus":
 * "In_Progress" }
 * 
 * 
 * 9. localhost:8765/manager/getAllTeamMemberTaskByStatus/1171564 {
 * "activityStatus": "In_Progress" } 10.
 * localhost:8765/manager/getAlluserDetails
 * 
 * 11. localhost:8765/manager/getUserDetail/1171564
 * 
 */
