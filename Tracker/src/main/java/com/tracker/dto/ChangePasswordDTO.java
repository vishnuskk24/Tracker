package com.tracker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ChangePasswordDTO {
    
//    public static final String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&=+//.])(?=.*[0-9]){8,}$";
    public static final String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])(?=.*[0-9]).{8,}$)";
    
//    @NotNull(message = "{employee.employeeid.absent}")
//    @Min(value=1,message = "{employee.employeeid.invalid}")
    private Integer employeeId;
    
    @NotNull(message = "{employee.curentpassword.blank}")
//    @Pattern(regexp = "",message ="" )
    private String currentPassword;
    
    @NotNull(message = "{employee.newpassword.blank}")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}$)",message ="{employee.newpassword.invalid}" )
    private String newPassword;
    
    
    public Integer getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
    public String getCurrentPassword() {
        return currentPassword;
    }
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
}
