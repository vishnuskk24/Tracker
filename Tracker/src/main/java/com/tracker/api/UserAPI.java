package com.tracker.api;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tracker.dto.ActivityDTO;
import com.tracker.dto.ChangePasswordDTO;
import com.tracker.dto.EmployeeDTO;
import com.tracker.entity.ActivityStatus;
import com.tracker.jwtvalidation.JwtService;
import com.tracker.service.EmployeeService;
import com.tracker.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "user/")
@Validated
@PreAuthorize("hasAnyAuthority('Manager','Lead','User')")
public class UserAPI {
//    getMyProfile  - > done

//    getMyTaskAccording to the status
//    getMyAlltask from these date to that date
//    getMyAllTaskBStatusAndDate
//    updateMytaskDetail according to the activity Id
	// change password
//    forget password

	@Autowired
	UserService userService;
//    localhost:8765/user/get    MyProfile/
	@Autowired
	JwtService util;
	@Operation
    @ApiResponses
	@GetMapping(value = "/getMyProfile/") // userid need to come in json object
	public ResponseEntity<EmployeeDTO> getEmployee(@RequestHeader("Authorization") String jwt,
			@RequestBody EmployeeDTO employee) throws NumberFormatException, Exception {

		System.out.println("inside  user api Extracting");
		String actualJWT = jwt.substring(7);
		System.out.println(jwt + " jwt token in get profile +++++++++++++++++");
//        int empId =Integer.parseInt(util.getValuesFromJwt(jwt,"id"));
//        System.out.println(util.getMapFromJwt(jwt.substring(7), "id"));
		EmployeeDTO e1 = util.getEmployeeDetailFromJWT(jwt.substring(7));
//		EmployeeDTO e1 = null ;// util.getEmployeeDetailFromJWT(jwt); ********
//        EmployeeDTO ee =  userService.getMyDetail(Integer.parseInt(util.getValuesFromJwt(actualJWT,"id"))) ;
		EmployeeDTO ee = userService.getMyDetail(e1.getEmployeeId());
		ResponseEntity<EmployeeDTO> e = new ResponseEntity<EmployeeDTO>(ee, HttpStatus.OK);
		return e;

	}
	@Operation
    @ApiResponses
	@GetMapping(value = "/getMyTaskByStatus/{status}") // userid need to come in json object need to check OR gate
	public ResponseEntity<EmployeeDTO> getMyTaskByStatus(@RequestHeader("Authorization") String jwt,
			@PathVariable ActivityStatus status) throws Exception {
//        @Pattern(regexp = "In_Progress|Completed|Not_Yet_Started|Remove",message = "{Activity.status.invalid}") 
		EmployeeDTO e1 = util.getEmployeeDetailFromJWT(jwt.substring(7));
		EmployeeDTO employeeDTO = userService.getMyTaskByStatus(e1.getEmployeeId(), status);
		ResponseEntity<EmployeeDTO> e = new ResponseEntity<EmployeeDTO>(employeeDTO, HttpStatus.OK);
		return e;

	}

//    @GetMapping(value="/getMyAllTask")             // provide empId in activity id feild
//    public ResponseEntity<EmployeeDTO> getMyAllTask(@RequestBody ActivityDTO activityDTO) throws Exception {
//        
//        
//        EmployeeDTO employeeDTO=  userService.getMyAllTask(activityDTO.getActivityId(),activityDTO.getStateDate(),activityDTO.getEndDate()) ;
//        ResponseEntity<EmployeeDTO> e = new ResponseEntity<EmployeeDTO>(employeeDTO, HttpStatus.OK);
//        return e;
//        
//    }
//    need to check
	@Operation
    @ApiResponses
	@PutMapping(value = "/updateMyTaskDetail")
	public ResponseEntity<String> updateMyTaskDetail(@RequestBody ActivityDTO activityDTO) throws Exception {
		String msg = userService.updateMyTaskDetail(activityDTO);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	@Operation
    @ApiResponses
	@PutMapping(value = "/changePassword")
	public ResponseEntity<String> changePassword(@RequestHeader("Authorization") String jwt,
			@Valid @RequestBody ChangePasswordDTO passwordDetails) throws Exception {

		EmployeeDTO e1 = util.getEmployeeDetailFromJWT(jwt.substring(7));
		passwordDetails.setEmployeeId(e1.getEmployeeId());
		userService.changePassword(passwordDetails);
		String msg = "Password changed Successfully";
		return new ResponseEntity<String>(msg, HttpStatus.OK);

	}
}

// url and json
/*
 * 1. localhost:8765/user/getMyProfile/ { "employeeId":1171564
 * 
 * }
 * 
 * 2.localhost:8765/user/getMyTaskByStatus/In_Progress { "employeeId":1171564
 * 
 * } 3. localhost:8765/user/updateMyTaskDetail { "activityId": 9,
 * "activityName": "Spring objective Qb creation", "stateDate": "2023-08-01",
 * "endDate": "2023-10-01", "assignedBy": "neena_mc", "noOfTimes": "0",
 * "remarks": "", "activityStatus": "Not_Yet_Started" } // sample activity
 * object
 * 
 * 4.localhost:8765/user/changePassword { "employeeId" : 1, "currentPassword"
 * :"", "newPassword" :"" }
 * 
 * need to change the application if the jwt token is not present then login and
 * while implemtnting the front end with jwt token
 * 
 * 
 * update task all posiblity and change password regex pattern
 */
