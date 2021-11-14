package com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos;

import lombok.Getter;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception.CountryNameNotFoundException;

@Getter
public enum CountryCodeEnum {

    CAMEROON("237", "\\(237\\)\\ ?[2368]\\d{7,8}$"),
        ETHIOPIA("251", " \\(251\\)\\ ?[1-59]\\d{8}$"),
        MOROCCO("212", "\\(212\\)\\ ?[5-9]\\d{8}$"),
        MOZAMBIQUE("258", "\\(258\\)\\ ?[28]\\d{7,8}$"),
        UGANDA("256", "\\(256\\)\\ ?\\d{9}$"),
        UNDEFINED("000", "");

    private final String code;
    private final String regex;

    CountryCodeEnum(String code, String regex) {
        this.code = code;
        this.regex = regex;
    }

    public static CountryCodeEnum getEnumByCountryCode(String countryCode) {
        for (CountryCodeEnum countryCodeEnum: CountryCodeEnum.values()) {
            if (countryCodeEnum.code.equals(countryCode))
                return countryCodeEnum;
        }
        return CountryCodeEnum.UNDEFINED;
    }

    public static CountryCodeEnum lookup(String countryName) {
        try {
            return CountryCodeEnum.valueOf(countryName);
        } catch (IllegalArgumentException e) {
            throw new CountryNameNotFoundException();
        }
    }
    
}