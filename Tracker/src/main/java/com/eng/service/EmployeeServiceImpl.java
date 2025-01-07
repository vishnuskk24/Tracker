package com.eng.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eng.dao.EmployeeDAO;
import com.eng.dao.UserRepository;
import com.eng.dto.ActivityDTO;
import com.eng.dto.EmployeeDTO;
import com.eng.entity.ActivityStatus;
import com.eng.entity.Employee;
import com.eng.entity.Status;
import com.eng.exception.InfyTrackerException;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{
    
    @Autowired
    UserRepository employeeDAO;
//    
//    @Autowired
//    PasswordEncoder  encoder;
    
    @Autowired
    ActivityServiceImpl activityService;

    @Override
    public EmployeeDTO getEmployee(Integer empId) throws InfyTrackerException {
        
    Optional<Employee>    emp= employeeDAO.findById(empId);
    System.out.println(emp.isEmpty() + "  " + empId);
    Employee employee=  emp.orElseThrow(()-> new InfyTrackerException("Service.User_UnAvailable"));
    
    EmployeeDTO employeeDTO = new EmployeeDTO(employee) ;
    return employeeDTO;
        
        
        
//        return null;
    }

    @Override
    public String addEmployee(EmployeeDTO employee) throws InfyTrackerException {
        // TODO Auto-generated method stub
         Optional<Employee>    emp= employeeDAO.findById(employee.getEmployeeId());
         if(emp.isPresent()) {
             throw new InfyTrackerException("Service.User_Already_Exist");
         }
        Employee e = new Employee(employee);
//        e.setPassword(encoder.encode(employee.getPassword()));
        employeeDAO.save(e);
        String msg = "Service.User_Added_Success";
        return msg;
    }

    @Override
    public String updateEmployee(EmployeeDTO employeeDTO) throws InfyTrackerException {
        // TODO Auto-generated method stub
        boolean flag = true;
        if(employeeDTO.getEmployeeId()==null) {
            throw new InfyTrackerException("Service.Provide_EmployeeId");
        }
        Optional<Employee>    emp= employeeDAO.findById(employeeDTO.getEmployeeId());
        Employee employee=  emp.orElseThrow(()-> new InfyTrackerException("Service.Customer_UnAvailable"));
        if(employeeDTO.getStatus()!=null) {
            if(employeeDTO.getStatus().equals(Status.Deactive)) {
                throw new InfyTrackerException("Servcie.Employee_Deactivate");
            }
            employee.setStatus(employeeDTO.getStatus());
            flag=false;
        }
        if(employeeDTO.getEmployeeName()!=null) {
            
            employee.setEmployeeName(employeeDTO.getEmployeeName());
            flag=false;
        }
        if(employeeDTO.getMailId()!=null) {
            
            employee.setMailId (employeeDTO.getMailId());
            flag=false;
        }
       
        if(employeeDTO.getRole()!=null) {
            
            employee.setRole(employeeDTO.getRole());
            flag=false;
        }
        if(employeeDTO.getUsername()!=null) {
            employee.setUsername(employeeDTO.getUsername());
            flag=false;
        }
        
       
        if(flag) {
            throw new InfyTrackerException("Service.No_Data_Available_To_Update");
        }
//        employeeDAO.save(employee);
        String msg = "Service.User_Updated_Success";
        return msg;
        
        
    }
    @Override
    public String deactivateEmployee(EmployeeDTO employeeDTO) throws InfyTrackerException {
        // TODO Auto-generated method stub
        
        if(employeeDTO.getEmployeeId()==null) {
            throw new InfyTrackerException("Service.Provide_EmployeeId");
        }
        Optional<Employee>    emp= employeeDAO.findById(employeeDTO.getEmployeeId());
        Employee employee=  emp.orElseThrow(()-> new InfyTrackerException("Service.User_UnAvailable"));
        String msg;
        if(employeeDTO.getStatus()!=null) {
            if(employee.getStatus().equals(Status.Deactive) && employeeDTO.getStatus().equals(Status.Deactive)) {
                throw new InfyTrackerException("Servcie.Employee_Already_Deactivate");
            }
            if(employee.getStatus().equals(Status.Active) && employeeDTO.getStatus().equals(Status.Active)) {
                throw new InfyTrackerException("Servcie.Employee_Already_Activate");
            }
             msg = "Service.User_Status_Updated_Success";
             System.out.println(employee.getStatus() + " <----" + employeeDTO.getStatus());
            employee.setStatus(employeeDTO.getStatus());
            employeeDAO.save(employee);
        }else {
            throw new InfyTrackerException("Service.Provide_User_Status");
        }
       
        return msg;
        
    }

    @Override
    public List<EmployeeDTO> getAllEmployee() throws InfyTrackerException {
        // TODO Auto-generated method stub
        List<EmployeeDTO> ret = new ArrayList<>();
        List<Employee> employees = employeeDAO.findAll();
        if(employees == null || employees.size()==0) {
            throw new InfyTrackerException("Service.Employee_Not_Available");
        }
        for(Employee e : employees) {
            ret.add(new EmployeeDTO(e));
            
        }
        return ret;
    }

//    @Override
//    public EmployeeDTO getMyDetail(EmployeeDTO employee) throws InfyTrackerException {
//        // TODO Auto-generated method stub
//        
//        if(employee==null) {
//            throw new InfyTrackerException("Service.Provide_Employee_Id");
//            
//        }
//        EmployeeDTO employeeDTO = this.getEmployee(employee.getEmployeeId());
//        return employeeDTO;
//    }
//
//    @Override
//    public List<ActivityDTO> getMyTaskByStatus(EmployeeDTO employee, ActivityStatus status) throws Exception {
//        // TODO Auto-generated method stub
//        
//        
//        
//        return null;
//    }
    
}