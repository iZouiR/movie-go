package com.izouir.web.moviego.exception;

public class MovieNotFoundException extends ResourceNotFoundException {
    public MovieNotFoundException(final String message) {
        super(message);
    }
}
