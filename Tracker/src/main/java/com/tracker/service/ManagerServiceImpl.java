package com.tracker.service;

import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tracker.dao.ActivityRepository;
import com.tracker.dao.UserRepository;
import com.tracker.dto.ActivityDTO;
import com.tracker.dto.EmployeeDTO;
import com.tracker.entity.Activity;
import com.tracker.entity.ActivityStatus;
import com.tracker.entity.Employee;
import com.tracker.entity.Status;
import com.tracker.exception.InfyTrackerException;
@Service
public class ManagerServiceImpl implements ManagerService{

    @Autowired UserRepository userRepository;
    @Autowired EmployeeService employeeService;
    @Autowired ActivityRepository activityRepository;
    @Autowired UserService userService;
    
    BCryptPasswordEncoder bCryptPasswordEncoder =  new BCryptPasswordEncoder(4);
    @Override
    public void addUser( EmployeeDTO userDetails) throws Exception {
        // TODO Auto-generated method stub
        
        Optional<Employee> optional =  userRepository.findById(userDetails.getEmployeeId());
        if(optional.isPresent()) {
            throw new InfyTrackerException("Service.User_Already_Exist");        
        }
        Employee employee =  new Employee(userDetails);
        employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword())); // hashing the password
        userRepository.save(employee);
        
    }
    @Override
    public String addActivity(Integer employeeId,ActivityDTO activityDTO) throws InfyTrackerException {
        
     Optional<Employee> optional=userRepository.findById(employeeId);
     Employee employee = optional.orElseThrow(()->new InfyTrackerException("Service.User_UnAvailable"));
     
     if(employee.getStatus().equals(Status.Deactive)) {
         throw new InfyTrackerException("Service.Employee_Status_Deactivate_Addactivity");
     }
     // validating the person who is adds the task
     Employee addersOptional =  userRepository.findByUsername(activityDTO.getAssignedBy());
    if(addersOptional==null)new InfyTrackerException("Service.Adder_Optional_Not_found");
     if(activityDTO.getEndDate().isBefore(activityDTO.getStateDate())) throw new InfyTrackerException("Service.EndDate_Needs_To_Be_After_Start_Date");
    Activity activity = new Activity(activityDTO);
    
    if(employee.getActivities()==null) {// no activity provided to employee we need to create list to avoid null pointer Exception
         List<Activity> activities = new ArrayList<>(); 
//         employee.setActivities(activities);
     }
        activity.setRemarks("");
         activity.setNoOfTimes("0");
         activity.setActivityStatus(ActivityStatus.Not_Yet_Started);
         employee.getActivities().add(activity); 
         Employee e= userRepository.save(employee);                                                              // getting last activity Id 
         String resp = " Activity added succesfully to the employee with activity Id : " + e.getActivities().get(e.getActivities().size()-1).getActivityId();
        
        return resp;
    }
    @Override
    public String changeRole(EmployeeDTO employeeDetails) throws InfyTrackerException {
        // TODO Auto-generated method stub
        
        Optional<Employee> optional = userRepository.findById(employeeDetails.getEmployeeId());
        Employee employee = optional.orElseThrow(()->new InfyTrackerException("Service.User_UnAvailable"));
        if(employeeDetails.getRole()==null) throw new InfyTrackerException("employee.role.absent");
        if(employeeDetails.getRole().equals(employee.getRole())) throw new InfyTrackerException("Service.Employee_Already_In_"+employee.getRole()+"_Role"); 
        employee.setRole(employeeDetails.getRole());
        userRepository.save(employee);
        String ret = "Employee_Role_Change_Success";
        return ret;
    }
    @Override
    public String removeActivity(Integer activityId, Integer empId) throws InfyTrackerException {
             Optional<Employee> optional = userRepository.findById(empId);
        // validate Emplid
//        validate activity Id
        Employee employee = optional.orElseThrow(()-> new InfyTrackerException("Service.User_UnAvailable"));
        
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);
        Activity activity  = optionalActivity.orElseThrow(()-> new InfyTrackerException("Service.Activity_Not_Found" ));
//         manager can remove anyone task that is assigned by anyone
//        if(!employee.getUsername().equals(activity.getAssignedBy())) {
//             throw new InfyTrackerException("Service.Activity_Can't_Remove_Not_Correct_Assignee");
//        }
        if(activity.getActivityStatus().equals(ActivityStatus.Remove)) throw new InfyTrackerException("Service.Activity_Already_In_Remove_State");
        activity.setActivityStatus(ActivityStatus.Remove);
        activityRepository.save(activity);
        
        return "Service.Activity_Removed_Successfull";
        
    }

    
    @Override
    public String reassignTask(Integer activityId, Integer newEmpId, Integer empId) throws InfyTrackerException {
//        System.out.println("reassign line 91");
        Optional<Employee> optional = userRepository.findById(newEmpId);
//        System.out.println("reassign line 93");
        Employee employee = optional.orElseThrow(()-> new InfyTrackerException("Service.Reassign_New_User_UnAvailable"));
//        System.out.println("reassign line 95");
        if(employee.getStatus().equals(Status.Deactive)) throw new InfyTrackerException("Service.Assignee_In_Deactivate_State");
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);
        Activity activity  = optionalActivity.orElseThrow(()-> new InfyTrackerException("Service.Activity_Not_Found" ));
//        System.out.println("reassign line 98");
        Optional<Employee> optionalPreviousAssignee = userRepository.findById(empId);
        Employee previousAssignee = optionalPreviousAssignee.orElseThrow(()-> new InfyTrackerException("Service.Can't_Found_Prevoius_Assignee"));
//        System.out.println("reassign line 101");
        int index = 0;
//        System.out.println("reassign line 103");
        List<Activity> Activities = previousAssignee.getActivities();
