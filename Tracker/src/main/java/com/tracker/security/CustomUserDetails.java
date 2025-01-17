package com.tracker.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tracker.dto.EmployeeDTO;
import com.tracker.entity.Role;
import com.tracker.entity.Status;

public class CustomUserDetails implements UserDetails {

	private Integer employeeId;
	private String employeeName;
	private String username;
	private String mailId;
	private Status status;
	private String password;
	private Role role;
	private List<GrantedAuthority> authorites; // providing the list of authorities in the project while initializing
												// the object

	// overiding the methods for our need
	@Override
	public String getPassword() {
		System.out.println(" get passsword in user details in user details " + this.password);

		return this.password;
	}

	@Override
	public String getUsername() {

		System.out.println("get user name 1 in  insode user details");
		System.out.println(toString());
		return this.username;

	}
	@Override
	public boolean isEnabled() {
		System.out.println("is enabled  insode user details");
		// TODO Auto-generated method stub
		return this.status.equals(Status.Active);
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorites;
	}
	
//	Constructor to create USerDetailService Object From DTO class 
//	 =============================
	public CustomUserDetails(EmployeeDTO user) {

		System.out.println("inside the constructor My user details +++++++++");
		this.employeeId = user.getEmployeeId();
		this.employeeName = user.getEmployeeName();
		this.mailId = user.getMailId();
		this.username = user.getUsername();
		this.mailId = user.getMailId();
		this.status = user.getStatus();
		this.password = user.getPassword();
		this.role = user.getRole();

		List<GrantedAuthority> auth = new ArrayList<>();
		auth.add(new SimpleGrantedAuthority(user.getRole().toString()));
		this.authorites = auth;

		System.out.println("in MyUSerDetails  ->  line 47");
		System.out.println(this.toString());

	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	

	

	
}
