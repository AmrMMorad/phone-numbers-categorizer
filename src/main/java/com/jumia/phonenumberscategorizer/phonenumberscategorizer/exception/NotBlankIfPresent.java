package com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = BlankValidator.class)
@Target({ 
    ElementType.METHOD,
    ElementType.ANNOTATION_TYPE,
    ElementType.PARAMETER 
})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankIfPresent{

    String message() default "Field must not be blank";

    Class <?> [] groups() default {};

    Class <? extends Payload> [] payload() default {};
}