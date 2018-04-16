package com.scmspain.infrastructure.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.scmspain.domain.TweetNotFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Controller to manage exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Object invalidArgumentException(IllegalArgumentException ex) {
        return getExceptionObject(ex);
    }

    @ExceptionHandler(TweetNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    public Object tweetNotFoundException(TweetNotFoundException ex) {
        return getExceptionObject(ex);
    }

    private Object getExceptionObject(Exception ex) {
        return new Object() {
            public String message = ex.getMessage();
            public String exceptionClass = ex.getClass().getSimpleName();
        };
    }

}
