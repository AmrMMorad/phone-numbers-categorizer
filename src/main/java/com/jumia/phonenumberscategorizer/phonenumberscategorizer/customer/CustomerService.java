package  com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer;

import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.repo.CustomerRepository;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.util.StringUtil;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception.CountryCodeNotFoundException;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.helper.CountryHelper;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.Customer;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CountryCodeEnum;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CustomerDTO;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CustomerPageDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public CustomerPageDTO getCustomers(Boolean validPhoneNumbersOnly, String countryName, String countryCode,
        String localNumber, int page, int size) {
            
            Page<Customer> paginatedCustomers = repository.findAll(PageRequest.of(page, size));
            List<Customer> filteredCustomers = paginatedCustomers.getContent();

            if(validPhoneNumbersOnly) {
                filteredCustomers = getValidPhoneNumbersOnly(filteredCustomers);
            } else if(StringUtil.checkEmptyString(countryName))
                filteredCustomers = getCustomersByCountryName(countryName, filteredCustomers);
            else if(StringUtil.checkEmptyString(countryCode) && StringUtil.checkEmptyString(localNumber))
                filteredCustomers = getCustomersByCountryCodeAndLocalNumber(countryCode, localNumber, filteredCustomers);
            return convertToCustomerPageDTO(paginatedCustomers, filteredCustomers);

    }

    private List<Customer> getValidPhoneNumbersOnly(List<Customer> paginatedCustomers) {
        List<Customer> validCustomerPhones = new ArrayList<>();
        for(Customer customer : paginatedCustomers) {
            try {
                CountryCodeEnum customerCountryCodeEnum = CountryHelper.getCountryEnumFromPhone(customer.getPhone());
                if(Pattern.compile(customerCountryCodeEnum.getRegex()).matcher(customer.getPhone()).find())
                    validCustomerPhones.add(customer);
            }catch(CountryCodeNotFoundException e) {
                e.printStackTrace();
            }
        }
        return validCustomerPhones;
    }

    private List<Customer> getCustomersByCountryName(String countryName, List<Customer> paginatedCustomers) {
        List<Customer> countryFilteredCustomers = new ArrayList<>();
        for(Customer customer : paginatedCustomers) {
            try {
                CountryCodeEnum customerCountryCodeEnum = CountryHelper.getCountryEnumFromPhone(customer.getPhone());
                if(customerCountryCodeEnum.name().equalsIgnoreCase(countryName))
                countryFilteredCustomers.add(customer);
            }catch(CountryCodeNotFoundException e) {
                e.printStackTrace();
            }
        }
        return countryFilteredCustomers;
    }

    private List<Customer> getCustomersByCountryCodeAndLocalNumber(String countryCode, String localPhoneNumber, 
        List<Customer> paginatedCustomers) {
            List<Customer> phoneFilteredCustomers = new ArrayList<>();
            String customerPhone = String.format("(%s) %s", countryCode, localPhoneNumber);
            for(Customer customer : paginatedCustomers) {
                if(customer.getPhone().equals(customerPhone))
                    phoneFilteredCustomers.add(customer);
            }
            return phoneFilteredCustomers;
    }

    private CustomerPageDTO convertToCustomerPageDTO(Page<Customer> customersPage, List<Customer> filteredCustomers) {
        List<CustomerDTO> customers = filteredCustomers.stream().map(this::convertToCustomerDTO).collect(Collectors.toList());
        return CustomerPageDTO.builder()
                .customers(customers)
                .pageSize(customersPage.getNumberOfElements())
                .filteredPageSize(customers.size())
                .totalPages(customersPage.getTotalPages()).build();
    }
    
    private CustomerDTO convertToCustomerDTO(Customer customer) {
        return CustomerDTO.builder()
                .name(customer.getName())
                .phone(customer.getPhone()).build();

    }
}