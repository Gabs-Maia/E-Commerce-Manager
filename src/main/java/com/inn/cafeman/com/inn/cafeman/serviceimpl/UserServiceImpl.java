package com.inn.cafeman.com.inn.cafeman.serviceimpl;

import com.inn.cafeman.com.inn.cafeman.JWT.CustomerUsersDetailsService;
import com.inn.cafeman.com.inn.cafeman.JWT.JwtFilter;
import com.inn.cafeman.com.inn.cafeman.JWT.JwtUtil;
import com.inn.cafeman.com.inn.cafeman.POJO.User;
import com.inn.cafeman.com.inn.cafeman.constants.CafeConstants;
import com.inn.cafeman.com.inn.cafeman.dao.UserDao;
import com.inn.cafeman.com.inn.cafeman.service.UserService;
import com.inn.cafeman.com.inn.cafeman.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    UserDao userDao;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomerUsersDetailsService customerUsersDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            log.info("Inside signup {}", requestMap);
            if (validateSignUpMap(requestMap)){
                User user = userDao.findByEmail(requestMap.get("email"));
                if(Objects.isNull(user)){
                    userDao.save(getUserFromMap(requestMap));
                    return CafeUtils.getResponseEntity("Successfully Registered!", HttpStatus.OK);
                }else{
                    return CafeUtils.getResponseEntity("Email already exists.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.INVALIDATE_DATA, HttpStatus.BAD_REQUEST);
            }
            
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        
    }
    
    
    private boolean validateSignUpMap(Map<String, String> requestMap){
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        }
        return false;
    }
    private User getUserFromMap(Map<String, String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
        
    }
    
    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );
            if(auth.isAuthenticated()){
                if(customerUsersDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""+
                            jwtUtil.generateToken(customerUsersDetailsService.getUserDetail().getEmail(),
                                    customerUsersDetailsService.getUserDetail().getRole()) + "\"}",
                    HttpStatus.OK);
            }else{
               return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval." +"\"}", HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception ex){
            log.error("{}", ex);
        }
        return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials."+"\"}",
                HttpStatus.BAD_REQUEST);
    }
}
