package com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.repo;

import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CustomerDTO;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CustomerPageDTO;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.CustomerService;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.Customer;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception.CountryNameNotFoundException;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception.FilterNotFoundException;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception.NotBlankIfPresent;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.validation.constraints.Min;
import javax.validation.Valid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.stream.Stream;

@Validated
@RestController
@RequestMapping("/customer")
@Api(value = "customer", description = "Manages Customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping
    @ApiOperation("Get all customers")
    @CrossOrigin
    public CustomerPageDTO getAll(@RequestParam(defaultValue = "false") Boolean validPhoneNumbersOnly,
        @RequestParam(required = false) @NotBlankIfPresent String countryName,
        @RequestParam(required = false) @NotBlankIfPresent String countryCode,
        @RequestParam(required = false) @NotBlankIfPresent String localNumber,
        @RequestParam(defaultValue = "0") @Min(value = 0, message = "Page value should be greater than 0") int page,
        @RequestParam(defaultValue = "10") @Min(value = 0, message = "Size value should be greater than 0") int size)
    throws FilterNotFoundException, CountryNameNotFoundException {

        return service.getCustomers(validPhoneNumbersOnly, countryName, countryCode, localNumber, page, size);
    }
}