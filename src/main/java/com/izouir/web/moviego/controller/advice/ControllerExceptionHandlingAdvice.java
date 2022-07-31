package com.izouir.web.moviego.controller.advice;

import com.izouir.web.moviego.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(annotations = Controller.class)
public class ControllerExceptionHandlingAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandlingAdvice.class);

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleException(final NotFoundException exception) {
        LOGGER.error(exception.getMessage());
        final ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.addObject("status", HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
