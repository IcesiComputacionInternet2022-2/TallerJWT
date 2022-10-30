package com.icesi.edu.users.error.exception;

public class NoAccesException extends Exception{

    public NoAccesException(){
        super("You have not the permissions to reach the resource");
    }
}
