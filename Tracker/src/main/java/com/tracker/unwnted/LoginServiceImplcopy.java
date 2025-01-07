//package com.eng.service;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.function.Function;
//
//import javax.crypto.SecretKey;
////import javax.servlet.ServletException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
////import org.springframework.security.core.userdetails.UserDetails;
////import org.springframework.security.core.userdetails.UsernameNotFoundException;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import com.eng.dto.EmployeePrincipal;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.eng.dao.LoginRepository;
//import com.eng.dto.EmployeeDTO;
//
//import com.eng.entity.Employee;
//import com.eng.entity.Role;
//import com.eng.entity.Status;
//import com.eng.exception.InfyTrackerException;
//import com.eng.security.MyUserDetails;
//import com.eng.utility.ErrorInfo;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//
//
//@RestController
//@Service
//@Transactional
//public class LoginServiceImpl implements LoginService {
//
////    @Autowired
////    BCryptPasswordEncoder encoder;
//    @Autowired
//    private Environment env;
//    
//    @Autowired
//    LoginRepository loginRepository;
//    
//    LoginServiceImpl(){
//        
//        
//    }
//    
//    
//    public String authenticateUser(EmployeeDTO employeeDetails) throws InfyTrackerException {
//        
////        System.out.println("inside authenica");
//        Optional<Employee> optional= loginRepository.findByUsername(employeeDetails.getUsername().toLowerCase());
//        System.out.println("line 32");
//        Employee employee = optional.orElseThrow(()->  new InfyTrackerException( "Service.Invalid_Data"));
//        System.out.println("line 34");
//        EmployeeDTO employeeDTO  = new EmployeeDTO(employee);
//        System.out.println("line 36");
//        
//        if(!(employeeDetails.getPassword().equals((employee.getPassword())))) {
////        if(!(employeeDetails.getPassword().equals(encoder.encode(employee.getPassword())))) {
//            throw new InfyTrackerException("Service.Invalid_Data");
//        }
//        else {
//                            System.out.println(" login successful");                                                //             User Valid
//                                                                    //             need to check the user status
//            if(employeeDTO.getStatus().equals(Status.Deactive)) {
//                throw new InfyTrackerException("Service.User_Deactivate");
//            }
//            System.out.println("secret key = " + env.getProperty("Secret_Key"));
//            System.out.println("going to generate token ");
//            String token = generateToken(loadMap(employeeDTO), employeeDTO.getUsername());
//            
////            String msg =""+ employeeDTO.getRole()+","+employee.getEmployeeId()+","+token;
//            String msg = token;
//
//            
//            
//            return msg;
//
//        }
//    
//    }
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // TODO Auto-generated method stub
//        
//        System.out.println("Getting the user details from the database in the user service ++++++++++ ");
//        Optional<Employee> optional= loginRepository.findByUsername(username);
//        System.out.println("checking the user is availabe are not like that ++++++++++");
//        
//        Employee employee = optional.orElseThrow(()->  new UsernameNotFoundException( "Service.Invalid_Data"));
//        
//        if(employee.getStatus().equals(Status.Deactive)) throw new AccessDeniedException("Service.User_Deactivate");
////        EmployeeDTO e= new EmployeeDTO(employee);
////        if(employee)
//        EmployeeDTO e = new EmployeeDTO(employee);
//        e.setPassword(employee.getPassword());
//        
//        
//        return new MyUserDetails(e);
//    }
//    
//    private Collection<? extends GrantedAuthority> getAuthorities(Employee user){
//        System.out.println("Creating the grant authority");
//        List<GrantedAuthority> authorities =  new ArrayList<>();
//        System.out.println("List created");
//        
//        for(Role role : Role.values()) {
//            System.out.println("Adding the role" + role);
//            authorities.add(new SimpleGrantedAuthority(role.toString()));
//        }
//        System.out.println("returing the authorities");
//        return authorities;
//        
//    }
//    public  String generateToken(Map<String, Object> claims, String subject) {
//        
//        System.out.println("Secret Key -- > " +env.getProperty("Secret_Key"));
//        System.out.println("generating token");
////        Calendar c= Calendar.getInstance();
//        Date now = new Date();
//        Date expirationTime = new Date(now.getTime() + 1800_000); // 30 minutes expiration
//String secretKey = env.getProperty("Secret_Key");
//System.out.println("secret key -> " +  secretKey);
////        c.add(Calendar.DAY_OF_MONTH, 2);
//        System.out.println("creating token using map -> claims login service 149");
//        String token = Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(now)
//                .setExpiration(expirationTime)
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//
////        String token= Jwts.builder().setClaims(claims).setSubject(subject)
//////                  setIssuedAt(Calendar.getInstance().getTime())
////        		 .setIssuedAt(now)
////                 .setExpiration(expirationTime)
//////                 .signWith(SignatureAlgorithm.HS256,env.getProperty("Secret_Key") ).compact();
////                 .signWith(SignatureAlgorithm.HS256, env.getProperty("Secret_Key")) // Secret key as String
////                 .compact();
//        System.out.println("|"+token+"|");
//        return token;
//    }
////    need to catch the Exception that is throws for type usernameNotFoundException
//    
//    private Map<String,Object> loadMap(EmployeeDTO employee){
//        	System.out.println("loading map using employee details login serice line 153"); 
//        Map<String,Object> pair= new HashMap<>();
//        pair.put("role", employee.getRole().toString());
//        pair.put("id",employee.getEmployeeId());
//        pair.put("name",employee.getEmployeeName());
//        System.out.println("map - > " + pair);
//        return pair;
//    }
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = Jwts.parser().setSigningKey(env.getProperty("Secret_Key")).parseClaimsJws(token).getBody();
//        return claimsResolver.apply(claims);
//    }
//    
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ErrorInfo> exceptoinHandler(AccessDeniedException exception) {
//        
//        System.out.println("Access Denied Exception ");
//        ErrorInfo  e = new ErrorInfo();
//        return new ResponseEntity<>(e,HttpStatus.UNAUTHORIZED); 
//        
//    }
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<ErrorInfo> usernameNotFoundExceptionHandler(UsernameNotFoundException exception) {
//        
//        System.out.println("user name Not found Exception ");
//        ErrorInfo  e = new ErrorInfo();
//        return new ResponseEntity<>(e,HttpStatus.UNAUTHORIZED); 
//        
//    }
//}






