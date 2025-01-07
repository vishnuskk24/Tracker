//package com.eng.security;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.eng.dto.EmployeeDTO;
//import com.eng.entity.Role;
//import com.eng.entity.Status;
//
//public class MyUserDetails  implements UserDetails{
//
//    private Integer employeeId;
//    private String employeeName;
//    private String username;
//    private String mailId;
//    private Status status;
//    private String password;
//    private Role role;  
//    private List<GrantedAuthority> authorites;
//    
//    @Override
//    public String toString() {
//        return "MyUserDetails [employeeId=" + employeeId + ", employeeName=" + employeeName + ", username=" + username
//                + ", mailId=" + mailId + ", status=" + status + ", password=" + password + ", role=" + role
//                + ", authorites=" + authorites + "]";
//    }
//    public MyUserDetails(EmployeeDTO user) {
//        
//        System.out.println("inside the constructor My user details +++++++++");
//        this.employeeId=user.getEmployeeId();
//        this.employeeName=user.getEmployeeName();
//        this.mailId=user.getMailId();
//        this.username=user.getUsername();
//        this.mailId=user.getMailId();
//        this.status=user.getStatus();
//        this.password=user.getPassword();
//        this.role=user.getRole();
//        List<GrantedAuthority> auth = new ArrayList<>();
//        auth.add(new SimpleGrantedAuthority(role.toString()));
//        this.authorites=auth;
//        
//        System.out.println("in MyUSerDetails  ->  line 47" );
//        System.out.println(this.toString());
//        
//    }
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        
//        System.out.println("getting grant authorities in user details");
////        List<GrantedAuthority> auth = new ArrayList<>();
////        auth.add(new SimpleGrantedAuthority(this.role.toString()));
//        return authorites;
//    }
//
//    @Override
//    public String getPassword() {
//        System.out.println(" get passsword in user details in user details ");
//        // TODO Auto-generated method stub
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        // TODO Auto-generated method stub
//        System.out.println("get user name 1 in  insode user details");
//        System.out.println(toString());
//        return username;
////        return "vishnukumar.m01";
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        // TODO Auto-generated method stub
//        System.out.println("is acc not exp  insode user details");
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        System.out.println("is acc not lock  insode user details");
//        // TODO Auto-generated method stub
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        // TODO Auto-generated method stub
//        System.out.println("is credential non exp   insode user details");
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        System.out.println("is enabled  insode user details");
//        // TODO Auto-generated method stub
//        return this.status.equals(Status.Active);
//    }
//
//}
//package com;
package com.tracker.unwnted;




