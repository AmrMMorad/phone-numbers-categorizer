package  com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer;

import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.repo.CustomerRepository;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.util.StringUtil;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception.CountryCodeNotFoundException;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.helper.CountryHelper;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.Customer;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CountryCodeEnum;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Iterable<Customer> getCustomers(Boolean validPhoneNumbersOnly, String countryName, String countryCode,
        String localNumber) {
        if(validPhoneNumbersOnly)
            return getValidPhoneNumbersOnly();
        else if(StringUtil.checkEmptyString(countryName))
            return getCustomersByCountryName(countryName);
        else if(StringUtil.checkEmptyString(countryCode) && StringUtil.checkEmptyString(localNumber))
            return getCustomersByCountryCodeAndLocalNumber(countryCode, localNumber);
        return repository.findAll();

    }

    private Iterable<Customer> getValidPhoneNumbersOnly() {
        List<Customer> validCustomers = new ArrayList<>();
        for(Customer customer : repository.findAll()) {
            try {
                CountryCodeEnum customerCountryCodeEnum = CountryHelper.getCountryEnumFromPhone(customer.getPhone());
                if(Pattern.compile(customerCountryCodeEnum.getRegex()).matcher(customer.getPhone()).find())
                    validCustomers.add(customer);
            }catch(CountryCodeNotFoundException e) {
                e.printStackTrace();
            }
        }
        return validCustomers;
    }

    private Iterable<Customer> getCustomersByCountryName(String countryName) {
        List<Customer> validCustomers = new ArrayList<>();
        for(Customer customer : repository.findAll()) {
            try {
                CountryCodeEnum customerCountryCodeEnum = CountryHelper.getCountryEnumFromPhone(customer.getPhone());
                if(customerCountryCodeEnum.name().equalsIgnoreCase(countryName))
                    validCustomers.add(customer);
            }catch(CountryCodeNotFoundException e) {
                e.printStackTrace();
            }
        }
        return validCustomers;
    }

    private Iterable<Customer> getCustomersByCountryCodeAndLocalNumber(String countryCode, String localPhoneNumber) {
        List<Customer> validCustomers = new ArrayList<>();
        String customerPhone = String.format("(%s) %s", countryCode, localPhoneNumber); 
        for(Customer customer : repository.findAll()) {
            if(customer.getPhone().equals(customerPhone))
                validCustomers.add(customer);
        }
        return validCustomers;
    }
}