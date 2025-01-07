package com.eng.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.eng.entity.Activity;
import com.eng.entity.ActivityStatus;

public interface ActivityRepository extends CrudRepository<Activity, Integer>{

//    List<Activity> getUserActivityByEmployeeIdAndActivityStatus(Integer empId, ActivityStatus activityStatus);

}