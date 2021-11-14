package com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.Customer;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CustomerDTO;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CustomerPageDTO;

import org.springframework.data.domain.Page;

public class CustomerPageMapper implements Mapper {

    public CustomerPageDTO convertTo(List < Customer > filteredCustomers, Page < Customer > unfilteredCustomers) {
        List < CustomerDTO > customers = filteredCustomers.stream().map(this::convertToCustomerDTO).collect(Collectors.toList());
        return CustomerPageDTO.builder()
            .customers(customers)
            .pageSize(unfilteredCustomers.getNumberOfElements())
            .filteredPageSize(customers.size())
            .totalPages(unfilteredCustomers.getTotalPages()).build();
    }

    private CustomerDTO convertToCustomerDTO(Customer customer) {
        return CustomerDTO.builder()
            .name(customer.getName())
            .phone(customer.getPhone()).build();
    }

}