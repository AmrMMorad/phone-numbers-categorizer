package com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception;

import org.springframework.http.HttpStatus;

public class PageSizeException extends GeneralApplicationException {

    public PageSizeException() {
        super(HttpStatus.BAD_REQUEST, "Invalid Page Size Exception");
    }

}
