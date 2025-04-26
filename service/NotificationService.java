package com.examly.springapp.service;
 
import com.examly.springapp.model.Notification;
import com.examly.springapp.repository.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
 
@Service
public class NotificationService {
 
    @Autowired
    private NotificationRepo notificationRepo;
 
    public void saveNotification(Notification notification) {
        notificationRepo.save(notification);
    }
 
    public List<Notification> getNotificationsForUser(int userId) {
        return notificationRepo.findByUser_UserId(userId); // Assuming you have a relation with User entity
    }
 
    public void deleteNotification(Long notificationId) {
        notificationRepo.deleteById(notificationId);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepo.findAll(); // Fetches all notifications from the database
    }

}
