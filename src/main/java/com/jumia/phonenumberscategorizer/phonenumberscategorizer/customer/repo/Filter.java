package com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.repo;

import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.Customer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Filter {

    public Key key;

    public Object value;

    public enum Key {

        VALIDPHONE("validPhone"),
            COUNTRYNAME("countryName"),
            FULLNUMBER("fullNumber");

        public final String label;

        private Key(String label) {
            this.label = label;
        }

        public static Key lookup(String key) {
            try {
                return Key.valueOf(key);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid value for Key enum: " + key);
            }
        }
    }

}