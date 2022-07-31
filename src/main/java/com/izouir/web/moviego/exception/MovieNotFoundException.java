package com.izouir.web.moviego.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(final String message) {
        super(message);
    }
}
