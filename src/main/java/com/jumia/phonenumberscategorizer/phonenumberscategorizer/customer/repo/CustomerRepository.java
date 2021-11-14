package com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.repo;

import java.util.List;

import com.jumia.phonenumberscategorizer.phonenumberscategorizer.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository < Customer, Long > {
    public Page < Customer > findByPhone(String phone, Pageable pageable);

    @Query(value = "SELECT * FROM Customer c where c.phone LIKE :countryCode%",
        nativeQuery = true)
    public Page < Customer > findByCountryCode(@Param("countryCode") String countryCode, Pageable pageable);
}