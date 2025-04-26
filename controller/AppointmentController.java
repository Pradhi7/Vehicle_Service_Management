package com.examly.springapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.model.Appointment;
import com.examly.springapp.service.AppointmentService;
import com.examly.springapp.service.AppointmentServiceImpl;

import jakarta.persistence.EntityNotFoundException;

@RestController
public class AppointmentController {


    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    

    @PostMapping("/api/appointment")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<?> addAppointment(@RequestBody Appointment appointment){
        Appointment newAppointment = appointmentService.addAppointment(appointment);
        return ResponseEntity.status(201).body(newAppointment);


    }
    
    @DeleteMapping("/api/appointment/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long appointmentId){
        try{
            appointmentService.deleteAppointment(appointmentId);
            return ResponseEntity.status(204).body("Deleted Successfully");
        }
        catch(EntityNotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/api/appointment/{appointmentId}")
    public ResponseEntity<?> getAppointmentById(@PathVariable Long appointmentId){
        try{
            Optional<Appointment> appointment = appointmentService.getAppointmentById(appointmentId);
            return ResponseEntity.status(200).body(appointment);

        }catch(EntityNotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
        
    }

    @GetMapping("/api/appointment")
    public ResponseEntity<?> getAllAppointments(){
        List<Appointment> list=appointmentService.getAllAppointments();
        return ResponseEntity.status(200).body(list);
    }

    @GetMapping("/api/appointment/user/{userId}")
    public ResponseEntity<?> getAppointmentByUserId(@PathVariable int userId){
        List<Appointment> appointmentList = appointmentService.getAppointmentByUserId(userId);
        return ResponseEntity.status(200).body(appointmentList);
    }

    @PutMapping("/api/appointment/{appointmentId}")
    public ResponseEntity<?> updateAppointment(@PathVariable Long appointmentId, @RequestBody Appointment appointment){
        try{
            Appointment updatedAppointment = appointmentService.updateAppointment(appointmentId, appointment);
            return ResponseEntity.status(200).body(updatedAppointment);
        }
        catch(EntityNotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }

    // New Endpoint: Request Payment for an Appointment
    @PutMapping("/api/appointment/{appointmentId}/request-payment")
    public ResponseEntity<?> requestPayment(@PathVariable Long appointmentId) {
        try {
            ((AppointmentServiceImpl) appointmentService).requestPayment(appointmentId);
            // Return a JSON response
            return ResponseEntity.ok().body(Map.of("message", "Payment request sent successfully."));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error requesting payment: " + e.getMessage()));
        }
    }

    // Get the count of unread appointments
    @GetMapping("/unread-count")
    public ResponseEntity<Map<String, Integer>> getUnreadCount() {
        int unreadCount = appointmentService.countUnreadAppointments();
        Map<String, Integer> response = new HashMap<>();
        response.put("unreadCount", unreadCount);
        return ResponseEntity.ok(response);
    }
 
    // Mark all appointments as read
    @PostMapping("/mark-read")
    public ResponseEntity<Void> markAllAsRead() {
        appointmentService.markAllAsRead();
        return ResponseEntity.ok().build();
    }

    
}
