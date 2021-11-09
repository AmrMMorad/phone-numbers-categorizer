package  com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer;

import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.repo.CustomerRepository;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.exception.CountryCodeNotFoundException;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.helper.CountryHelper;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.Customer;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CountryCodeEnum;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Iterable<Customer> getAll(Boolean validPhoneNumbersOnly) {
        if(validPhoneNumbersOnly)
            return getValidPhoneNumbersOnly();
        return repository.findAll();

    }

    private Iterable<Customer> getValidPhoneNumbersOnly() {
        List<Customer> validCustomers = new ArrayList<>();
        for(Customer customer : repository.findAll()) {
            try {
                CountryCodeEnum customerCountryCodeEnum = CountryHelper.getCountryFromPhone(customer.getPhone());
                if(Pattern.compile(customerCountryCodeEnum.getRegex()).matcher(customer.getPhone()).find())
                    validCustomers.add(customer);
            }catch(CountryCodeNotFoundException e) {
                e.printStackTrace();
            }
        }
        return validCustomers;
    }
}