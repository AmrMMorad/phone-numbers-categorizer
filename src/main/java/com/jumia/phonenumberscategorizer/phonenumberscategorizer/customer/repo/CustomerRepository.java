package  com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.repo;

import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
 
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
}