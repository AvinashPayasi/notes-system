package com.notes.system.api.exception;

public class UserAlreadyExistsExcpetion extends RuntimeException {
    public UserAlreadyExistsExcpetion(String message) {
        super(message);
    }
}
