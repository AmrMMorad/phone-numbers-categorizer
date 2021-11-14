package com.jumia.phonenumberscategorizer.phonenumberscategorizer;

import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.repo.CustomerRepository;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.CustomerService;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.Customer;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CustomerPageDTO;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService service;

    @MockBean
    private CustomerRepository repository;

    /**
     * ################### CONTEXT -- EMPTY DATABASE ###################
     */
    @Test
    public void return_success_for_empty_database_default_parameters() {
        Page < Customer > customerPage = new PageImpl < > (Collections.emptyList());

        doReturn(customerPage).when(repository).findAll(any(PageRequest.class));

        CustomerPageDTO customerPageDTO = service.getCustomers(false, null, null, null, 0, 10);
        assertThat(customerPageDTO.getCustomers().size()).isEqualTo(0);
        assertThat(customerPageDTO.getTotalPages()).isEqualTo(1);
        assertThat(customerPageDTO.getPageSize()).isEqualTo(0);
        assertThat(customerPageDTO.getFilteredPageSize()).isEqualTo(0);
    }

    @Test
    public void return_success_for_empty_database_valid_phones_only() {
        Page < Customer > customerPage = new PageImpl < > (Collections.emptyList());

        doReturn(customerPage).when(repository).findAll(any(PageRequest.class));

        CustomerPageDTO customerPageDTO = service.getCustomers(true, null, null, null, 0, 10);
        assertThat(customerPageDTO.getCustomers().size()).isEqualTo(0);
        assertThat(customerPageDTO.getTotalPages()).isEqualTo(1);
        assertThat(customerPageDTO.getPageSize()).isEqualTo(0);
        assertThat(customerPageDTO.getFilteredPageSize()).isEqualTo(0);
    }

    @Test
    public void return_success_for_empty_database_country_name() {
        Page < Customer > customerPage = new PageImpl < > (Collections.emptyList());

        doReturn(customerPage).when(repository).findAll(any(PageRequest.class));

        CustomerPageDTO customerPageDTO = service.getCustomers(false, "MOROCCO", null, null, 0, 10);
        assertThat(customerPageDTO.getCustomers().size()).isEqualTo(0);
        assertThat(customerPageDTO.getTotalPages()).isEqualTo(1);
        assertThat(customerPageDTO.getPageSize()).isEqualTo(0);
        assertThat(customerPageDTO.getFilteredPageSize()).isEqualTo(0);
    }

    // // /**
    // //  * ################### CONTEXT -- FULL DATABASE ###################
    // //  */
    private Customer insertCustomer(Long id, String name, String phone) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setPhone(phone);
        return customer;
    }

    @Test
    public void return_success_for_one_record_database_default_parameters() {
        Page < Customer > customerPage = new PageImpl < > (Collections.singletonList(insertCustomer(1 L, "Amr Morad", "(237) 1234567")));

        doReturn(customerPage).when(repository).findAll(any(PageRequest.class));

        CustomerPageDTO customerPageDTO = service.getCustomers(false, null, null, null, 0, 10);
        assertThat(customerPageDTO.getCustomers().size()).isEqualTo(1);
        assertThat(customerPageDTO.getTotalPages()).isEqualTo(1);
        assertThat(customerPageDTO.getPageSize()).isEqualTo(1);
        assertThat(customerPageDTO.getFilteredPageSize()).isEqualTo(1);
    }


    @Test
    public void return_record_for_one_record_database_valid_phones() {
        Page < Customer > customerPage = new PageImpl < > (Collections.singletonList(insertCustomer(1 L, "Dominique mekontchou", "(237) 691816558")));

        doReturn(customerPage).when(repository).findAll(any(PageRequest.class));

        CustomerPageDTO customerPageDTO = service.getCustomers(true, null, null, null, 0, 10);
        assertThat(customerPageDTO.getCustomers().size()).isEqualTo(1);
        assertThat(customerPageDTO.getTotalPages()).isEqualTo(1);
        assertThat(customerPageDTO.getPageSize()).isEqualTo(1);
        assertThat(customerPageDTO.getFilteredPageSize()).isEqualTo(1);
    }

    @Test
    public void return_empty_for_one_record_database_invalid_phones() {
        Page < Customer > customerPage = new PageImpl < > (Collections.singletonList(insertCustomer(1 L, "Dominique mekontchou", "(237) 12345")));

        doReturn(customerPage).when(repository).findAll(any(PageRequest.class));

        CustomerPageDTO customerPageDTO = service.getCustomers(true, "", "", "", 0, 10);
        assertThat(customerPageDTO.getCustomers().size()).isEqualTo(0);
        assertThat(customerPageDTO.getTotalPages()).isEqualTo(1);
        assertThat(customerPageDTO.getPageSize()).isEqualTo(1);
        assertThat(customerPageDTO.getFilteredPageSize()).isEqualTo(0);
    }


    @Test
    public void return_empty_for_one_record_database_filter_code_number() {
        Page < Customer > customerPage = new PageImpl < > (Collections.singletonList(insertCustomer(1 L, "Dominique mekontchou", "(237) 691816558")));

        doReturn(customerPage).when(repository).findAll(any(PageRequest.class));

        CustomerPageDTO customerPageDTO = service.getCustomers(false, null, "237", "6916558", 0, 10);
        assertThat(customerPageDTO.getCustomers().size()).isEqualTo(0);
        assertThat(customerPageDTO.getTotalPages()).isEqualTo(1);
        assertThat(customerPageDTO.getPageSize()).isEqualTo(1);
        assertThat(customerPageDTO.getFilteredPageSize()).isEqualTo(0);
    }

    @Test
    public void return_one_record_for_multiple_records_database() {
        List < Customer > customers = new ArrayList < > ();
        customers.add(insertCustomer(1 L, "Amr Morad", "(237) 111111"));
        customers.add(insertCustomer(2 L, "Dominique mekontchou", "(237) 691816558"));

        Page < Customer > customerPage = new PageImpl < > (customers);

        doReturn(customerPage).when(repository).findAll(any(PageRequest.class));

        CustomerPageDTO customerPageDTO = service.getCustomers(true, null, null, null, 0, 10);
        assertThat(customerPageDTO.getCustomers().size()).isEqualTo(1);
        assertThat(customerPageDTO.getTotalPages()).isEqualTo(1);
        assertThat(customerPageDTO.getPageSize()).isEqualTo(2);
        assertThat(customerPageDTO.getFilteredPageSize()).isEqualTo(1);
    }

    @Test
    public void return_empty_record_for_multiple_records_database_filter_country() {
        List < Customer > customers = new ArrayList < > ();
        customers.add(insertCustomer(1 L, "Amr Morad", "(237) 1111111"));
        customers.add(insertCustomer(2 L, "Dominique mekontchou", "(237) 691816558"));

        Page < Customer > customerPage = new PageImpl < > (customers);

        doReturn(customerPage).when(repository).findAll(any(PageRequest.class));

        CustomerPageDTO customerPageDTO = service.getCustomers(false, "MOROCCO", "", "", 0, 10);
        assertThat(customerPageDTO.getCustomers().size()).isEqualTo(0);
        assertThat(customerPageDTO.getTotalPages()).isEqualTo(1);
        assertThat(customerPageDTO.getPageSize()).isEqualTo(2);
        assertThat(customerPageDTO.getFilteredPageSize()).isEqualTo(0);
    }

    @Test
    public void return_empty_record_for_multiple_records_database_filter_country_name() {
        List < Customer > customers = new ArrayList < > ();
        customers.add(insertCustomer(1 L, "Amr Morad", "(237) 111111"));
        customers.add(insertCustomer(2 L, "Dominique mekontchou", "(237) 691816558"));

        Page < Customer > customerPage = new PageImpl < > (customers);

        doReturn(customerPage).when(repository).findAll(any(PageRequest.class));

        CustomerPageDTO customerPageDTO = service.getCustomers(false, "Cameroon", null, null, 0, 10);
        assertThat(customerPageDTO.getCustomers().size()).isEqualTo(0);
        assertThat(customerPageDTO.getTotalPages()).isEqualTo(1);
        assertThat(customerPageDTO.getPageSize()).isEqualTo(2);
        assertThat(customerPageDTO.getFilteredPageSize()).isEqualTo(0);
    }

    @Test
    public void return_record_for_unknown_phone_database_filter_local_number() {
        Page < Customer > customerPage = new PageImpl < > (Collections.singletonList(insertCustomer(1 L, "Amr Morad", "012 3456789")));

        doReturn(customerPage).when(repository).findAll(any(PageRequest.class));

        CustomerPageDTO customerPageDTO = service.getCustomers(false, null, null, null, 0, 10);
        assertThat(customerPageDTO.getCustomers().size()).isEqualTo(1);
        assertThat(customerPageDTO.getTotalPages()).isEqualTo(1);
        assertThat(customerPageDTO.getPageSize()).isEqualTo(1);
        assertThat(customerPageDTO.getFilteredPageSize()).isEqualTo(1);
    }

}