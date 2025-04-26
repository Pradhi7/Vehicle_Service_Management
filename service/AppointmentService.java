package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;
import com.examly.springapp.model.Appointment;

public interface AppointmentService {
    public Appointment addAppointment(Appointment appointment);
    public void deleteAppointment(Long appointmentId);
    public Optional<Appointment> getAppointmentById(Long appointmentId);
    public List<Appointment> getAllAppointments();
    public List<Appointment> getAppointmentByUserId(int userId);
    public Appointment updateAppointment(Long appointmentId, Appointment appointment);
    public void requestPayment(Long appointmentId);
    public int countUnreadAppointments();
    public void markAllAsRead();

}
