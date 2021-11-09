package com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception;

import org.springframework.http.HttpStatus;

public class CountryCodeNotFoundException extends GeneralApplicationException {

    public CountryCodeNotFoundException() {
        super(HttpStatus.BAD_REQUEST, "Country Code Not Found");
    }

}
