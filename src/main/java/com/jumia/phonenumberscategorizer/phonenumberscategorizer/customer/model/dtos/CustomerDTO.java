package  com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.dtos;

import lombok.Builder;
import lombok.Data;

 @Data
 @Builder
public class CustomerDTO {

    private String name;
    private String phone;
    private CountryCodeEnum countryCode;

}