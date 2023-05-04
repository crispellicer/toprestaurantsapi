package com.svalero.toprestaurantsapi.service;

import com.svalero.toprestaurantsapi.domain.Customer;
import com.svalero.toprestaurantsapi.exception.CustomerNotFoundException;
import com.svalero.toprestaurantsapi.exception.ReserveNotFoundException;
import com.svalero.toprestaurantsapi.exception.RestaurantNotFoundException;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();
    Customer findById(long id) throws CustomerNotFoundException;
    Customer addCustomer (Customer customer);
    void deleteCustomer (long id) throws CustomerNotFoundException;
    Customer modifyCustomer(long id, Customer newCustomer) throws CustomerNotFoundException;
}
