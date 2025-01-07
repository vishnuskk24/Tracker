package com.tracker.entity;

import java.util.ArrayList;
import java.util.List;

import com.tracker.dto.ActivityDTO;
import com.tracker.dto.EmployeeDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Employee  {

    
    @Id
    private Integer employeeId;
    private String employeeName;
    private String username;
    private String mailId;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;  
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "e_id")
    private List<Activity> activities;
    
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

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
    
    // model to entity 
//    CustomerDTO to Customer
    // ActivityDTO to Activity

    public Employee(EmployeeDTO employeeDTO) {
        
        this.employeeId = employeeDTO.getEmployeeId();
        this.employeeName = employeeDTO.getEmployeeName();
        this.username = employeeDTO.getUsername();
        this.mailId = employeeDTO.getMailId();
        this.status = employeeDTO.getStatus();
//        this.password = employeeDTO.getPassword();
        this.role = employeeDTO.getRole();
        List<Activity>  activites= null;
        if(employeeDTO.getActivities()!=null) {
            activites=new ArrayList<>();
            for( ActivityDTO activity: employeeDTO.getActivities()) {
                Activity act  =  new Activity(activity);
                activites.add(act);
            }
            this.setActivities(activites);
            
            
        }
    }
    @Override
    public String toString() {
        return "Employee [employeeId=" + employeeId + ", employeeName=" + employeeName + ", username=" + username
                + ", mailId=" + mailId + ", status=" + status + ", password=" + password + ", role=" + role
                + ", activities=" + activities + "]";
    }

    
    public EmployeeDTO getEmployeeOnly(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setEmployeeName(employee.getEmployeeName());
        employeeDTO.setMailId(employee.getMailId());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setStatus(employee.getStatus());
        employeeDTO.setUsername(employee.getUsername());
        return employeeDTO;
        
    }
    public Employee() {
        // TODO Auto-generated constructor stub
    }
}

