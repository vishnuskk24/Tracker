//package com.eng.security;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.sql.DataSource;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//
//import com.eng.utility.UtilityMethods;
//import com.eng.jwtvalidation.*;
//import com.eng.entity.Role;
//import com.eng.service.LoginService;
//import com.eng.service.LoginServiceImpl;
//
//@Configuration
//@EnableWebSecurity
//
//@EnableGlobalMethodSecurity(prePostEnabled=true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    
//    @Autowired
//    DataSource dataSource;
//    
//    @Autowired
//    UserDetailsService userDetailService;
//    
//    @Autowired
//    private JwtUtil requestFilter;
//    
//    @Autowired
//    LoginService myUserDetailsService;
//
////    @Autowired
////    
////    BCryptPasswordEncoder encoder;
//    
////    
//    @Autowired
//    public void configure(AuthenticationManagerBuilder auth) throws Exception{
//        System.out.println("inside auth manager");
//        auth.userDetailsService(userDetailService)
//        .passwordEncoder(NoOpPasswordEncoder.getInstance());
//            
////        .usersByUsernameQuery("Select username,password,status from employee where username=?") // taking user name and password and status based on username
////        .authoritiesByUsernameQuery("select username,roles from employee where username=?"); // taking roles based on username
//    }
//    @Bean
//    public AuthenticationManager authenicationProvider(AuthenticationConfiguration auth) throws Exception { // we are taking aut manager to get our own flow of validating request
//    
//    		return auth.getAuthenticationManager();
//    }
//    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        
//        
//        http.authorizeRequests()
//        .antMatchers("/login**").permitAll()
//        .antMatchers("/user**").hasRole(Role.User.toString())  // - >  user url can be accessed by user lead and manager
//        .antMatchers("/lead**").hasRole(Role.Lead.toString()) // for lead url  can able to accesed by lead and manager
//        .antMatchers("/manager**").hasRole(Role.Manager.toString())// -> for manager role
//        
//        .anyRequest().authenticated()  // -  > all request needs to be authenicated
////        .and()
////        .formLogin()//.loginPage("http://localhost:3000/login") // - > mentioning the login needs to happen in form based login and it is by custom page
////        .loginProcessingUrl("/login")
////        .defaultSuccessUrl("/", true)   // url will requested once the validation sucecss
////        .failureUrl("/login").permitAll()  // url will be landed once the validation fails
//        
//        .and().httpBasic()
//        .and()
//        .logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll()
//        .and()
//        .csrf().disable()
//        .exceptionHandling().and().sessionManagement()
//        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//http.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class); // // csrf disabled to perform all the request post put delete request
//        
//         
//        
//        //- >  these line says that all the user have access to the above url(permit all)
//        
//        
////        http.
////        authorizeRequests().antMatchers("/**").hasAnyRole("EMPLOYEE","USER")   // - > these line shows that only these role users can able to access the url
////        .anyRequest().authenticated()             // - > all the request for the above url needs to be authenicated
////        .and().formLogin();    // - > if the user is not suthenicated then it will route to the default login page
////        
//    }
//    
//    
//    
//    // inmemory -> 
//    // database - >
//    //OAUTH - > LDAP  google
//    
//    
//}







