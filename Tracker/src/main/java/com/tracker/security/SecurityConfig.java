package com.tracker.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tracker.jwtvalidation.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	UserDetailsService userDetilDetailsService;
	@Autowired JwtFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		

//		csrf() -> mthod takes
//		Customizer<CsrfConfigurer<HttpSecurity>>
//		customizer -> fuctional interface prvided by spring security -> its having customize one abstract method  
//		customizer-> customizer.disable() -> goes to the implementation for the abstract method
//		
//								Customizer<CsrfConfigurer<HttpSecurity>> csrf = new Customizer<CsrfConfigurer<HttpSecurity>>() {
//							
//									
//									@Override
//									public void customize(CsrfConfigurer<HttpSecurity> customizer) {
//										// TODO Auto-generated method stub
//										customizer.disable();
//									}
//								};
		
//		instead of doing above stuffes we can use lanbda function that mentioned in line 37
								
		return httpSecurity
				.csrf(customizer-> customizer.disable()) //  disabling csrf
				.authorizeHttpRequests(request -> request
													.requestMatchers("/login","register").permitAll()
													.anyRequest().authenticated()) // we are mentioning all the request to be authenicated
				.formLogin().disable() // enabling the default login form  for web page  ||  disaling the form login becoz we are making it to stateless server is not managin any session so everytime we will get new session id  but http basic will prvide the small prompt for cedentials 
				.httpBasic(Customizer.withDefaults())// for postman we are configuring /enabling login operation  
				.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// so every time we have to pass the credetials  so the is not state is manages in server side if we use form login then every tme we have to give credentials in webpage but in postman in authorization we can give only once
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) //  before username and password filter we are adding jwt filter to validate jwt token if not it will check for username and password
				.build();  // if we have only build in that cas we are not customizing   before build we have to do out required configuration
		
		
		
	}
	
	
	/*
//	InMemoryUserDetailsManager is child of the user details service  //  so we are configuring the user detail service to inmemory instead of default flow 
	@Bean // Inmemory Authenication
	public UserDetailsService userDetailService() {
		// we are providin the user details in inmemory it will present in application Ram memory
//		User -> child of UserDetails
		UserDetails user1= User
							.withDefaultPasswordEncoder()
							.username("Rajesh")
							.password("Raj@123")
							.roles("Admin")
							.build();
		UserDetails user2= User
				.withDefaultPasswordEncoder()
				.username("Rakesh")
				.password("Rak@123")
				.roles("User")
				.build();
		return new InMemoryUserDetailsManager(user1,user2);
		
	}
	*/
	
	
//	DaoAuthenticationProvider -> child of the AuthenicationProvider
//	we are actually  configuring the Authenication provider (who authenicates the request) with help of their child class
	@Bean
	public AuthenticationProvider autheniAuthenticationProvider() {
		DaoAuthenticationProvider provider =  new DaoAuthenticationProvider();
		
		provider.setPasswordEncoder(new BCryptPasswordEncoder(4));   // mention the bcrypt password encoder to validdate the user with password
		provider.setUserDetailsService(userDetilDetailsService);
		return provider;
		
		
	}
	@Bean
	public AuthenticationManager authenicateManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		
		return authenticationConfiguration.getAuthenticationManager();
		
		
	}
}


//===================================================================================================================
//   1 
//  configure http filter chain Security filterchain HttpSecurity


// SecurityFilterChain     
//whereeve i mentioned * required lambda function

//HttpSecurity

//             csrf() -> customizer.disable()  
// (cafhsb)				 *                           auth ->  *  requestMatcher("") permitAll() anyRequest() authenicated()     *                      
// HttpsSecurity  methods -> csrf(),                   authorizeHttpRequest()        
//																  *
//					formlogin() httpBasic()                    sessionManagement build()

//using that we are configuring the request

//UserDetailService -> helps to load username password roles from	inmemory database , LDAP



//=======================================================================================================

//   2

// InMemoryAuthenication using    1 UserDetailService, 2 InMemoryUserDetailManager 3 UserDetails 4 User 
// ---------------------------
//	Explaining class

//	1 UserDetailService  -> main guy who makes the orginal data to be ready for authencation ith actua user requst
//	2 InMemoryUserDetailsManager    < - By passing UserDetails(created by User class)  to initiantiate  InMemoryUserDetailsManager Object
//  3 User        child of UserDetails     
//      
//  4 UserDetails parent of User

//Relationship between class
// -------------------------

//InMemoryUserDetailManager -> childs of UserDetailService       UserDetails < -User
//	configuring inmemory Authenication -> method will return InMemoryUserDetailsManager Object

//steps  to do
//     
//		1 create method with UserDetailService as return type and  anotate with @Bean
//		2 that mthod have to return the object of InMemoryUserDetailManager by taking UserDetails 
//      3  UserDetails  < created by USer class by providing userame password and roles
// methods
//   UserDetails user2= User .withDefaultPasswordEncoder() .username("Rakesh") .password("Rak@123") .roles("User").build();

//==========================================================================================================================

//Authenication Provider  -> will hold the logic of how to validate the user



//3 
//=====
// Using the Database for Authenication  class -> AuthenicationProvider DAOAuthenicationProvider  
//												UserDetailService  ->CustomUSerDetailService(userdefined child class) // to retrive data from db
//												USerDetails -> CustomUserDetails (user defined child class) to populate the value from entity to UserDetailService format
//												GrantedAuthority -> SimpleGrantedAuthority

