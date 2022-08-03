package com.izouir.web.moviego.exception;

public class AuthorityNotFoundException extends ResourceNotFoundException {
    public AuthorityNotFoundException(final String message) {
        super(message);
    }
}
