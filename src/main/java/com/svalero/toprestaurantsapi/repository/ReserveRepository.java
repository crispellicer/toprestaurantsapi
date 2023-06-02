package com.svalero.toprestaurantsapi.repository;

import com.svalero.toprestaurantsapi.domain.Reserve;
import com.svalero.toprestaurantsapi.domain.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveRepository extends CrudRepository<Reserve, Long> {

    List<Reserve> findAll();
    //Reserve findByCustomerName(String customerName);
    List<Reserve> findAllByIsPaid(boolean isPaid);
    List<Reserve> findByRestaurant(Restaurant restaurant);
}
