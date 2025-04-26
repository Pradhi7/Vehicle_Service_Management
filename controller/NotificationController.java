
package com.examly.springapp.controller;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
 
import com.examly.springapp.model.Notification;
import com.examly.springapp.service.NotificationService;
 
@RestController
public class NotificationController {

    
    private final NotificationService notificationService;
 
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    
    @PostMapping("/api/notifications")
    public ResponseEntity<?> createNotification(@RequestBody Notification notification) {
        notificationService.saveNotification(notification);
        return ResponseEntity.ok("Notification created successfully.");
    }

    @GetMapping("/api/notifications")
    public ResponseEntity<?> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications); // Returns the list of notifications as a JSON response
    }
    
    @GetMapping("/api/notifications/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable int userId) {
        List<Notification> notifications = notificationService.getNotificationsForUser(userId);
        return ResponseEntity.ok(notifications);
    }
    
    @DeleteMapping("/api/notifications/{notificationId}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long notificationId) {
        try {
            notificationService.deleteNotification(notificationId);
            // Wrap the success message in a JSON response
            Map<String, String> response = new HashMap<>();
            response.put("message", "Notification deleted successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Wrap the error message in a JSON response
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error deleting notification: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
 
}
