package com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CountryNameNotFoundException extends RuntimeException {

    public CountryNameNotFoundException() {
        super("Country Name Not Found");
    }

}