package com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.helper;

import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CountryCodeEnum;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception.CountryCodeNotFoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountryHelper {
    private static final Pattern countryCodePattern = Pattern.compile("\\(([^)]+)\\)");

    public static CountryCodeEnum getCountryEnumFromPhone(String phoneNumber) throws CountryCodeNotFoundException {
        Matcher matcher = countryCodePattern.matcher(phoneNumber);

        if (!matcher.find()) {
            throw new CountryCodeNotFoundException();
        }
        return CountryCodeEnum.getEnumByCountryCode(matcher.group(1));
    }
}