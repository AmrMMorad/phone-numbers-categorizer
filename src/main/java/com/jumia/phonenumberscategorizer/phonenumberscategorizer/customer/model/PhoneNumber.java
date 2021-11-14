package com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Builder
@Getter
@Setter
public class PhoneNumber {

    private String countryCode;
    private String localNumber;

}