package com.examly.springapp.controller;
 
 
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
 
import com.examly.springapp.model.User;
import com.examly.springapp.service.UserServiceImpl;
 
import jakarta.persistence.EntityNotFoundException;
 
 
@RestController
 
public class UserController {

    
    private final UserServiceImpl userService;
 
 
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    //Get All Users
    @GetMapping("/api/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.findAllUsers();
            return ResponseEntity.status(200).body(users); // OK
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage()); // Bad Request
        }
    }
 
    // Update User
    @PutMapping("/api/user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable int userId, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(userId, user);
            return ResponseEntity.status(200).body(updatedUser); // OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // Not Found
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage()); // Bad Request
        }
    }
 
    // Delete User
    @DeleteMapping("/api/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.status(204).body("User deleted successfully"); // No Content
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // Not Found
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage()); // Bad Request
        }
    }
    @GetMapping("/api/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        User user = userService.getByUserId(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/api/user/name/{name}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("name") String username) {
        try {
            User user = userService.loadUserByUsername(username);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}
 