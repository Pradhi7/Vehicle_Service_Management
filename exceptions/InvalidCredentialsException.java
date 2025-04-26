package com.examly.springapp.exceptions;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(){
        super();
    }
    public InvalidCredentialsException(String msg){
        super(msg);
    }
}
