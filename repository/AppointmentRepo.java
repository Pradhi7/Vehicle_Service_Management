package com.examly.springapp.repository;
import org.springframework.stereotype.Repository;
import com.examly.springapp.model.Appointment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Long>{
    
    // @Query("SELECT a FROM Appointment a WHERE a.user.id = :userId")
    public List<Appointment> findByUserUserId(int userId);
    // Count appointments by their status
    public int countByStatus(String status);
 
    // Find appointments by their status
    public List<Appointment> findByStatus(String status);
    
}
