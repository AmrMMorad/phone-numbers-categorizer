package  com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer;

import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.repo.CustomerRepository;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.Customer;
import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Iterable<Customer> getAll() {
        return repository.findAll();
    }
}