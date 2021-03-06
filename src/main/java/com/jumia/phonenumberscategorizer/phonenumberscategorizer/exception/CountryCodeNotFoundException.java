package com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CountryCodeNotFoundException extends RuntimeException {

    public CountryCodeNotFoundException() {
        super("Country Code Not Found");
    }

}