package  com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos;

import java.util.List;

import lombok.Builder;
import lombok.Data;

 @Data
 @Builder
public class CustomerPageDTO {

    private List<CustomerDTO> customers;
    private int pageSize;
    private int filteredPageSize;
    private int totalPages;
    
}