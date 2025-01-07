package com.eng.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.eng.entity.Activity;
import com.eng.entity.Employee;
import com.eng.entity.Role;
import com.eng.entity.Status;



public class EmployeeDTO{
    
    @NotNull(message = "{employee.employeeid.absent}")
    @Min(value=1,message = "{employee.employeeid.invalid}")
    private Integer employeeId;
    
    @NotNull(message = "{employee.employeename.absent}")
    @NotBlank(message = "{employee.employeename.blank}")
    private String employeeName;
    
    @NotNull(message = "{employee.username.absent}")
    @NotBlank(message = "{employee.username.blank}")
    private String username;
    
    @NotNull(message = "{employee.email.absent}")
    @Email(message ="{employee.email.invalid}")
    private String mailId;
    
    private Status status;
    @NotNull(message = "{employee.passsword.absent}")
    @NotBlank(message = "{employee.password.blank}")
    private String password;
    
    @NotNull(message = "{employee.role.absent}")
//    @NotBlank(message = "{employee.role.blank}")
    private Role role;  
    
    
    private List<ActivityDTO> activities;
    
    
    public Integer getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
    public String getEmployeeName() {
        return employeeName;
    }
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getMailId() {
        return mailId;
    }
    public void setMailId(String mailId) {
        this.mailId = mailId;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public List<ActivityDTO> getActivities() {
        return activities;
    }
    public void setActivities(List<ActivityDTO> activities) {
        this.activities = activities;
    }
    
//    Entity to Model
    
     public EmployeeDTO(Employee employee) {
    
    this.employeeId = employee.getEmployeeId();
    this.employeeName = employee.getEmployeeName();
    this.username = employee.getUsername();
    this.mailId = employee.getMailId();
    this.status = employee.getStatus();
    System.out.println("while transfer ing the entity to model setting password is null");
//    this.password = employee.getPassword();
    this.role = employee.getRole();
    List<ActivityDTO>  activites= null;
    if(employee.getActivities()!=null) {
        activites=new ArrayList<>();
        for( Activity activity: employee.getActivities()) {
            ActivityDTO act  =  new ActivityDTO(activity);
            activites.add(act);
        }
        this.setActivities(activites);
        
        
    }
}
     public EmployeeDTO() {
        // TODO Auto-generated constructor stub
    }
    
     public EmployeeDTO getEmployeeOnly(Employee employeeDTO) {
//         transfering values from employee enitity to employee DTO
            EmployeeDTO employee = new EmployeeDTO();
            employee.setEmployeeId(employeeDTO.getEmployeeId());
            employee.setEmployeeName(employeeDTO.getEmployeeName());
            employee.setMailId(employeeDTO.getMailId());
            employee.setRole(employeeDTO.getRole());
            employee.setStatus(employeeDTO.getStatus());
            employee.setUsername(employeeDTO.getUsername());
//            employee.setPassword(employeeDTO.);
            return employee;
            
        }
    @Override
    public String toString() {
        return "EmployeeDTO [employeeId=" + employeeId + ", employeeName=" + employeeName + ", username=" + username
                + ", mailId=" + mailId + ", status=" + status + ", password=" + password + ", role=" + role
                + ", activities=" + activities + "]";
    }
    
}