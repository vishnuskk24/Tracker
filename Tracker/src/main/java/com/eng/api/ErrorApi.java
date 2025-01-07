package com.eng.api;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorApi {

    
    public void errorMethod(String msg) {
        System.out.println("Api is throwing the Exception ErroraPI error methods");
        throw new AccessDeniedException(msg);
    }
}