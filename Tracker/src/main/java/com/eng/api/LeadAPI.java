package com.eng.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.eng.dto.ActivityDTO;
import com.eng.dto.EmployeeDTO;
import com.eng.entity.ActivityStatus;
import com.eng.exception.InfyTrackerException;
import com.eng.jwtvalidation.JwtFilter;
import com.eng.jwtvalidation.JwtService;
import com.eng.service.ActivityService;
import com.eng.service.EmployeeService;
import com.eng.service.LeadService;
//import com.eng.utility.UtilityMethods;
@RestController
@RequestMapping(value="lead/")
@PreAuthorize("hasAnyAuthority('Lead','Manager')")
public class LeadAPI {

//    addtask - > done checked
//    
//    remove task  if it is assigned by me - >  done checked
//
//     getthe user task status according to the user id -done
//    
//    reassign the task to someone if it is assigned by me - >
//    gett the user Details by id - > done
//     get all the employee detail - > done
    
    @Autowired
    Environment environment;
    @Autowired
    private ActivityService activityService; 
    
    @Autowired
    JwtService util;
    
    @Autowired
    private EmployeeService employeeService; 
    @Autowired
    private LeadService leadService; 
    
    @PostMapping(value="addtask/{empId}")
    public ResponseEntity<String> addTask(@RequestHeader("Authorization") String jwt, @PathVariable Integer empId,@Valid @RequestBody ActivityDTO activity) throws Exception{
        String actualJWT = jwt.substring(7);
        System.out.println(jwt + " jwt token in get profile +++++++++++++++++");
//        int empId =Integer.parseInt(util.getValuesFromJwt(jwt,"id"));
//        System.out.println(util.getMapFromJwt(jwt.substring(7), "id"));
        EmployeeDTO e1 = null ;// util.getEmployeeDetailFromJWT(jwt); ********
        
        System.out.println("assignee username " + e1.getUsername());
        activity.setAssignedBy(e1.getUsername());
        String msg = leadService.addActivity(empId, activity);
        
        return new ResponseEntity<>(msg,HttpStatus.CREATED);
        
    }
    @PutMapping(value="removeTask/")
    public ResponseEntity<String>  removeTask(@RequestHeader("Authorization") String jwt,@RequestBody EmployeeDTO employee) throws InfyTrackerException{
        
//        EmployeeDTO e1 = util.getEmployeeDetailFromJWT(jwt);
    	EmployeeDTO e1 = null ;// util.getEmployeeDetailFromJWT(jwt); ********
        
        String msg =leadService.removeActivity(employee.getActivities().get(0).getActivityId(),e1.getEmployeeId());
        
        return new ResponseEntity<>(environment.getProperty(msg),HttpStatus.OK);
    }
    
    @PutMapping(value = "reassignTask/")
    public ResponseEntity<String> reassignTask(@RequestBody EmployeeDTO employeeDTO) throws InfyTrackerException{
        
        
        String msg = leadService.reassignTask(employeeDTO.getActivities().get(0).getActivityId(),employeeDTO.getEmployeeId());
        
        return new ResponseEntity<>(environment.getProperty(msg),HttpStatus.OK);
    }
    
    @GetMapping(value = "userTaskAccordingToUserIdAndStatus/{activityStatus}")
    public ResponseEntity<List<EmployeeDTO>> userTaskAccordingToUserIdAndStatus(@RequestBody EmployeeDTO employee, @PathVariable ActivityStatus activityStatus) throws InfyTrackerException{
        
        List<EmployeeDTO> activities = leadService.getUserActivityByUserIdAndStatus(employee.getEmployeeId(),activityStatus);
        return new ResponseEntity<List<EmployeeDTO>>(activities,HttpStatus.OK);
        
    }
    @GetMapping(value="/getEmployee/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable @Min( value =1,message = "The Given employee Id  must be greater then 0")  Integer employeeId) throws InfyTrackerException {
        
        EmployeeDTO ee =  employeeService.getEmployee(employeeId) ;
        ResponseEntity<EmployeeDTO> e = new ResponseEntity<EmployeeDTO>(ee, HttpStatus.OK);
        return e;
        
    }
    @GetMapping(value="/getAllEmployee/")
    public ResponseEntity<List<EmployeeDTO>> getEmployee() throws InfyTrackerException {
        
        List<EmployeeDTO> employees =  employeeService.getAllEmployee() ;
        ResponseEntity<List<EmployeeDTO>> e = new ResponseEntity<>(employees, HttpStatus.OK);
        return e;
        
    }
}



//localhost:8765/lead/addtask/1171654

/*  
1. localhost:8765/lead/addtask/1171654  (employee id)    post   done
{     
"activityName": "Java Certification scaleup",
"stateDate": "2023-08-01",
"endDate": "2023-10-01",
"assignedBy": "balasundari.v"
}
                                /{activityId}/{empId}     done
2.localhost:8765/lead/removeTask/                        put
{
"employeeId":8037,
"activities":[
   {
       "activityId":22
   }
]
}
3. localhost:8765/lead/getEmployee/1171654    
4.localhost:8765/lead/getAllEmployee/
5.localhost:8765/lead/reassignTask/  -  > new Employee ID
{
"employeeId":8037,  -- >  new Assignee
"activities":[
   {
       "activityId":22    - > activity that yiu want to reassign
   }
]
}
6. localhost:8765/lead/userTaskAccordingToUserIdAndStatus/{status}
{
"employeeId":8037
}


*/

