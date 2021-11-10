package com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception;

import org.springframework.http.HttpStatus;

public class PageNumberException extends GeneralApplicationException {

    public PageNumberException() {
        super(HttpStatus.BAD_REQUEST, "Invalid Page Number Exception");
    }

}
