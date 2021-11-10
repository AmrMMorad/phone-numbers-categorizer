package  com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.repo;

import java.util.List;

import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
 
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
    
}