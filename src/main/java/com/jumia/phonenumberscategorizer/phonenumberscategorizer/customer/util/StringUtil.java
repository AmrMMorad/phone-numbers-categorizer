package com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.util;

public class StringUtil {
    
    public static boolean checkEmptyString(String string) {
        return string != null && !string.trim().isEmpty() ? true : false;
    }
}