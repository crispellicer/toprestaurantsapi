package com.svalero.toprestaurantsapi.service;

import com.svalero.toprestaurantsapi.domain.Customer;
import com.svalero.toprestaurantsapi.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();
    Customer findById(long id) throws CustomerNotFoundException;
    List<Customer> findByName(String name);
    Customer addCustomer (Customer customer);
    void deleteCustomer (long id) throws CustomerNotFoundException;
    Customer modifyCustomer(long id, Customer newCustomer) throws CustomerNotFoundException;
}
