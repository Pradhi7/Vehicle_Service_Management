package com.examly.springapp.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Appointment implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;
    @ManyToOne 
    @JoinColumn(name="serviceId")
    private VehicleMaintenance service;
    private LocalDate appointmentDate; 
    private String location;
    private String status;
   
    @ManyToOne 
    @JoinColumn(name="userId")
    // @JsonIgnore
    private User user;

    @Column(name = "is_payment_requested", nullable = false)
    private Boolean isPaymentRequested = false;
       
}
