package com.svalero.toprestaurantsapi.service;

import com.svalero.toprestaurantsapi.domain.Address;
import com.svalero.toprestaurantsapi.domain.Customer;
import com.svalero.toprestaurantsapi.domain.Restaurant;
import com.svalero.toprestaurantsapi.exception.AddressNotFoundException;
import com.svalero.toprestaurantsapi.exception.CustomerNotFoundException;

import java.util.List;

public interface AddressService {

    List<Address> findAll();
    List<Address> findByCity(String city);
    Address findById(long id) throws AddressNotFoundException;
    Address addAddress (Address address);
    void deleteAddress (long id) throws AddressNotFoundException;
    Address modifyAddress(long id, Address newAddress) throws AddressNotFoundException;
}
