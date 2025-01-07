package com.tracker.entity;

import java.time.LocalDate;
import java.util.List;

import com.tracker.dto.ActivityDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Activity {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityId;
    private String activityName;
    @Column(name = "start_date")
    private LocalDate stateDate;
    private LocalDate endDate;
    private String assignedBy;
    @Column(name = "no_of_times_updated")
    private String noOfTimes;
    private String remarks;
    @Enumerated(EnumType.STRING)   
    private ActivityStatus activityStatus;
    
    
    
    public Integer getActivityId() {
        return activityId;
    }



    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }



    public LocalDate getStateDate() {
        return stateDate;
    }



    public void setStateDate(LocalDate stateDate) {
        this.stateDate = stateDate;
    }



    public LocalDate getEndDate() {
        return endDate;
    }



    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }



    public String getAssignedBy() {
        return assignedBy;
    }



    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }



    public String getNoOfTimes() {
        return noOfTimes;
    }



    public void setNoOfTimes(String noOfTimes) {
        this.noOfTimes = noOfTimes;
    }



    public String getRemarks() {
        return remarks;
    }



    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }



    public ActivityStatus getActivityStatus() {
        return activityStatus;
    }



    public void setActivityStatus(ActivityStatus activityStatus) {
        this.activityStatus = activityStatus;
    }






    @Override
    public String toString() {
        return "Activity [activityId=" + activityId + ", activityName=" + activityName + ", stateDate=" + stateDate
                + ", endDate=" + endDate + ", assignedBy=" + assignedBy + ", noOfTimes=" + noOfTimes + ", remarks="
                + remarks + ", activityStatus=" + activityStatus + "]";
    }



    public Activity(ActivityDTO activity) {
        
        this.activityId = activity.getActivityId();
        this.activityName=activity.getActivityName();
        this.stateDate = activity.getStateDate() ;
        this.endDate = activity.getEndDate() ;
        this.assignedBy = activity.getAssignedBy() ;
        this.noOfTimes = activity.getNoOfTimes();
        this.remarks = activity.getRemarks() ;
        this.activityStatus = activity.getActivityStatus() ;
        // TODO Auto-generated constructor stub
    }

    public String getActivityName() {
        return activityName;
    }



    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }



    public Activity() {
        // TODO Auto-generated constructor stub
    }
    
    
}




//create table activity(
//        activity_id int auto_increment primary key,
//        start_date date,
//        end_date date,
//        assigned_by varchar(20),
//        no_of_times_updated int ,
//        remarks varchar(20),
//        activity_status varchar(25),
//        emp_id int,
//        FOREIGN KEY (emp_id) REFERENCES employee(employee_id)
//    );