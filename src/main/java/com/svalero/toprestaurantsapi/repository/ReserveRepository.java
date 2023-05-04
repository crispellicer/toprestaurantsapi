package com.svalero.toprestaurantsapi.repository;

import com.svalero.toprestaurantsapi.domain.Customer;
import com.svalero.toprestaurantsapi.domain.Reserve;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveRepository extends CrudRepository<Reserve, Long> {

    List<Reserve> findAll();
    //Reserve findByCustomerName(String customerName);
    List<Reserve> findByIsPaid(boolean isPaid);
}
