package com.examly.springapp.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthUser implements Serializable{
    private int userId ;
    private String userName ;
    private String token ; 
    private String role;
    // private String name;    
}
