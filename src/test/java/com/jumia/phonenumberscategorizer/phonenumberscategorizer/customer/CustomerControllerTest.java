package com.jumia.phonenumberscategorizer.phonenumberscategorizer;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.NestedServletException;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;

import com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception.PageNumberException;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception.PageSizeException;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.Customer;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CustomerPageDTO;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.repo.CustomerRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerIntegrationTest {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

	@Test
    void get_customers_success() throws Exception {
        
        MvcResult result = mockMvc.perform(get("/customer")).andExpect(status().isOk()).andReturn();

        CustomerPageDTO pageDTO = objectMapper.readValue(result.getResponse().getContentAsString(), CustomerPageDTO.class);
        assertThat(pageDTO.getCustomers().size()).isEqualTo(0);
        assertThat(pageDTO.getTotalPages()).isEqualTo(0);
        assertThat(pageDTO.getPageSize()).isEqualTo(0);
        assertThat(pageDTO.getFilteredPageSize()).isEqualTo(0);
    }

    @Test
    void get_invalid_country() throws Exception {

        MvcResult result = mockMvc.perform(get("/customer").param("country", "no country")).andExpect(status().isOk()).andReturn();
        
        CustomerPageDTO pageDTO = objectMapper.readValue(result.getResponse().getContentAsString(), CustomerPageDTO.class);
        assertThat(pageDTO.getCustomers().size()).isEqualTo(0);
        assertThat(pageDTO.getTotalPages()).isEqualTo(0);
        assertThat(pageDTO.getPageSize()).isEqualTo(0);
        assertThat(pageDTO.getFilteredPageSize()).isEqualTo(0);
    }

    @Test
    void get_invalid_page_number() throws Exception {
        try{
            mockMvc.perform(get("/customer").param("page", "-1")).andExpect(status().isBadRequest()).
                andExpect(result -> assertTrue(result.getResolvedException() instanceof PageNumberException));
        }catch(NestedServletException e){
            e.getCause();
        }
    }

    @Test
    void get_invalid_size() throws Exception {
        try{    
            mockMvc.perform(get("/customer").param("size", "-1")).andExpect(status().isBadRequest()).
                andExpect(result -> assertTrue(result.getResolvedException() instanceof PageSizeException));
        }catch(NestedServletException e){
            e.getCause();
        }
    }

    private Customer insertCustomer(String name, String phone) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        return customer;
    }


	

}
