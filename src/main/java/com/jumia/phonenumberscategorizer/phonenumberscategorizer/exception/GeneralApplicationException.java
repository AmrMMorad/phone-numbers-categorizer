package com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception;

import org.springframework.http.HttpStatus;

import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralApplicationException extends Exception {

    private HttpStatus status;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public GeneralApplicationException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        logger.info("Exception is: " + message);
    }

}
