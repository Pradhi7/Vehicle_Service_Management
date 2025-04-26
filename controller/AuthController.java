package com.examly.springapp.controller;
 
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.config.JwtUtils;
import com.examly.springapp.model.LoginDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.service.UserServiceImpl;
 
import jakarta.persistence.EntityNotFoundException;
 
@RestController
public class AuthController {

    private final UserServiceImpl userServiceImpl;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;
   
    public AuthController(UserServiceImpl userServiceImpl, JwtUtils jwtUtils,
            AuthenticationManager authenticationManager) {
        this.userServiceImpl = userServiceImpl;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }
 
    @PostMapping("/api/register")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        User newUser = userServiceImpl.registerUser(user);
        return ResponseEntity.status(201).body(newUser);  // 201 Created
    }
 
 
    @GetMapping("/api/user/{userId}")
    public ResponseEntity<?> getUserId(@PathVariable int userId ){
        try {
            User savedGetId = userServiceImpl.getByUserId(userId);
            return ResponseEntity.status(200).body(savedGetId);  // 200 OK
        }
        catch(EntityNotFoundException e){
            return ResponseEntity.status(401).body(e.getMessage()); // 401 Unauthorized
        }
    }
 
    @PostMapping("/api/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO user){
        Authentication authentication = authenticationManager
                                                .authenticate(
                                                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
                                                );
                    
        if(authentication.isAuthenticated()){
            User responsUser = userServiceImpl.loadUserByUsername(user.getUsername());
            LoginDTO response = new LoginDTO();
            response.setToken(jwtUtils.generateToken(user.getUsername()));
            response.setUserId(responsUser.getUserId());
            response.setUsername(responsUser.getUsername());
            response.setUserRole(responsUser.getUserRole());
            return ResponseEntity.status(201).body(response);  // 201 Created
        }
        else{
            throw new UsernameNotFoundException("invalid user request...!");
        }
    }
 
    @GetMapping("/api/check-username")
    @PreAuthorize("permitAll()")
    public boolean checkUsername(@RequestParam String username) {
        return userServiceImpl.usernameExists(username);
    }
 
    @GetMapping("/api/check-email")
    @PreAuthorize("permitAll()")
    public boolean checkEmail(@RequestParam String email) {
        return userServiceImpl.emailExists(email);
    }
 
    @GetMapping("/api/check-mobile")
    @PreAuthorize("permitAll()")
    public boolean checkMobile(@RequestParam String mobileNumber) {
        return userServiceImpl.mobileExists(mobileNumber);
    }
    
    // --------------------- OTP Endpoints ---------------------
    
    /**
     * Sends an OTP to the provided email address.
     * Expected JSON payload: { "email": "user@example.com" }
     * On success, returns status 200; on failure, returns status 500.
     */
    @PostMapping("/api/send-otp")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> sendOtp(@RequestBody String email) {
        try {
            userServiceImpl.sendOtpViaEmail(email);
            // Return a JSON response
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "OTP sent successfully to " + email);
            
            return ResponseEntity.status(200).body(responseBody);
        } catch (Exception e) {
            // Handle errors gracefully with JSON
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("status", "error");
            errorBody.put("message", "Error sending OTP: " + e.getMessage());
            
            return ResponseEntity.status(500).body(errorBody);
        }
    }    
    
    /**
     * Verifies the provided OTP and, if valid, registers the user.
     * Expected JSON payload:
     * {
     *   "email": "user@example.com",
     *   "otp": "123456",
     *   "user": {
     *       "username": "johndoe",
     *       "mobileNumber": "9876543210",
     *       "password": "YourPassword123!",
     *       "userRole": "User"
     *   }
     * }
     * On success returns status 201; on failure returns status 400.
     */
    @PostMapping("/api/verify-otp")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, Object> request) {
        try {
            String email = (String) request.get("email");
            String otp = (String) request.get("otp");
            @SuppressWarnings("unchecked")
            Map<String, Object> userMap = (Map<String, Object>) request.get("user");
            
            User user = new User();
            user.setUsername((String) userMap.get("username"));
            user.setEmail(email);
            user.setMobileNumber((String) userMap.get("mobileNumber"));
            user.setPassword((String) userMap.get("password"));
            user.setUserRole((String) userMap.get("userRole"));
            
            User registeredUser = userServiceImpl.verifyOtpAndRegister(user, otp);
            return ResponseEntity.status(201).body(registeredUser);  // 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body("OTP verification failed: " + e.getMessage());  // 400 Bad Request
        }
    }
}
