//package com.eng.dto;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.eng.entity.Employee;
//import com.eng.entity.Status;
//
//public class EmployeePrincipal implements UserDetails {
//
//    private  EmployeeDTO employee;
//    
//    public EmployeePrincipal() {
//        
//    }
//
//    public EmployeePrincipal(EmployeeDTO e) {
//        super();
//        this.employee=e;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // TODO Auto-generated method stub
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("Manager"));
//        authorities.add(new SimpleGrantedAuthority("User"));
//        authorities.add(new SimpleGrantedAuthority("Lead"));
//        
//        System.out.println("authorities ++++++++++++++++");
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        // TODO Auto-generated method stub
//        return employee.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        // TODO Auto-generated method stub
//        return employee.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        // TODO Auto-generated method stub
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        // TODO Auto-generated method stub
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        // TODO Auto-generated method stub
//        return employee.getStatus().equals(Status.Active);
//    }
//}
//package com.eng.dto;
//package com;




