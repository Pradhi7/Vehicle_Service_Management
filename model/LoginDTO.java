package com.examly.springapp.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginDTO implements Serializable{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId ; 

    private String username;
    private String token ; 
    private String userRole ; 
    private String password;

}
