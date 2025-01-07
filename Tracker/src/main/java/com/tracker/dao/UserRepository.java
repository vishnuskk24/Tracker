package com.tracker.dao;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tracker.entity.ActivityStatus;
import com.tracker.entity.Employee;

public interface UserRepository extends JpaRepository<Employee, Integer>  {

//    @Query("Select e from Employee e where e.employeeId=?1 and e.activities.activityStatus=?2")
    public Employee getEmployeeByEmployeeId(Integer employeeId);
//
//    public Employee getAllTaskAccordingToDate(Integer employeeId, LocalDate stateDate, LocalDate endDate);

    public Employee findByUsername(String username);
    
    @Query("Select e from Employee e join e.activities a where a.activityId=?1")
    public Employee getEmployeeByActivityId(Integer activityId);

    
}