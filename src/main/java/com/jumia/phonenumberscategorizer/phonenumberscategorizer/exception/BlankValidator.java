package com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BlankValidator implements ConstraintValidator < NotBlankIfPresent, String > {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if (s == null) {
            return true;
        }
        return !s.isBlank();
    }

}