//flow  -> Authenication Provider - > DAOAuthenictionprovider -?(required) USerDetailService -?(requires) CustomUserDetailService -?Requires() UserDetails -?CustomUserDetails
// 	                                                                         									loadUserbyUserName                 all entity var wth authority
// RelationShip   
// -----------
//AuthenicationProvider   is parent of DAOAuthenicationProvider

//	UserDetailService -> have to retrive the data from Db for then we have to customize the config by creating child class
//   that Child class needed the USerDetails Object becoz it have to send the data in that type  so 
//    crete aother CustomUSerDetails that impleents UserDetails  and provide some methods


//Retriving the Data from Table
//Steps 
//	1. create method that returns AuthenicationProvider
//	2  Return type will be DAOAuthenicationProvider
//  3  Create Object for DAO AuthenicationProvider
//  4. setPsswordEncoder -> NoOpPasswordEncoder.getInstance()  // not using anypassord encoder
//  5. setUserDetailService  ->    provide the UserDetail Service  that will provide the data from DB or inmemory 
//			create own CustomUserDetailservice(child class) by implementing UserDetailService   make it as child 
//					 make that child as component by providig @service


//  When you create CustomUserDetailService At that tim it will ask  to implementes some abstract method   4
//		   to implement that we have to provide the UserDetails  <- privided by Spring
//				 to provided that object we have to create another CustomUserDetail Class  in 5
	


//=================================================================


//4 CustomUserDetailService class  implements USerDetailService

//    add unimplemented methods - > loadUserbyUserNAme  -> call repo to get the user data according to the username
//	 check for user present or not convr entity -> intoDTO -> CustomUserDetail(USerDetail) by calling constructor
//	return the values



//Steps
//====================================================================================================================
//5
//CustomUserDetailService

//Configure  to pass it to DaoAuthenicateProvider
//Steps
//	1
//		CustomUserDetailServie -> UserDetailService -> give all the variable in entity like( password , status username, mailId role) + add authorities of type List<GrantedAuthority>
//		2
//		initilize the Grantedauthority by SimpleGrantedAuthority by passing the roles as String
//     3
//		List<GrantedAuthority> auth = new ArrayList<>();
//		auth.add(new SimpleGrantedAuthority(role.toString()));
//		this.authorites = auth;   // in constructor
//		4
//  	Override the methods like getUserName()  getPassword()   getAuthorities()  isEnabled()


//=================================================================================================

//6  
//Encrypting password
//---------------------



//classes  -> Bcryptpassword  spring security wil provide Bcryptpassword encoder

//    process
//----------------

//   password in db will be hashed 
//  while login we retrive than hash and we will hash our password and we will compare thr has value if batch true else false
//  hashin algo Bcrypt  we cant text from hash
//
//	steps
//-----------
// do changes in two place one -> add user and validate the user
// in security config mention the bcryptPassword encoder in Dao authenicator
//  update other passwords
// ================================================================



//   7

//  jwt - > json web token
//---------------

//sso  single sign on  in one place we will sign in so all apps we will use like google drive mail playstore

//   about token
//  header      .       body.           signature(hashed)
//					about subject(user)integraty



//==================================================================================


//  8 setting up project
//--------------------------
// class -> AutheniticationManager  , AuthenticationConfiguration
//  AuthenicationManager -> will take access to validate the request if request have any token
//  AuthenticationConfiguration is to get the instance of authenticationManager instance
// we have to add one more layer
//when request gos thn we have to object of the authencation goes to the server -> authenication manager -> auhenication provider
//  using Authenication manager

// For jwt we have to work wth authenication manager

//dependencies
//<dependency>
//	<groupId>io.jsonwebtoken</groupId>
//	<artifactId>jjwt-api</artifactId>
//	<version>0.12.5</version>
//</dependency>

//<dependency>
//	<groupId>io.jsonwebtoken</groupId>
//	<artifactId>jjwt</artifactId>
//	<version>0.12.5</version>
//</dependency>

//<dependency>
//	<groupId>io.jsonwebtoken</groupId>
//	<artifactId>jjwt-impl</artifactId>
//	<version>0.12.5</version>
//	<scope>runtime</scope>
//</dependency>
//<dependency>
//	<groupId>io.jsonwebtoken</groupId>
//	<artifactId>jjwt-jackson</artifactId>
//	<version>0.12.5</version>
//	<scope>runtime</scope>
//</dependency>


//Steps
//		when we say authmanager we want to control in that case we want to create the bean return type authenicationManager
// in login we are not equation the username and password we are simply providing to authenticationmanager to he will validate with help of autheniticationprovider using the UserDetailService and the DaoAuthentication manager

//	Authentication Configuration will give auhmanager object
//==========================================================================================================
// 9 JWT generation
//	class  -> Jwts -> it will help to generate the token
//it provideds mthod like claims() claimBuilder()-> to build out token

//  KeyGenerator- >  getInstance() will give the object of the same in get instance we have to specfiy the alog => "HmacSHA256"
//	SecretKey - KeyGeneratorobj.generateKey() it will return secret key
//  use base64.getEncoder.encodeToString(secretkey.getEncoded()); it wil return string
// need to add more point  *************************

//======================================================

//10 validate JWT token
//------------------------

//in  security config we are adding a line to before build addFilterBefore(jwtFilter,Usernameandpasswordfilter.class)

// so we need to create jwtFilter -> parent -> oncePerFilter

// abstract method doInternal filter  -> provide implementation


//  


