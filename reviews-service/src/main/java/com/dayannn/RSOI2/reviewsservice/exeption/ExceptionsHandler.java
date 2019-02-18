package com.dayannn.RSOI2.reviewsservice.exeption;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExceptionsHandler {
    private Logger logger = LoggerFactory.getLogger(ExceptionsHandler.class);

    public ExceptionsHandler() {
    }

    @ResponseBody
    @ExceptionHandler(ReviewNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String reviewNotFoundHandler(ReviewNotFoundException e){
        logger.error("Error while looking for a review: " + e);
        return e.getMessage();
    }
}
