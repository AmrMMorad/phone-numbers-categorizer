package  com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.repo;

import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CustomerDTO;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.CustomerService;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

 
import java.util.stream.Stream;

@RestController
@RequestMapping("/customer")

public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping
    public Iterable<Customer> getAll(@RequestParam(defaultValue = "false") Boolean validPhoneNumbersOnly,
        @RequestParam(required = false) String countryName,
        @RequestParam(required = false) String countryCode,
        @RequestParam(required = false) String localNumber) {
        return service.getCustomers(validPhoneNumbersOnly, countryName, countryCode, localNumber);
    }
}