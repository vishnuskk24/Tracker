package com.tracker.api;

import jakarta.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tracker.dto.EmployeeDTO;
import com.tracker.service.LoginService;

@RestController

//@RequestMapping(value = "/login")
@CrossOrigin("*")
public class LoginAPI {

	@Autowired
	LoginService loginService;
	@Autowired
	Environment envirnment;

//localhost:8765/login
	@PostMapping(value = "/login")
	@PermitAll
	public ResponseEntity<String> authenticateCustomer(@RequestBody EmployeeDTO employeeDetails)
			throws Exception {

		System.out.println("inside the API login calling - > service login");
		String s = loginService.login(employeeDetails);
		return new ResponseEntity<String>(s, HttpStatus.OK);

	}

	@GetMapping(value = "/")
	public String welcome() {
		return "welcome these login successfull";
	}

}


//JSON body
/*

localhost:8765/login

{
"username":"Neena_mc",
"password":"Neena_mc"
}



*/

