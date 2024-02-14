package com.inn.cafeman.com.inn.cafeman.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CafeUtils {
    private CafeUtils(){
    
    }
    
    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        
    }
}
