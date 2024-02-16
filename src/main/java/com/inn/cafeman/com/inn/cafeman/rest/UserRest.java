package com.inn.cafeman.com.inn.cafeman.rest;

import com.inn.cafeman.com.inn.cafeman.wraper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path="/user")
public interface UserRest{
    
    @PostMapping
    ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap);
    
    @PostMapping(path = "/login")
    ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap);
    
    @GetMapping(path= "/get")
    ResponseEntity<List<UserWrapper>> getAllUser();
    
    @PostMapping(path = "/update")
    public ResponseEntity<String> update(@RequestBody(required = true)Map<String, String> requestMap);
}
