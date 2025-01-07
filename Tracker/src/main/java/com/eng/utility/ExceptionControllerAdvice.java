package com.eng.utility;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eng.exception.InfyTrackerException;
import com.eng.service.LoginService;


@ControllerAdvice
@RestControllerAdvice(basePackageClasses  = LoginService.class)
public class ExceptionControllerAdvice {
    @Autowired
    Environment environment;
    
    private static final Log LOGGER = LogFactory.getLog(ExceptionControllerAdvice.class);
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> exceptionHandler(Exception exception) {
        
        LOGGER.error("This Exception is Handled by Exception-handler in central Exception handler and the Exception is " + exception);
        ErrorInfo error = new ErrorInfo();
        error.setErrorMessage(environment.getProperty("General.EXCEPTION_MESSAGE"));
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorInfo>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorInfo> ValidationExceptionexceptionHandler(ValidationException exception) {
        
        LOGGER.error("This Exception is Handled by Exception-handler in central Exception handler and the Exception is " + exception);
        ErrorInfo error = new ErrorInfo();
        String prp=exception.getMessage();
        System.out.println(prp);
        error.setErrorMessage(environment.getProperty(prp));
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorInfo>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InfyTrackerException.class)
    public ResponseEntity<ErrorInfo> infyTrackerExceptionHandler(InfyTrackerException exception) {
        LOGGER.error("This Exception is Handled by INFY-TRACKER-Exception handler in central Exception handler and the Exception is " + exception);
        System.out.println(exception.getMessage());
        ErrorInfo error = new ErrorInfo();
        error.setErrorMessage(environment.getProperty(exception.getMessage()));
        error.setTimestamp(LocalDateTime.now());
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorInfo>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInfo> exceptionHandler(ConstraintViolationException exception) {
        LOGGER.error("This Exception is Handled by CONSTRAIN-VIOLATION-Exception handler in central Exception handler and the Exception is " + exception);
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());

        
        String errorMsg = exception.getConstraintViolations().stream().map(x -> x.getMessage())
                .collect(Collectors.joining(", "));
        errorInfo.setErrorMessage(errorMsg);
        errorInfo.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> exceptionHandler(MethodArgumentNotValidException exception) {
        LOGGER.error("This Exception is Handled by METHOD-ARGUMENT-NOT-VALID-Exception handler in central Exception handler and the Exception is " + exception);
            System.out.println("issue in json");
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());

        
        String errorMsg = exception.getBindingResult().getAllErrors().stream().map(x -> x.getDefaultMessage())
                .collect(Collectors.joining(", "));

        errorInfo.setErrorMessage(errorMsg);
        errorInfo.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorInfo> accessDeniedExceptionHandler(AccessDeniedException exception) {
        System.out.println("Acces denied Exception in the REst controller Advice");
        ErrorInfo error = new ErrorInfo();
        error.setErrorMessage(environment.getProperty("Access_Denied"));
        error.setErrorCode(HttpStatus.FORBIDDEN.value());
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<ErrorInfo>(error, HttpStatus.FORBIDDEN);
        
    }
    

}

    