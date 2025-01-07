package com.tracker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tracker.entity.ActivityStatus;
import com.tracker.entity.Employee;

public interface EmployeeDAO extends CrudRepository<Employee, Integer>{
    @Query("select e from Employee e where e.status='Deactive'")
    public List<Employee> findAllEmployee();

//    @Query("select c from Employee c where c.employeeId=?1" )
//    public List<Employee> getUserActivityByUserId(Integer e);
    

}