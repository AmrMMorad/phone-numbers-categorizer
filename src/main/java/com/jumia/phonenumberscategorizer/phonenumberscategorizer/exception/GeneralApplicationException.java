package com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception;

import org.springframework.http.HttpStatus;

public class GeneralApplicationException extends Exception {

	private HttpStatus status;

    public GeneralApplicationException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}
