package com.inn.cafeman.com.inn.cafeman.service;

import com.inn.cafeman.com.inn.cafeman.wraper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService  {
    ResponseEntity<String> signUp(Map<String, String> requestMap);
    
    ResponseEntity<String> login(Map<String, String> requestMap);
    ResponseEntity<List<UserWrapper>> getAllUser();
    ResponseEntity<String> update(Map<String, String> requestMap);
}
