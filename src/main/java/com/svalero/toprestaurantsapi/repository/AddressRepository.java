package com.svalero.toprestaurantsapi.repository;

import com.svalero.toprestaurantsapi.domain.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {

    List<Address> findAll();
    List<Address> findByCity(String city);
}
