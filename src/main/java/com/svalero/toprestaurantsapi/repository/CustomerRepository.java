package com.svalero.toprestaurantsapi.repository;

import com.svalero.toprestaurantsapi.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findAll();
    List<Customer> findByName(String name);
}
