package com.tracker.utility;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//@RestController
@RestControllerAdvice
public class CustomErrorController implements ErrorController {
    
    @Autowired
    Environment environment;
    
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorInfo> UsernameNotFoundExceptionHandler(UsernameNotFoundException exception) {
        System.out.println("Servelet Exception handler");
        ErrorInfo error = new ErrorInfo();
        error.setErrorMessage(environment.getProperty(exception.getMessage()));
        error.setErrorCode(HttpStatus.FORBIDDEN.value());
        error.setTimestamp(LocalDateTime.now());
        System.out.println("returning Http bad request for ServeletException");
        return new ResponseEntity<ErrorInfo>(error, HttpStatus.FORBIDDEN);
        
    }

}
