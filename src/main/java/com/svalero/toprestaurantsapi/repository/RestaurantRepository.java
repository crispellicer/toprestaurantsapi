package com.svalero.toprestaurantsapi.repository;

import com.svalero.toprestaurantsapi.domain.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    List<Restaurant> findAll();
    List<Restaurant> findByVeganMenu(boolean veganMenu);
}
