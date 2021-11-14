package com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.helper;

import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.Customer;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.PhoneNumber;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CountryCodeEnum;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception.CountryCodeNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerHelper {

    public static List < Customer > filterCustomersByPhoneValidity(List < Customer > allCustomers) throws CountryCodeNotFoundException {
        List < Customer > filteredCustomers = new ArrayList < Customer > ();

        for (Customer customer: allCustomers) {
            CountryCodeEnum customerCountryCodeEnum = CountryHelper.getCountryEnumFromPhone(customer.getPhone());
            if (Pattern.compile(customerCountryCodeEnum.getRegex()).matcher(customer.getPhone()).find())
                filteredCustomers.add(customer);
        }
        return filteredCustomers;
    }
}