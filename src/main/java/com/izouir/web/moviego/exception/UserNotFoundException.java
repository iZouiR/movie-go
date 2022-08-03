package com.izouir.web.moviego.exception;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(final String message) {
        super(message);
    }
}
