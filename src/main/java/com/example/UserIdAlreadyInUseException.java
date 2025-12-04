package com.example;

public class UserIdAlreadyInUseException extends RuntimeException{
    public UserIdAlreadyInUseException(String message) {
        super(message);
    }
}
