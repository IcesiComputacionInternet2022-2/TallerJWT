package com.icesi.edu.users.error.exception;

public class InvalidLoginException  extends Exception{
    public InvalidLoginException(){
        super("Invalid credentials try again");
    }
}
