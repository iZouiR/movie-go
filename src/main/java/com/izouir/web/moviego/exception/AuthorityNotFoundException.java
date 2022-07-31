package com.izouir.web.moviego.exception;

public class AuthorityNotFoundException extends RuntimeException {
    public AuthorityNotFoundException(final String message) {
        super(message);
    }
}
