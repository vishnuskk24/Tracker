package com.tracker.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tracker.dao.ActivityRepository;
import com.tracker.dao.EmployeeDAO;
import com.tracker.dto.ActivityDTO;
import com.tracker.dto.EmployeeDTO;
import com.tracker.entity.Activity;
import com.tracker.entity.ActivityStatus;
import com.tracker.entity.Employee;
import com.tracker.entity.Status;
import com.tracker.exception.InfyTrackerException;

@Service
@Transactional
public class ActivityServiceImpl implements ActivityService{
    
    @Autowired
    ActivityRepository activityDAO;
    
    @Autowired
    EmployeeDAO employeeDAO;
    

    @Override
    public String addActivity(Integer employeeId,ActivityDTO activityDTO) throws InfyTrackerException {
        
     Optional<Employee> optional=employeeDAO.findById(employeeId);
     Employee employee = optional.orElseThrow(()->new InfyTrackerException("Service.Customer_UnAvailable"));
     
     if(employee.getStatus().equals(Status.Deactive)) {
         throw new InfyTrackerException("Service.Employee_Status_Deactivate_Addactivity");
     }
     
     Activity activity = new Activity(activityDTO);
     if(employee.getActivities()==null) {// no activity provided to employee
         List<Activity> activities = new ArrayList<>();
         
         employee.setActivities(activities);
     }
         employee.getActivities().add(activity); 
         Employee e= employeeDAO.save(employee);                                                              // getting last activity Id 
         String resp = " Activity added succesfully to the employee with activity Id : " + e.getActivities().get(e.getActivities().size()-1).getActivityId();
        
        return resp;
    }

    @Override
    public ActivityDTO getActivity(Integer activityId) throws InfyTrackerException {
        // TODO Auto-generated method stub
        Optional<Activity> optional =  activityDAO.findById(activityId);
        Activity activity = optional.orElseThrow(()->new InfyTrackerException("Service.Activity_Not_Found"));
        ActivityDTO activityDTO = new ActivityDTO(activity);
        
        
        
        return activityDTO;
    }

    @Override
    public List<EmployeeDTO> getAllActivity() throws InfyTrackerException {
        // getting all user incomplete activity
        Iterable<Employee> employees= employeeDAO.findAll();
//        Iterator i =employees.iterator();
        List<EmployeeDTO> employeesDTOA = new ArrayList<>();
        employees.forEach(employee -> {
            EmployeeDTO employeeDTO = new EmployeeDTO(employee);
            employeesDTOA.add(employeeDTO);
        });
        
            if(employeesDTOA.isEmpty()) {
                throw new InfyTrackerException("Servie.No_Employees_Available");
            }
        
            return employeesDTOA;
        
        
//        employeeDAO.getUserActivityByUserId()
        
        
    }

    @Override
    public EmployeeDTO getUserAllInCompleteActivity(Integer employeeId) throws InfyTrackerException {
        // TODO Auto-generated method stub
        Optional<Employee> optional =  employeeDAO.findById(employeeId);
//        System.out.println(emp.toString());
        Employee employee = optional.orElseThrow(()->new InfyTrackerException("Service.Customer_UnAvailable"));
        if(employee.getActivities()==null || employee.getActivities().size()==0) {
            throw new InfyTrackerException("Service.No_Activity_Allocated_To_The_Employee");
            
        }
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setEmployeeName(employee.getEmployeeName());
        employeeDTO.setMailId(employee.getMailId());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setStatus(employee.getStatus());
        employeeDTO.setUsername(employee.getUsername());
        List<ActivityDTO> act = new ArrayList<>();
        for(Activity ac : employee.getActivities()) {
            
            if(ac.getActivityStatus().equals(ActivityStatus.Completed)) {
                continue;
            }
            ActivityDTO activityDTO = new ActivityDTO(ac);
            act.add(activityDTO);
            System.out.println(ac.toString());
//            System.out.println(emp.toString());
            
            
        }
        employeeDTO.setActivities(act);
        return employeeDTO;
    }
    
    @Override
    public EmployeeDTO getActivityByStatus(Integer employeeId, ActivityDTO activityDTO) throws InfyTrackerException {
        // TODO Auto-generated method stub
        Optional<Employee> optional =  employeeDAO.findById(employeeId);
//        System.out.println(emp.toString());
        Employee employee = optional.orElseThrow(()->new InfyTrackerException("Service.Customer_UnAvailable"));
        if(employee.getActivities()==null || employee.getActivities().size()==0) {
            throw new InfyTrackerException("Service.No_Activity_Allocated_To_The_Employee");
            
        }
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setEmployeeName(employee.getEmployeeName());
        employeeDTO.setMailId(employee.getMailId());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setStatus(employee.getStatus());
        employeeDTO.setUsername(employee.getUsername());
        List<ActivityDTO> act = new ArrayList<>();
        for(Activity ac : employee.getActivities()) {
            
            if(!ac.getActivityStatus().equals(activityDTO.getActivityStatus())) {
                continue;
            }
            ActivityDTO activity = new ActivityDTO(ac);
            act.add(activity);
            System.out.println(ac.toString());
//            System.out.println(emp.toString());
            
            
        }
        
        if(act.size()==0) {
            String msg="Service.No_Activities_"+activityDTO.getActivityStatus() ;
//            Service.No_Activities_In_Progress
//            Service.No_Activities_Completed
//            Service.No_Activities_Not_Yet_Started
            throw new InfyTrackerException(msg);
        }
        employeeDTO.setActivities(act);
        return employeeDTO;
        
        
        
        
    }

    @Override
    public String updateActivity(ActivityDTO activityDTO) throws InfyTrackerException {
        
        // TODO Auto-generated method stub
        
        return null;
    }

//    ===============================  adding new
    

    

    

}