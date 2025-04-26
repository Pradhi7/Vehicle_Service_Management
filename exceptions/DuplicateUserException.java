package com.examly.springapp.exceptions;

public class DuplicateUserException extends RuntimeException{
    public DuplicateUserException(){
        super();
    }
    public DuplicateUserException(String msg){
        super(msg);
    }
}
