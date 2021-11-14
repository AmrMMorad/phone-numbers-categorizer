package com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.mapper;

import java.util.List;

import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.Customer;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CustomerPageDTO;

import org.springframework.data.domain.Page;

public interface Mapper {

    public CustomerPageDTO convertTo(List < Customer > filteredcustomers, Page < Customer > unfilteredCustomers);

}