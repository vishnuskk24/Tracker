package com.tracker.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tracker.entity.Activity;
import com.tracker.entity.ActivityStatus;

public interface ActivityRepository extends CrudRepository<Activity, Integer>{

//    List<Activity> getUserActivityByEmployeeIdAndActivityStatus(Integer empId, ActivityStatus activityStatus);

}