//        System.out.println("reassign line 105");
        boolean flag=false;
        
//        if(flag) {
//        previousAssignee.getActivities().remove(index); //  removing the task from the previous employee
//        }
        
        employee.getActivities().add(activity); // reassign the task to the new user
        
//        userRepository.save(previousAssignee);
        userRepository.save(employee);  // commiting the changes
        return "Service.Activity_Reassign_Success"; // returning the message once the process done
        

        
    }
    @Override
    public String deactivateEmployee(EmployeeDTO employeeDTO) throws InfyTrackerException {
        if(employeeDTO.getEmployeeId()==null) {
            throw new InfyTrackerException("Service.Provide_EmployeeId");
        }
        Optional<Employee>    emp= userRepository.findById(employeeDTO.getEmployeeId());
        Employee employee=  emp.orElseThrow(()-> new InfyTrackerException("Service.User_UnAvailable"));
        String msg;
        if(employeeDTO.getStatus()!=null) {// cant deactivate if the employee is already deactivated
            if(employee.getStatus().equals(Status.Deactive) && employeeDTO.getStatus().equals(Status.Deactive)) {
                throw new InfyTrackerException("Servcie.Employee_Already_Deactivate");
            }  // can't Activate an Employee if he is already activated
            if(employee.getStatus().equals(Status.Active) && employeeDTO.getStatus().equals(Status.Active)) {
                throw new InfyTrackerException("Servcie.Employee_Already_Activate");
            }
             msg = "Service.User_Status_Updated_Success";
             System.out.println(employee.getStatus() + " <----" + employeeDTO.getStatus());
            employee.setStatus(employeeDTO.getStatus());
            userRepository.save(employee);
        }else {
            throw new InfyTrackerException("Service.Provide_User_Status");
        }
       
        return msg;
    }
    @Override
    public List<EmployeeDTO> getAllTeamMemberTask() throws InfyTrackerException {
        List<Employee> employees = userRepository.findAll();
        List<EmployeeDTO> result = new ArrayList<>();
                for(Employee employee : employees) {
                    EmployeeDTO emp = new EmployeeDTO().getEmployeeOnly(employee);
                    for(Activity activity:employee.getActivities()) {
                        ActivityDTO activityDTO = new ActivityDTO(activity );
                        emp.setActivities(new ArrayList<>()); // every time initilize new list
                        emp.getActivities().add(activityDTO);
                        result.add(emp);
                    }
                    
                }
        return result;
    }
    @Override
    public List<EmployeeDTO> getAllTeamMemberTaskByStatus(ActivityStatus activityStatus) throws InfyTrackerException {
        List<Employee> employees = userRepository.findAll();
        
        List<EmployeeDTO> result = new ArrayList<>();
                for(Employee employee : employees) {
                    EmployeeDTO emp = new EmployeeDTO().getEmployeeOnly(employee);
                    for(Activity activity:employee.getActivities()) {
                        if(activityStatus.equals(activity.getActivityStatus())) {
                            ActivityDTO activityDTO = new ActivityDTO(activity );
                            emp.setActivities(new ArrayList<>()); // every time initilize new list
                            emp.getActivities().add(activityDTO);
                            result.add(emp);
                        }    
                    }
                    
                }
        return result;
        
    }

    @Override
    public List<EmployeeDTO> getAllTeamMemberTaskByStatusById(Integer employeeId, ActivityStatus activityStatus)
            throws InfyTrackerException {
        Optional<Employee>    optional= userRepository.findById(employeeId);
        Employee employee=  optional.orElseThrow(()-> new InfyTrackerException("Service.User_UnAvailable"));
        
        if(employee.getActivities()==null || employee.getActivities().isEmpty()) {// if no activity found for given employee
            throw new InfyTrackerException("Service.No_Activity_"+activityStatus);
            
        }
        List<EmployeeDTO> result = new ArrayList<>();
                
        for(Activity activity:employee.getActivities()) {
            EmployeeDTO emp = new EmployeeDTO().getEmployeeOnly(employee);// initilize new object when you adding activity
            if(activityStatus.equals(activity.getActivityStatus())) {
                ActivityDTO activityDTO = new ActivityDTO(activity );
                emp.setActivities(new ArrayList<>()); // every time initilize new list
                emp.getActivities().add(activityDTO);
                result.add(emp);
            }    
        }        
                
        return result;
        
    }
    @Override
    public List<EmployeeDTO> getAlluserDetails() throws InfyTrackerException {
        List<Employee> employees = userRepository.findAll();
        List<EmployeeDTO> result = new ArrayList<>();
        for(Employee employee:employees) {
            EmployeeDTO employeeDTO = new EmployeeDTO().getEmployeeOnly(employee);
            result.add(employeeDTO);
        }
        return result;
    }
    @Override
    public EmployeeDTO getuserDetailsById(Integer empId) throws InfyTrackerException {
        // 
        return employeeService.getEmployee(empId);
    }
    @Override
    public EmployeeDTO getTaskDetailsByActivityId(Integer activityId) throws InfyTrackerException {
        Optional<Activity> opt = activityRepository.findById(activityId);
        Activity activity = opt.orElseThrow(()->new InfyTrackerException("Service.Activity_Not_Found"));
        Employee emp = userRepository.getEmployeeByActivityId(activityId);
        boolean flag=true;
        EmployeeDTO empDTO = new EmployeeDTO().getEmployeeOnly(emp);
        
        for(Activity activity1:emp.getActivities()) {
            if(activity1.getActivityId().equals(activityId)) {
                List<ActivityDTO> ac = new ArrayList<>();
                ac.add(new ActivityDTO(activity1));
                empDTO.setActivities(ac);
                flag=false;
            }
            
        }
        return empDTO;
    }

    
    
}
