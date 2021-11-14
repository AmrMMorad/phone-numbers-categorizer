package com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer;

import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.repo.CustomerRepository;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.repo.Filter;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception.CountryCodeNotFoundException;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception.CountryNameNotFoundException;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception.FilterNotFoundException;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.helper.CountryHelper;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.helper.CustomerHelper;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.mapper.Mapper;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.mapper.CustomerPageMapper;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.Customer;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.PhoneNumber;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CountryCodeEnum;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CustomerDTO;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CustomerPageDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CustomerPageDTO getCustomers(Boolean validPhoneNumbersOnly, String countryName, String countryCode,
        String localNumber, int page, int size) throws FilterNotFoundException, CountryNameNotFoundException, CountryCodeNotFoundException {

        List < Filter > filters = new ArrayList < > ();
        Mapper mapper = new CustomerPageMapper();
        Page < Customer > customersPage = repository.findAll(PageRequest.of(page, size));

        if (validPhoneNumbersOnly)
            filters.add(Filter.builder().key(Filter.Key.VALIDPHONE).value(validPhoneNumbersOnly).build());
        else if (countryName != null)
            filters.add(Filter.builder().key(Filter.Key.COUNTRYNAME).value(countryName).build());
        else if (countryCode != null && localNumber != null)
            filters.add(Filter.builder().key(Filter.Key.FULLNUMBER).value(PhoneNumber.builder().countryCode(countryCode).localNumber(localNumber).build()).build());

        return mapper.convertTo(getCustomersFiltered(filters, customersPage, PageRequest.of(page, size)), customersPage);
    }

    private List < Customer > getCustomersFiltered(List < Filter > filters, Page < Customer > customersPage, Pageable pageable) throws FilterNotFoundException, CountryNameNotFoundException, CountryCodeNotFoundException {
        List < Customer > customersWithoutFilter = customersPage.getContent();

        for (Filter filter: filters) {
            switch (filter.key) {
                case VALIDPHONE:
                    logger.info("[CustomerService - getCustomersFiltered] Get valid phone customers only ");
                    return CustomerHelper.filterCustomersByPhoneValidity(customersWithoutFilter);
                case COUNTRYNAME:
                    String countryName = String.valueOf(filter.getValue()).toUpperCase();
                    String countryCode = "(" + CountryCodeEnum.lookup(countryName).getCode() + ")";
                    logger.info("[CustomerService - getCustomersFiltered] Country code is: " + countryCode);
                    customersPage = repository.findByCountryCode(countryCode, pageable);
                    return customersPage != null ? customersPage.getContent() : new ArrayList<Customer>();
                case FULLNUMBER:
                    PhoneNumber phoneNumber = (PhoneNumber) filter.getValue();
                    String customerPhoneNumber = String.format("(%s) %s", phoneNumber.getCountryCode(), phoneNumber.getLocalNumber());
                    logger.info("[CustomerService - getCustomersFiltered] Phone number is: " + customerPhoneNumber);
                    customersPage = repository.findByPhone(customerPhoneNumber, pageable);
                    return customersPage != null ? customersPage.getContent() : new ArrayList<Customer>();
                default:
                    logger.info("[CustomerService - getCustomersFiltered] Error in filter value");
                    throw new FilterNotFoundException();
            }
        }
        return customersWithoutFilter;
    }
}