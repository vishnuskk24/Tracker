package com.eng.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.eng.entity.Activity;
import com.eng.entity.ActivityStatus;

public class ActivityDTO {
//  json - > java object
     // need to perform data validation
    private Integer activityId;
    
    @NotNull(message = "{activity.activityName.absent}")
    @NotBlank(message = "{activity.activityName.blank}")
//    @NotBlank(message = "{activity.activityName.blank}")
    private String activityName;
    
    @NotNull(message = "{activity.startDate.absent}")
//    @NotBlank(message = "{activity.startDate.blank}")
//    @NotEmpty(message = "{activity.startDate.blank}")
    private LocalDate stateDate;
    
    @NotNull(message = "{activity.endDate.absent}")
//    @NotBlank(message = "{activity.endDate.blank}")
//    @NotEmpty(message = "{activity.endDate.blank}")
    private LocalDate endDate;
    
//    @NotNull(message = "{activity.endDate.absent}")
//    @NotBlank(message = "{activity.assignedBy.blank}")
//    @NotEmpty(message = "{activity.assignedBy.blank}")
    private String assignedBy;
    private String noOfTimes;
    private String remarks;
    private ActivityStatus activityStatus;
    
    public String getActivityName() {
        return activityName;
    }
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
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
    
    // entity to model 
//    
//    Activity to ActivityDTO
    
    public ActivityDTO() {
        // TODO Auto-generated constructor stub
    }
    
public ActivityDTO(Activity activity) {
        
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
@Override
public String toString() {
    return "ActivityDTO [activityId=" + activityId + ", activityName=" + activityName + ", stateDate=" + stateDate
            + ", endDate=" + endDate + ", assignedBy=" + assignedBy + ", noOfTimes=" + noOfTimes + ", remarks="
            + remarks + ", activityStatus=" + activityStatus + "]";
}
}
