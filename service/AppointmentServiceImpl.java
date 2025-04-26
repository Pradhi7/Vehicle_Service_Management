package com.examly.springapp.service;
 
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.examly.springapp.model.Appointment;
import com.examly.springapp.model.Notification;
import com.examly.springapp.model.User;
import com.examly.springapp.model.VehicleMaintenance;
import com.examly.springapp.repository.AppointmentRepo;
import com.examly.springapp.repository.NotificationRepo;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.repository.VehicleServiceRepo;
import jakarta.persistence.EntityNotFoundException;
 
@Service
public class AppointmentServiceImpl implements AppointmentService{
 
    private final AppointmentRepo appointmentRepo;
 
    private final UserRepo userRepo;
 
    private final NotificationRepo notificationRepo;

    private final VehicleServiceRepo vehicleServiceRepo;
 
    public AppointmentServiceImpl(AppointmentRepo appointmentRepo, UserRepo userRepo, NotificationRepo notificationRepo,
            VehicleServiceRepo vehicleServiceRepo) {
        this.appointmentRepo = appointmentRepo;
        this.userRepo = userRepo;
        this.notificationRepo = notificationRepo;
        this.vehicleServiceRepo = vehicleServiceRepo;
    }

    
    @Override
    public Appointment addAppointment(Appointment appointment) {
        User user = userRepo.findById(appointment.getUser().getUserId()).get();
        VehicleMaintenance vehicle = vehicleServiceRepo.findById(appointment.getService().getServiceId()).get();
        appointment.setUser(user);
        appointment.setService(vehicle);
        return appointmentRepo.save(appointment);
    }
 
    @Override
    public void deleteAppointment(Long appointmentId) {
        Optional<Appointment> opt = appointmentRepo.findById(appointmentId);
        if(opt.isEmpty()){
            throw new EntityNotFoundException("Not Found");
        }
        appointmentRepo.deleteById(appointmentId);
    }
 
    @Override
    public Optional<Appointment> getAppointmentById(Long appointmentId) {
        Optional<Appointment> opt = appointmentRepo.findById(appointmentId);
        if(opt.isEmpty()){
            throw new EntityNotFoundException("Not Found");
        }
        return appointmentRepo.findById(appointmentId);
    }
 
    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointmentList = appointmentRepo.findAll();
        return appointmentList;
    }
 
    @Override
    public List<Appointment> getAppointmentByUserId(int userId) {
        List<Appointment> appointmentList = appointmentRepo.findByUserUserId(userId);
        return appointmentList;
    }
 
    @Override
    public Appointment updateAppointment(Long appointmentId, Appointment appointment) {
        Optional<Appointment> opt = appointmentRepo.findById(appointmentId);
        if(opt.isEmpty())
        {
            throw new EntityNotFoundException("Not Found");
        }
       
        appointment.setAppointmentId(appointmentId);
        return appointmentRepo.save(appointment);
    }    
 
 
    @Override
    public void requestPayment(Long appointmentId) {
    Appointment appointment = appointmentRepo.findById(appointmentId)
            .orElseThrow(() -> new EntityNotFoundException("Appointment not found with ID: " + appointmentId));
 
    // Update appointment status
    appointment.setStatus("Payment Requested");
    appointmentRepo.save(appointment);
 
    // Create a notification for the user
 
    Notification notification = new Notification();
    notification.setUser(appointment.getUser()); // Set the user
    notification.setAppointment(appointment); // Link the appointment
    notification.setMessage("Payment requested for your appointment. Please proceed to payment.");
    notification.setRead(false); // Mark as unread
    notificationRepo.save(notification); // Save the notification
    }

    @Override
    public int countUnreadAppointments() {
        return appointmentRepo.countByStatus("Pending");
}
 
    @Override
    public void markAllAsRead() {
        List<Appointment> unreadAppointments = appointmentRepo.findByStatus("Pending");
        for (Appointment appointment : unreadAppointments) {
            appointment.setStatus("Read");
            appointmentRepo.save(appointment);
        }
    }
   
}
 
 